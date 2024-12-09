package bank.app.dto;

import bank.app.model.enums.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@ToString
public class AccountBasicDto
 {
     Long id;
     Status status;
     Double balance;
     String iban;
     String swift;
     LocalDateTime createdAt;
     LocalDateTime lastUpdate;


     public AccountBasicDto(Long id, Status status, Double balance, String iban,
                            String swift, LocalDateTime createdAt, LocalDateTime lastUpdate) {
         this.id = id;
         this.status = status;
         this.balance = balance;
         this.iban = iban;
         this.swift = swift;
         this.createdAt = createdAt;
         this.lastUpdate = lastUpdate;
     }

     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         AccountBasicDto that = (AccountBasicDto) o;
         return Objects.equals(id, that.id) && status == that.status && Objects.equals(balance, that.balance)
                 && Objects.equals(createdAt, that.createdAt)
                 && Objects.equals(lastUpdate.toLocalDate(), that.lastUpdate.toLocalDate());
     }

     @Override
     public int hashCode() {
         return Objects.hash(id, status, balance, createdAt, lastUpdate);
     }
 }

