package bank.app.controllers;

import bank.app.dto.*;
import bank.app.mapper.AccountMapper;
import bank.app.model.entity.Account;
import bank.app.service.AccountService;
import bank.app.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    private final TransactionService transactionService;
    private final AccountMapper accountMapper;

    @Operation(
            summary = "Get basic account information",
            description = "Get basic and full account information by account id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<AccountBasicDto> getAccountInfo(@PathVariable Long id,
                                                          @RequestParam (name = "full",required = false) boolean isFull) {
        return ResponseEntity.ok(isFull ? accountService.getFullAccountInfo(id) : accountService.getBasicAccountInfo(id));}


    @Operation(
            summary = "Get basic account information",
            description = "Get basic or full account information by account ID and return List<AccountBasicDto>"
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountBasicDto>> findAccountsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.findByUserId(userId));
    }

    @Operation(
            summary = "find the user who is the bank",
            description = "accepts nothing and returns UserResponseDto"
    )
    @GetMapping("/bank")
    public ResponseEntity<AccountFullDto> findBankAccount() {
        Account account = accountService.getBankAccount();
        return ResponseEntity.ok(accountMapper.toFullDto(account));
    }

    @Operation(
            summary = "Get all transactions in the bank by id",
            description = "takes an accountId and returns List<TransactionResponseDto>"
    )
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionResponseDto>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<TransactionResponseDto> transactions = transactionService.getTransactionsByAccountId(accountId);
        return transactions.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @Operation(
            summary = "Get last month transactions",
            description = "Get all transactions for a specific account within the last month"
    )
    @GetMapping("/{accountId}/transactions/last-month")
    public ResponseEntity<List<TransactionResponseDto>> getLastMonthTransactionsByAccount(@PathVariable Long accountId) {
        List<TransactionResponseDto> transactions = transactionService.getTransactionsLastMonthByAccountId(accountId);
        return transactions.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @Operation(
            summary = "Create new account",
            description = "Create a new account for a specific user"
    )
    @PostMapping("/add/user/{userId}")
    public ResponseEntity<AccountBasicDto> add(@PathVariable Long userId,@RequestBody AccountRequestDto accountRequestDto){
        AccountBasicDto account = accountService.createNewAccount(accountRequestDto,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);

    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> softDeleteAccount(@PathVariable Long id) {
//        accountService.softDeleteAccount(id);
//        return ResponseEntity.noContent().build();
//    }


}
