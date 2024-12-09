package bank.app.mapper;

import bank.app.dto.TransactionResponseDto;
import bank.app.model.entity.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    public TransactionResponseDto toDto(Transaction transaction) {
        System.out.println("--------------------------------------------------");
        System.out.println(transaction.getTransactionType().getTransactionTypeName());
        System.out.println("--------------------------------------------------");
        return new TransactionResponseDto(transaction.getId(),
                transaction.getSender().getId(),
                transaction.getReceiver().getId(),
                transaction.getAmount(),
                transaction.getComment(),
                transaction.getTransactionDate(),
                transaction.getTransactionStatus().toString(),
                transaction.getTransactionType().getTransactionTypeName()
        );
    }

    public List<TransactionResponseDto> adjustedAmountsInTransactions(List<Transaction> transactions, Long accountId) {
        return transactions.stream()
                .map(transaction -> {
                    Double adjustedAmount = transaction.getSender().getId().equals(accountId)
                            ? -transaction.getAmount()
                            : transaction.getAmount();

                    return new TransactionResponseDto(
                            transaction.getId(),
                            transaction.getSender().getId(),
                            transaction.getReceiver().getId(),
                            adjustedAmount,
                            transaction.getComment(),
                            transaction.getTransactionDate(),
                            transaction.getTransactionStatus().toString(),
                            transaction.getTransactionType().getTransactionTypeName()
                    );
                })
                .collect(Collectors.toList());
    }

}
