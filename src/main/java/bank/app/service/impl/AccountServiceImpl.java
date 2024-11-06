package bank.app.service.impl;

import bank.app.model.entity.Account;
import bank.app.model.enums.Status;
import bank.app.repository.AccountRepository;
import bank.app.service.AccountService;
import jakarta.transaction.Transactional;
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
//        return accountRepository.findById(accountId).orElse(null);
        return null;
    }

    @Override
    public Account getAccountByUserId(Long userId) {
        return null;
    }


    @Override
    public List<Account> readAllAccounts() {
//        return accountRepository.findAll();
        return null;
    }

    @Override
    public void softDeleteAccount(Long accountId) {

    }

//    @Override
//    @Transactional
//    public void softDeleteAccount(Long accountId) {
//        accountRepository.softDeleteAccount(accountId, Status.DELETED);
//    }


}
