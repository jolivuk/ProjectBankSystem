package bank.app.service.impl;

import bank.app.dto.TransactionDto;
import bank.app.dto.TransactionResponseDto;
import bank.app.exeptions.BalanceException;
import bank.app.exeptions.TransactionNotFoundException;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Override
    public Transaction getTransactionById(Long id){
        if(id == null){
            throw new IllegalArgumentException("Transaction id can not be null");
        }
        return transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException("transaction with id " + id + " not founded") );
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Пользователь с ID: " + id + " не найден"));
        transactionRepository.deleteById(id);
    }


    @Override
    public List<TransactionResponseDto> getTransactionsByAccountId(Long accountId) {
        return adjustedAmountsInTransactionByAccount(transactionRepository.findBySenderIdOrReceiverId(accountId, accountId),accountId);
    }

    @Override
    public List<TransactionResponseDto> getTransactionsLastMonthByAccountId(Long accountId) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(30);
        return adjustedAmountsInTransactionByAccount(
                transactionRepository.findBySenderIdOrReceiverIdAndTransactionDateBetween(accountId,accountId, startDate, endDate),
                accountId);
    }

    @Override
    public Transaction addNewTransaction(TransactionDto transactionDto) {

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
            throw new BalanceException("Not enough balance on account " + transactionDto.getSender());
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

    @Override
    public List<TransactionResponseDto> adjustedAmountsInTransactionByAccount(List<Transaction> transactions, Long accountId) {
        return transactions.stream()
                .map(transaction -> {
                    Double adjustedAmount = transaction.getSender().getId().equals(accountId)
                            ? -transaction.getAmount()
                            : transaction.getAmount();

                    return new TransactionResponseDto(
                            transaction.getId(),
                            transaction.getSender().getId(),
                            transaction.getReceiver().getId(),
                            adjustedAmount,
                            transaction.getComment(),
                            transaction.getTransactionDate().toString(),
                            transaction.getTransactionStatus().toString(),
                            transaction.getTransactionType().getTransactionTypeName()
                    );
                })
                .collect(Collectors.toList());
    }

}
