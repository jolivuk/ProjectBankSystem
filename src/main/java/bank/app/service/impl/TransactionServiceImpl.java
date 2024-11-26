package bank.app.service.impl;

import bank.app.dto.TransactionRequestDto;
import bank.app.dto.TransactionResponseDto;
import bank.app.exeptions.BalanceException;
import bank.app.exeptions.TransactionNotFoundException;
import bank.app.exeptions.TransactionTypeException;
import bank.app.mapper.TransactionMapper;
import bank.app.model.entity.Account;
import bank.app.model.entity.Transaction;
import bank.app.model.entity.TransactionType;
import bank.app.model.enums.TransactionStatus;
import bank.app.repository.AccountRepository;
import bank.app.repository.TransactionRepository;
import bank.app.repository.TransactionTypeRepository;
import bank.app.service.AccountService;
import bank.app.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public Transaction getTransactionById(Long id){
        if(id == null){
            throw new IllegalArgumentException("Transaction id can not be null");
        }
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id " + id + " not founded") );
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id " + id + " not found "));
        transactionRepository.deleteById(id);
    }


    @Override
    public List<TransactionResponseDto> getTransactionsByAccountId(Long accountId) {
        List<Transaction> listTransactions = transactionRepository.findBySenderIdOrReceiverId(accountId, accountId);
        return transactionMapper.adjustedAmountsInTransactions(listTransactions,accountId);
    }

    @Override
    public List<TransactionResponseDto> getTransactionsLastMonthByAccountId(Long accountId) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(30);

        System.out.println("Searching transactions between: " + startDate + " and " + endDate);

        List<Transaction> transactions = transactionRepository.findByDateRangeAndAccount(
                startDate,
                endDate,
                accountId
        );
        return transactionMapper.adjustedAmountsInTransactions(transactions, accountId);
    }

    @Override
    public Transaction addNewTransaction(TransactionRequestDto transactionRequestDto) {

        Account sender = accountService.getAccountById(transactionRequestDto.sender());
        Account receiver = accountService.getAccountById(transactionRequestDto.receiver());
        Account bank = accountService.getBankAccount();

        accountService.checkAccount(bank);
        accountService.checkAccount(sender);
        accountService.checkAccount(receiver);

        TransactionType transactionType = transactionTypeRepository.findByTransactionTypeName(transactionRequestDto.transactionType())
                .orElseThrow(() ->
                        new TransactionTypeException(String.format("Transaction Type %s not found", transactionRequestDto.transactionType())));


        Transaction transaction = new Transaction(sender, receiver, transactionRequestDto.amount(),
                transactionRequestDto.comment(), TransactionStatus.INITIALIZED, transactionType);

        transaction.setTransactionStatus(TransactionStatus.PROCESSING);
        transaction.setFee(transactionType.getTransactionFee()* transactionRequestDto.amount()/100);
        transactionRepository.save(transaction);

        double totalSum = transactionRequestDto.amount() + transaction.getFee();

        if (totalSum > sender.getBalance()) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setComment(transactionRequestDto.comment() + " - Not enough balance");
            transactionRepository.save(transaction);  // Save the failed transaction
            throw new BalanceException("Not enough balance on account " + transactionRequestDto.sender());
        }

        sender.setBalance(sender.getBalance() - totalSum);
        receiver.setBalance(receiver.getBalance() + transactionRequestDto.amount());
        bank.setBalance(bank.getBalance() + transaction.getFee());

        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        Transaction savedTransaction = transactionRepository.save(transaction);

        accountRepository.save(sender);
        accountRepository.save(receiver);
        accountRepository.save(bank);

        return savedTransaction;
    }
}
