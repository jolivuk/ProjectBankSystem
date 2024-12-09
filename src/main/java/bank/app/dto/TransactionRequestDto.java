package bank.app.dto;

import bank.app.model.enums.TransactionTypeName;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public record TransactionRequestDto(
        @NotNull(message = "Sender ID cannot be null")
        @Positive(message = "Sender ID must be positive number")
        Long sender,

        @NotNull(message = "Receiver ID cannot be null")
        @Positive(message = "Receiver ID must be positive number")
        Long receiver,

        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be positive")
        @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
        double amount,

        @Size(max = 255, message = "Comment should not exceed 255 characters")
        String comment,

        @NotNull(message = "Transaction type cannot be null")
        TransactionTypeName transactionType) {
}


