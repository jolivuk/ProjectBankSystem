package bank.app.service;

import bank.app.dto.AccountBasicDto;
import bank.app.dto.AccountFullDto;
import bank.app.model.entity.Account;

import java.util.List;

public interface AccountService {

    AccountBasicDto getBasicAccountInfo(Long accountId);
    AccountFullDto getFullAccountInfo(Long accountId);
    Account getAccountById(Long accountId);
//    List<Account> readAllAccounts();
//    void softDeleteAccount(Long accountId);
}
