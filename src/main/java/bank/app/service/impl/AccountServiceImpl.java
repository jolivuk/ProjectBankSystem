package bank.app.service.impl;

import bank.app.dto.AccountBasicDto;
import bank.app.dto.AccountFullDto;
import bank.app.model.entity.Account;
import bank.app.repository.AccountRepository;
import bank.app.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("account not found"));
    }

//    @Override
//    public List<Account> readAllAccounts() {
//        return null;
//    }
//
//    @Override
//    public void softDeleteAccount(Long accountId) {
//
//    }

    @Override
    public AccountBasicDto getBasicAccountInfo(Long accountId) {
        Account account = getAccountById(accountId);
        return AccountBasicDto.fromAccount(account);
    }

    @Override
    public AccountFullDto getFullAccountInfo(Long accountId) {
        Account account = getAccountById(accountId);
        return AccountFullDto.fromAccount(account);
    }
//    @Override
//    @Transactional
//    public void softDeleteAccount(Long accountId) {
//        accountRepository.softDeleteAccount(accountId, Status.DELETED);
//    }


}
