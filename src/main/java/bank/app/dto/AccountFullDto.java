package bank.app.dto;

import bank.app.model.enums.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ToString
public class AccountFullDto extends AccountBasicDto {
    UserResponseDto user;

    public AccountFullDto(Long id, Status status, Double balance, String iban,
                          String swift, LocalDateTime createdAt,
                          LocalDateTime lastUpdate, UserResponseDto user) {

        super(id, status, balance, iban, swift, createdAt, lastUpdate);
        this.user = user;
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
