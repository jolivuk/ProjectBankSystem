package bank.app.controllers;

import bank.app.model.entity.Transaction;
import bank.app.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getInformationById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAll() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return transactions.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    @GetMapping("/account/{accountId}/last-month")
    public ResponseEntity<List<Transaction>> getTransactionsLastMonthByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsLastMonthByAccountId(accountId);
        return transactions.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
