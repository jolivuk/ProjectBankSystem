package bank.app.dto;

import bank.app.model.entity.Account;
import bank.app.model.enums.Status;

import java.time.LocalDateTime;

public record AccountBasicDto (Long id,
        Status status,
        Double balance,
        LocalDateTime createdAt,
        LocalDateTime lastUpdate
) {
public static AccountBasicDto fromAccount(Account account) {
        return new AccountBasicDto(
        account.getId(),
        account.getStatus(),
        account.getBalance(),
        account.getCreatedAt(),
        account.getLastUpdate()
        );
        }
    }

