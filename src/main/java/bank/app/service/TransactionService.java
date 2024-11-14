package bank.app.service;

import bank.app.dto.TransactionDto;
import bank.app.dto.TransactionResponseDto;
import bank.app.model.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction getTransactionById(Long id);
    List<Transaction> getAllTransactions();
    void delete(Long id);
    List<TransactionResponseDto> getTransactionsByAccountId(Long accountId);
    List<TransactionResponseDto> getTransactionsLastMonthByAccountId(Long accountId);
    Transaction addNewTransaction(TransactionDto transactionDto);
    List<TransactionResponseDto> adjustedAmountsInTransactionByAccount(List<Transaction> transactions,Long accountId);
}
