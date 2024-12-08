package bank.app.service;

import bank.app.dto.TransactionRequestDto;
import bank.app.dto.TransactionResponseDto;
import bank.app.model.entity.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    Transaction getTransactionById(Long id);
    void delete(Long id);
    List<TransactionResponseDto> getTransactionsByAccountId(Long accountId);
    List<TransactionResponseDto> getTransactionsLastMonthByAccountId(Long accountId);
    Transaction addNewTransaction(TransactionRequestDto transactionRequestDto);
    List<TransactionResponseDto> getTransactionsBetweenDates(Long accountId, LocalDate startDate, LocalDate endDate);
}
