package bank.app.service;

import bank.app.dto.AccountBasicDto;
import bank.app.dto.AccountFullDto;
import bank.app.dto.AccountRequestDto;
import bank.app.model.entity.Account;

import java.util.List;

public interface AccountService {
    Account getAccountById(Long accountId);
    AccountBasicDto getBasicAccountInfo(Long accountId);
    AccountFullDto getFullAccountInfo(Long accountId);
    List<AccountBasicDto> findByUserId(Long userId);
    AccountBasicDto createNewAccount(AccountRequestDto account, Long userId);
    Account getBankAccount();
    void checkAccount(Account account);
}
