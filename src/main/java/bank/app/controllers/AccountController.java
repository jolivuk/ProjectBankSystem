package bank.app.controllers;

import bank.app.dto.*;
import bank.app.model.entity.Account;
import bank.app.model.entity.Transaction;
import bank.app.service.AccountService;
import bank.app.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    private final TransactionService transactionService;
    @GetMapping("/{id}")
    public ResponseEntity<AccountBasicDto> getBasicAccountInfo(@PathVariable Long id,
                                                               @RequestParam (name = "full",required = false) boolean isFull) {
        return ResponseEntity.ok(isFull ? accountService.getFullAccountInfo(id) : accountService.getBasicAccountInfo(id));}


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> findAccountsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.findByUserId(userId));
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{accountId}/transactions/last-month")
    public ResponseEntity<List<Transaction>> getLastMonthTransactionsByAccount(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsLastMonthByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/{accountId}/transactions/add")
    public ResponseEntity<Transaction> addTransaction(@PathVariable Long accountId,@RequestBody TransfertDto tranfertDto) {
        Transaction transaction = transactionService.addNewTransaction(accountId,tranfertDto);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/add/user/{userId}")
    public ResponseEntity<Account> add(@PathVariable Long userId,@RequestBody AccountBasicDto accountBasicDto){
        Account account = accountService.createNewAccount(accountBasicDto,userId);
        return ResponseEntity.ok(account);
    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> softDeleteAccount(@PathVariable Long id) {
//        accountService.softDeleteAccount(id);
//        return ResponseEntity.noContent().build();
//    }


}
