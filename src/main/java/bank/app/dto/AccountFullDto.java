package bank.app.dto;

import bank.app.model.entity.Account;
import bank.app.model.entity.User;
import bank.app.model.enums.Status;

import java.time.LocalDateTime;


public record AccountFullDto(
        Long id,
        UserFullDto user,
        Status status,
        Double balance,
        LocalDateTime createdAt,
        LocalDateTime lastUpdate
) {
    public static AccountFullDto fromAccount(Account account) {
        return new AccountFullDto(
                account.getId(),
                UserFullDto.fromUser(account.getUser()),
                account.getStatus(),
                account.getBalance(),
                account.getCreatedAt(),
                account.getLastUpdate()
        );
    }
}
