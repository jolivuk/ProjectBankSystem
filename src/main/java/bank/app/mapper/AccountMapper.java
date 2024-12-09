package bank.app.mapper;

import bank.app.dto.AccountBasicDto;
import bank.app.dto.AccountFullDto;
import bank.app.model.entity.Account;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountMapper {

    private final UserMapper userMapper;

    public AccountMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public AccountBasicDto toAccountBasicDto(Account account) {
        return new AccountBasicDto(account.getId(),
                account.getStatus(),
                account.getBalance(),
                account.getIban(),
                account.getSwift(),
                account.getCreatedAt(),
                account.getLastUpdate()
        );
    }

    public AccountFullDto toFullDto(Account account) {
        return new AccountFullDto(
                account.getId(),
                account.getStatus(),
                account.getBalance(),
                account.getIban(),
                account.getSwift(),
                account.getCreatedAt(),
                account.getLastUpdate(),
                account.getUser() != null ? this.userMapper.toDto(account.getUser()) : null
        );
    }

    public AccountBasicDto toBasicDto(Account account) {
        return new AccountBasicDto(
                account.getId(),
                account.getStatus(),
                account.getBalance(),
                account.getIban(),
                account.getSwift(),
                account.getCreatedAt(),
                account.getLastUpdate()
        );
    }

    public List<AccountBasicDto> toAccountBasicDtoList(List<Account> accounts){
        return accounts.stream().map(account -> toBasicDto(account)).toList();
    }
}
