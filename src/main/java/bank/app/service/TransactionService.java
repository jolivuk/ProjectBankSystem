package bank.app.service;

import bank.app.dto.TransactionDto;
import bank.app.dto.TransfertDto;
import bank.app.model.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction getTransactionById(Long id);
    List<Transaction> getAllTransactions();
    void delete(Long id);
    List<Transaction> getTransactionsByAccountId(Long accountId);
    List<Transaction> getTransactionsLastMonthByAccountId(Long accountId);
    Transaction addNewTransaction(Long accountId, TransfertDto transfertDto);

}
