package bank.app.service.impl;

import bank.app.dto.*;
import bank.app.exeption.AccountAlreadyDeletedException;
import bank.app.exeption.AccountIsBlockedException;
import bank.app.exeption.AccountNotFoundException;
import bank.app.exeption.UserNotFoundException;
import bank.app.exeption.errorMessage.ErrorMessage;
import bank.app.mapper.AccountMapper;
import bank.app.mapper.TransactionMapper;
import bank.app.model.entity.Account;
import bank.app.model.entity.Transaction;
import bank.app.model.entity.User;
import bank.app.model.enums.Role;
import bank.app.model.enums.Status;
import bank.app.repository.AccountRepository;
import bank.app.repository.TransactionRepository;
import bank.app.repository.UserRepository;
import bank.app.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static bank.app.util.TransactionUtil.calculateTotalExpenses;
import static bank.app.util.TransactionUtil.calculateTotalIncome;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public Account getAccountById(Long accountId) {
        log.info("Fetching account with ID: {}", accountId);
        try {
            return accountRepository.findById(accountId)
                    .orElseThrow(() -> {
                        log.error("Account not found with ID: {}", accountId);
                        return new AccountNotFoundException(String.format(ErrorMessage.ACCOUNT_NOT_FOUND + accountId));
                    });
        } catch (AccountNotFoundException e) {
            log.error("Failed to get account with ID: {}. Error: {}", accountId, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public AccountBasicDto getBasicAccountInfo(Long accountId) {
        log.info("Fetching basic info for account with ID: {}", accountId);
        Account account = getAccountById(accountId);
        return accountMapper.toAccountBasicDto(account);
    }

    @Override
    public AccountFullDto getFullAccountInfo(Long accountId) {
        log.info("Fetching full info for account with ID: {}", accountId);
        Account account = getAccountById(accountId);
        return accountMapper.toFullDto(account);
    }

    @Override
    public List<AccountBasicDto> findByUserId(Long userId) {
        log.info("Fetching accounts for user with ID: {}", userId);
        List<Account> allByUserId = accountRepository.findAllByUserId(userId);
        log.info("Found {} accounts for user with ID: {}", allByUserId.size(), userId);
        return accountMapper.toAccountBasicDtoList(allByUserId);
    }

    @Override
    public AccountBasicDto createNewAccount(AccountRequestDto accountRequestDto, Long userId) {
        log.info("Starting to create new account for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Cannot create account - user not found with ID: {}", userId);
                    return new UserNotFoundException(ErrorMessage.USER_NOT_FOUND + userId);
                });

        Account account = new Account(user, accountRequestDto.iban(),
                accountRequestDto.swift(), accountRequestDto.status(), accountRequestDto.balance());
        accountRepository.save(account);
        log.info("Successfully created new account with IBAN: {} for user ID: {}", accountRequestDto.iban(), userId);
        return accountMapper.toBasicDto(account);
    }

    @Override
    public Account getBankAccount() {
        return accountRepository.findByUserRole(Role.ROLE_BANK).stream().findFirst()
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.ACCOUNT_BANK_NOT_FOUND));
    }

    @Override
    public void checkAccount(Account account) {
        log.info("Checking status for account ID: {}", account.getId());
        if (account.isDeleted()) {
            log.error("Account with ID {} is deleted", account.getId());
            throw new AccountIsBlockedException(String.format(ErrorMessage.ACCOUNT_IS_DELETED + account.getId()));
        }

        if (account.isBlocked()) {
            log.error("Account with ID {} is blocked", account.getId());
            throw new AccountIsBlockedException(String.format(ErrorMessage.ACCOUNT_IS_BLOCKED + account.getId()));
        }
        log.info("Account ID: {} status check passed", account.getId());
    }


    @Override
    public void deleteAccount(Long accountId) throws AccountNotFoundException {
        log.info("Attempting to delete account ID: {}", accountId);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.error("Cannot delete account - account not found with ID: {}", accountId);
                    return new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND + accountId);
                });
        if (account.getStatus().equals(Status.DELETED)) {
            throw new AccountAlreadyDeletedException(String.format(ErrorMessage.ACCOUNT_IS_DELETED + account));
        }
        account.setStatus(Status.DELETED);
        accountRepository.save(account);
        log.info("Successfully deleted account ID: {}", accountId);
    }

    @Override
    public void blockAccount(Long accountId) {
        log.info("Attempting to block account ID: {}", accountId);
        Account account;
        try {
            account = accountRepository.findById(accountId)
                    .orElseThrow(() -> {
                        log.error("Cannot block account - account not found with ID: {}", accountId);
                        return new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND);
                    });
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
        account.setStatus(Status.BLOCKED);

        accountRepository.save(account);
        log.info("Successfully blocked account ID: {}", accountId);
    }

    @Override
    public void unblockAccount(Long accountId) {
        log.info("Attempting to unblock account ID: {}", accountId);
        Account account;
        try {
            account = accountRepository.findById(accountId)
                    .orElseThrow(() -> {
                        log.error("Cannot unblock account - account not found with ID: {}", accountId);
                        return new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND);
                    });
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(account.getStatus().equals(Status.BLOCKED)) {
            account.setStatus(Status.ACTIVE);
        }
        if(account.getStatus().equals(Status.DELETED)) {
            throw new AccountAlreadyDeletedException(String.format(ErrorMessage.ACCOUNT_IS_DELETED + account));
        }

        accountRepository.save(account);
        log.info("Successfully unblocked account ID: {}", accountId);
    }

    @Override
    public AccountReportDto generateAccountPdfBetweenDates(Long accountId, LocalDate startDate, LocalDate endDate) {
        Account account = getAccountById(accountId);
        User user = account.getUser();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        List<Transaction> transactions = transactionRepository
                .findByDateRangeAndAccount(startDateTime, endDateTime, accountId);

        List<TransactionResponseDto> transactionResponseDtoList =
                transactionMapper.adjustedAmountsInTransactions(transactions, accountId);

        return new AccountReportDto(
                account.getId(),
                account.getStatus(),
                account.getBalance(),
                account.getIban(),
                account.getSwift(),
                account.getCreatedAt(),
                account.getLastUpdate(),
                transactionResponseDtoList,
                user.getPrivateInfo().getFirstName(),
                user.getPrivateInfo().getLastName(),
                user.getPrivateInfo().getAddress().toString(),
                calculateTotalIncome(transactions, accountId),
                calculateTotalExpenses(transactions, accountId)
        );
    }
}
