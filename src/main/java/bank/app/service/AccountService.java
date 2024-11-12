package bank.app.service;

import bank.app.dto.AccountBasicDto;
import bank.app.dto.AccountFullDto;
import bank.app.model.entity.Account;
import bank.app.model.entity.Transaction;
import bank.app.model.entity.User;

import java.util.List;

public interface AccountService {
    AccountBasicDto getBasicAccountInfo(Long accountId);
    AccountFullDto getFullAccountInfo(Long accountId);
    Account getAccountById(Long accountId);
    List<Account> findByUserId(Long userId);
    Account createNewAccount(AccountBasicDto account,Long userId);
    List<Transaction> getAllTransactionsByAccountId(Long accountId);
    void checkAccount(Account account);
}
