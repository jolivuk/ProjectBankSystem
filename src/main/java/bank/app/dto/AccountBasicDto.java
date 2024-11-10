package bank.app.dto;

import bank.app.model.entity.Account;
import bank.app.model.enums.Status;

import java.time.LocalDateTime;
import java.util.Objects;

public class AccountBasicDto
 {
     private final Long id;
     private final Status status;
     private final Double balance;
     private final LocalDateTime createdAt;
     private final LocalDateTime lastUpdate;

     public AccountBasicDto(Long id, Status status, Double balance,
                            LocalDateTime createdAt, LocalDateTime lastUpdate) {
         this.id = id;
         this.status = status;
         this.balance = balance;
         this.createdAt = createdAt;
         this.lastUpdate = lastUpdate;
     }
public static AccountBasicDto fromAccount(Account account) {
        return new AccountBasicDto(
        account.getId(),
        account.getStatus(),
        account.getBalance(),
        account.getCreatedAt(),
        account.getLastUpdate()
        );
        }

     public Long getId() {
         return id;
     }

     public Status getStatus() {
         return status;
     }

     public Double getBalance() {
         return balance;
     }

     public LocalDateTime getCreatedAt() {
         return createdAt;
     }

     public LocalDateTime getLastUpdate() {
         return lastUpdate;
     }

     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         AccountBasicDto that = (AccountBasicDto) o;
         return Objects.equals(id, that.id) && status == that.status && Objects.equals(balance, that.balance) && Objects.equals(createdAt, that.createdAt) && Objects.equals(lastUpdate, that.lastUpdate);
     }

     @Override
     public int hashCode() {
         return Objects.hash(id, status, balance, createdAt, lastUpdate);
     }
 }

