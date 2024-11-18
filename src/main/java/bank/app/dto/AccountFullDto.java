package bank.app.dto;

import bank.app.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class AccountFullDto extends AccountBasicDto{
    private final UserResponseDto user;

    public AccountFullDto(Long id, Status status, Double balance, String iban,
                          String swift, LocalDateTime createdAt,
                          LocalDateTime lastUpdate, UserResponseDto user) {

        super(id, status, balance, iban, swift, createdAt, lastUpdate);
        this.user = user;
    }


    public UserResponseDto getUser() {
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
