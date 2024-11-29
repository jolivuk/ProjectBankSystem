package bank.app.service.impl;

import bank.app.dto.AccountBasicDto;
import bank.app.dto.AccountFullDto;
import bank.app.dto.AccountRequestDto;
import bank.app.exeptions.AccountIsBlockedException;
import bank.app.exeptions.AccountNotFoundException;
import bank.app.exeptions.UserNotFoundException;
import bank.app.mapper.AccountMapper;
import bank.app.model.entity.Account;
import bank.app.model.entity.User;
import bank.app.model.enums.Role;
import bank.app.model.enums.Status;
import bank.app.repository.AccountRepository;
import bank.app.repository.TransactionRepository;
import bank.app.repository.UserRepository;
import bank.app.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
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
        return accountMapper.toAccountBasicDto(account);
    }

    @Override
    public AccountFullDto getFullAccountInfo(Long accountId) {
        Account account = getAccountById(accountId);
        return accountMapper.toFullDto(account);
    }

    @Override
    public List<AccountBasicDto> findByUserId(Long userId) {
        List<Account> allByUserId = accountRepository.findAllByUserId(userId);
        return accountMapper.toAccountBasicDtoList(allByUserId);
    }

    @Override
    public AccountBasicDto createNewAccount(AccountRequestDto accountRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found with id: " + userId));
        
        Account account = new Account(user,accountRequestDto.iban(),
                accountRequestDto.swift(),accountRequestDto.status(),accountRequestDto.balance());
        accountRepository.save(account);
        return accountMapper.toBasicDto(account);
    }

    @Override
    public Account getBankAccount() {
        Account accountBank = accountRepository.findByUserRole(Role.ROLE_BANK).stream().findFirst()
                .orElseThrow(() -> new UserNotFoundException("Account not found with role: " + Role.ROLE_BANK));;
        return accountBank;
    }

    @Override
    public void checkAccount(Account account) {
        if (account.isDeleted()) {
            throw new AccountIsBlockedException(String.format("Account with id %d is deleted", account.getId()));
        }

        if (account.isBlocked()) {
            throw new AccountIsBlockedException(String.format("Account with id %d is blocked", account.getId()));
        }
    }

    @Override
    public void deleteAccount(Long accountId) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()-> new AccountNotFoundException("Account not found with id: " + accountId));
        account.setStatus(Status.DELETED);
        accountRepository.save(account);
    }

}
