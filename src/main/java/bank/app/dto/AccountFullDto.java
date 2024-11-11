package bank.app.dto;

import bank.app.model.entity.Account;
import bank.app.model.entity.User;
import bank.app.model.enums.Status;

import java.time.LocalDateTime;
import java.util.Objects;


public class AccountFullDto extends AccountBasicDto{
    private final UserFullDto user;

    public AccountFullDto(Long id, UserFullDto user, Status status,
                          Double balance, LocalDateTime createdAt, LocalDateTime lastUpdate) {
        super(id, status, balance, createdAt, lastUpdate);
        this.user = user;
    }

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

    public UserFullDto getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AccountFullDto that = (AccountFullDto) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user);
    }
}