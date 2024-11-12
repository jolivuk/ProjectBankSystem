package bank.app.service.impl;

import bank.app.dto.TransactionDto;
import bank.app.dto.TransfertDto;
import bank.app.exeptions.BalanceException;
import bank.app.exeptions.TransactionTypeException;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        List<Transaction> transactions = transactionRepository.findBySenderIdOrReceiverId(accountId, accountId);
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsLastMonthByAccountId(Long accountId) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(30);
        return transactionRepository.findBySenderIdOrReceiverIdAndTransactionDateBetween(accountId,accountId, startDate, endDate);
    }

    @Override
    public Transaction addNewTransaction(Long accountId,TransfertDto transfertDto) {

        TransactionDto transactionDto = new TransactionDto(
                accountId, transfertDto.receiver(),transfertDto.amount(),transfertDto.comment(),transfertDto.transactionType());

        Account sender = accountService.getAccountById(transactionDto.getSender());
        Account receiver = accountService.getAccountById(transactionDto.getReceiver());
        Account bank = accountService.getAccountById(1L);

        accountService.checkAccount(bank);
        accountService.checkAccount(sender);
        accountService.checkAccount(receiver);

        TransactionType transactionType = transactionTypeRepository.findByTransactionTypeName(transactionDto.getTransactionType())
                .orElseThrow(() ->
                        new TransactionTypeException(String.format("Transaction Type %s not found", transactionDto.getTransactionType())));


        Transaction transaction = new Transaction(sender, receiver, transactionDto.getAmount(),
                transactionDto.getComment(), TransactionStatus.INITIALIZED, transactionType);

        transaction.setTransactionStatus(TransactionStatus.PROCESSING);
        transaction.setFee(transactionType.getTransactionFee()*transactionDto.getAmount()/100);
        transactionRepository.save(transaction);

        double totalSum = transactionDto.getAmount() + transaction.getFee();

        if (totalSum > sender.getBalance()) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setComment(transactionDto.getComment() + " - Not enough balance");
            transactionRepository.save(transaction);  // Save the failed transaction
            throw new BalanceException("Not enough balance on account " + accountId);
        }


        sender.setBalance(sender.getBalance() - totalSum);
        receiver.setBalance(receiver.getBalance() + transactionDto.getAmount());
        bank.setBalance(bank.getBalance() + transaction.getFee());

        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        Transaction savedTransaction = transactionRepository.save(transaction);

        accountRepository.save(sender);
        accountRepository.save(receiver);
        accountRepository.save(bank);

        return savedTransaction;
    }

}
