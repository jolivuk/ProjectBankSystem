package bank.app.dto;

import bank.app.model.entity.TransactionType;
import bank.app.model.enums.TransactionTypeName;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TransactionResponseDto {
         Long transactionId;
         Long sender;
         Long receiver;
         double amount;
         String comment;
         String transactionDate;
         String transactionStatus;
         TransactionTypeName transactionType;

    public TransactionResponseDto(long transactionId, Long sender, Long receiver, double amount, String comment,
                                  String transactionDate, String transactionStatus, TransactionTypeName transactionType) {
        this.transactionId = transactionId;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.comment = comment;
        this.transactionDate = transactionDate;
        this.transactionStatus = transactionStatus;
        this.transactionType = transactionType;
    }
}
