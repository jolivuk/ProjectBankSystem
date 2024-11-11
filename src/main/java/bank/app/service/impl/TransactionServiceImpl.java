package bank.app.service.impl;

import bank.app.dto.TransactionDto;
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
    public Transaction addNewTransaction(TransactionDto transactionDto) {

        Account sender = accountService.getAccountById(transactionDto.sender());
        Account receiver = accountService.getAccountById(transactionDto.receiver());

        if (sender.isDeleted() || sender.isBlocked() || receiver.isDeleted() || receiver.isBlocked()) {
            throw new RuntimeException("One or both accounts are inactive or blocked.");
        }

        TransactionType transactionType = transactionTypeRepository.findByTransactionTypeName(transactionDto.transactionType())
                .orElseThrow(() -> new RuntimeException("Transaction type not found"));


        Transaction transaction = new Transaction(sender, receiver, transactionDto.amount(),
                transactionDto.comment(), TransactionStatus.INITIALIZED, transactionType);

        transaction.setTransactionStatus(TransactionStatus.PROCESSING);
        transaction.setFee(transactionType.getTransactionFee()*transactionDto.amount());
        transactionRepository.save(transaction);


        if ((transactionDto.amount() + transaction.getFee()) > sender.getBalance()) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setComment(transactionDto.comment() + " - Not enough balance");
            transactionRepository.save(transaction);  // Save the failed transaction
            throw new RuntimeException("Not enough balance");
        }

        //Send code confirmation to user

        sender.setBalance(sender.getBalance() - transactionDto.amount());
        receiver.setBalance(receiver.getBalance() + transactionDto.amount());


        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        Transaction savedTransaction = transactionRepository.save(transaction);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        return savedTransaction;
    }

}
