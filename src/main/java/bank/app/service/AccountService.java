package bank.app.service;

import bank.app.model.entity.Account;

import java.util.List;

public interface AccountService {
    Account getAccountById(Long accountId);
    Account getAccountByUserId(Long userId);
    List<Account> readAllAccounts();

    void softDeleteAccount(Long accountId);
}
