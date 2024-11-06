package bank.app.dto;



import java.time.LocalDateTime;


public record TransactionDto(String senderName, String receiverName,
        double amount,
        String description,
        LocalDateTime createdAt){


}
