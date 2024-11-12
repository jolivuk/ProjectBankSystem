package bank.app.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionDto{
        private final Long sender;
        private final Long receiver;
        private final double amount;
        private final String comment;
        private final String  transactionType;

    public TransactionDto(Long sender, Long receiver, double amount, String comment, String transactionType) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.comment = comment;
        this.transactionType = transactionType;
    }

}
