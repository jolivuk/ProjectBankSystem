package bank.app.service.impl;

import bank.app.dto.AccountBasicDto;
import bank.app.dto.AccountFullDto;
import bank.app.model.entity.Account;
import bank.app.model.entity.Transaction;
import bank.app.repository.AccountRepository;
import bank.app.repository.TransactionRepository;
import bank.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("account not found"));
    }

    @Override
    public List<Account> findByUserId(Long userId) {
        return accountRepository.findAllByUserId(userId);
    }

    @Override
    public List<Transaction> getAllTransactionsByAccountId(Long accountId) {
        return transactionRepository.findBySenderIdOrReceiverId(accountId, accountId);
    }


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



}
