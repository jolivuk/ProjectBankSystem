package bank.app.util;

import bank.app.model.entity.Transaction;

import java.util.List;

public class TransactionUtil {
    public static Double calculateTotalIncome(List<Transaction> transactions, Long accountId) {
        return transactions.stream()
                .filter(transaction -> transaction.getReceiver().getId().equals(accountId))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public static Double calculateTotalExpenses(List<Transaction> transactions, Long accountId) {
        return transactions.stream()
                .filter(transaction -> transaction.getSender().getId().equals(accountId))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}
