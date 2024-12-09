package bank.app.dto;

import bank.app.model.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ToString
public class AccountReportDto extends AccountBasicDto {
    List<TransactionResponseDto> transactions;
    String firstName;
    String lastName;
    String address;
    Double startBalance;
    Double totalIncome;
    Double totalExpenses;

    public AccountReportDto(Long id, Status status, Double balance, String iban, String swift, LocalDateTime createdAt,
                            LocalDateTime lastUpdate, List<TransactionResponseDto> transactions, String firstName,
                            String lastName, String address, Double totalIncome, Double totalExpenses) {
        super(id, status, balance, iban, swift, createdAt, lastUpdate);
        this.transactions = transactions;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.startBalance = balance - Math.abs(totalIncome) + Math.abs(totalExpenses);
    }
}
