package bank.app.service.impl;

import bank.app.dto.AccountBasicDto;
import bank.app.dto.AccountFullDto;
import bank.app.dto.UserBasicDto;
import bank.app.exeptions.AccountNotFoundException;
import bank.app.exeptions.UserNotFoundException;
import bank.app.model.entity.Account;
import bank.app.model.entity.Transaction;
import bank.app.model.entity.User;
import bank.app.model.enums.Status;
import bank.app.repository.AccountRepository;
import bank.app.repository.TransactionRepository;
import bank.app.repository.UserRepository;
import bank.app.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;


    private final TransactionRepository transactionRepository;

    public Account getAccountById(Long accountId) {
        try {
            return accountRepository.findById(accountId)
                    .orElseThrow(() -> new AccountNotFoundException(
                            String.format("Account with id %d not found", accountId)
                    ));
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public List<Account> findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return accountRepository.findAllByUserId(userId);
    }

    @Override
    public Account createNewAccount(AccountBasicDto accountBasicDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("userNot founded"));
        
        Account account = new Account(user,accountBasicDto.getIban(),
                accountBasicDto.getSwift(),Status.ACTIVE,accountBasicDto.getBalance());
        accountRepository.save(account);
        return account;
    }

    @Override
    public List<Transaction> getAllTransactionsByAccountId(Long accountId) {
        return transactionRepository.findBySenderIdOrReceiverId(accountId, accountId);
    }


}
