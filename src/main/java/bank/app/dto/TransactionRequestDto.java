package bank.app.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionRequestDto {
    @NotNull(message = "Sender ID cannot be null")
    @Positive(message = "Sender ID must be positive number")
        private final Long sender;

    @NotNull(message = "Receiver ID cannot be null")
    @Positive(message = "Receiver ID must be positive number")
        private final Long receiver;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
        private final double amount;

    @Size(max = 255, message = "Comment should not exceed 255 characters")
        private final String comment;

    @NotNull(message = "Transaction type cannot be null")
        private final String  transactionType;

    public TransactionRequestDto(Long sender, Long receiver, double amount, String comment, String transactionType) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.comment = comment;
        this.transactionType = transactionType;
    }

}
