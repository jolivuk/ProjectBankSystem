package bank.app.service;

import bank.app.dto.AccountBasicDto;
import bank.app.dto.AccountFullDto;
import bank.app.model.entity.Account;
import bank.app.model.entity.Transaction;
import bank.app.model.entity.User;

import java.util.List;

public interface AccountService {
    Account getAccountById(Long accountId);
    AccountBasicDto getBasicAccountInfo(Long accountId);
    AccountFullDto getFullAccountInfo(Long accountId);
    List<AccountBasicDto> findByUserId(Long userId);
    Account createNewAccount(AccountBasicDto account,Long userId); // todo account заменить на accountDto
    List<Transaction> getAllTransactionsByAccountId(Long accountId); // todo переместить в TransactionService
    void checkAccount(Account account);
}
