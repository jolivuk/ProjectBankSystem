package bank.app.service.impl;

import bank.app.dto.AccountBasicDto;
import bank.app.dto.AccountFullDto;
import bank.app.exeptions.AccountIsBlockedException;
import bank.app.exeptions.AccountNotFoundException;
import bank.app.exeptions.UserNotFoundException;
import bank.app.mapper.AccountMapper;
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
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
// todo - все сервисы должны в методах принимать dto и возвращать dto на входе какой
//  requestdto на выходе ResponceDto.Сервисы должны использовать mapper для преобразования сущности в dto и обратно
//  и тесты
// todo валидацию dto
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
    public AccountFullDto createNewAccount(AccountBasicDto accountBasicDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found with id: " + userId));
        
        Account account = new Account(user,accountBasicDto.getIban(),
                accountBasicDto.getSwift(),Status.ACTIVE,accountBasicDto.getBalance());
        accountRepository.save(account);
        return accountMapper.toFullDto(account);
    }

    @Override
    public Account getBankAccount() {
        User user = userRepository.findByRole(Role.BANK)
                .orElseThrow(() -> new UserNotFoundException("User not found with role: " + Role.BANK));
        Account accountBank = getAccountById(user.getId());
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

}
