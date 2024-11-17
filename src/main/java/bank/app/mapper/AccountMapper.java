package bank.app.mapper;

import bank.app.dto.AccountBasicDto;
import bank.app.dto.AddressCreateRequestDto;
import bank.app.dto.AddressResponseDto;
import bank.app.model.entity.Account;
import bank.app.model.entity.Address;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class AccountMapper {
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
    public List<AccountBasicDto> toAccountBasicDtoList(List<Account> accounts){
        return accounts.stream().map(account -> toAccountBasicDto(account)).toList();
    }
}
