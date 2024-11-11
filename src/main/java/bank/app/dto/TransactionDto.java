package bank.app.dto;



import java.math.BigDecimal;
import java.time.LocalDateTime;


public record TransactionDto(
        Long sender,
        Long receiver,
        double amount,
        String comment,
        String  transactionType){
}
