package bank.app.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
         String transactionType;

    public TransactionResponseDto(long transactionId, Long sender, Long receiver, double amount, String comment,
                                  String transactionDate, String transactionStatus, String transactionType) {
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
