package bank.app.controllers;

import bank.app.annotation.AccountReport;
import bank.app.annotation.AccountReportPdf;
import bank.app.annotation.CreateAccount;
import bank.app.dto.*;
import bank.app.exeption.AccountNotFoundException;
import bank.app.exeption.StartDateAfterEndDateException;
import bank.app.mapper.AccountMapper;
import bank.app.model.entity.Account;
import bank.app.service.AccountService;
import bank.app.service.PdfService;
import bank.app.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static bank.app.exeption.errorMessage.ErrorMessage.START_DATE_AFTER_END_DATE;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Validated
public class AccountController {
    private final AccountService accountService;
    private final PdfService pdfService;
    private final TransactionService transactionService;
    private final AccountMapper accountMapper;

    @Operation(
            summary = "Get basic account information",
            description = "Get basic and full account information by account id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<AccountBasicDto> getAccountInfo(@PathVariable Long id,
                                                          @RequestParam(name = "full", required = false) boolean isFull) {
        return ResponseEntity.ok(isFull ? accountService.getFullAccountInfo(id) : accountService.getBasicAccountInfo(id));
    }

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


    @AccountReportPdf(path = "/{accountId}/pdf")
    public ResponseEntity<byte[]> generatePdfReportBetweenDates(@PathVariable Long accountId,
                                                                @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                                                @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new StartDateAfterEndDateException(START_DATE_AFTER_END_DATE);
        }

        try {
            byte[] pdfBytes = pdfService.generateAccountPdf(accountId, startDate, endDate);

            String filename = "account_report_id_" + accountId + "_" + LocalDate.now() + ".pdf";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                    .body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @AccountReport(path = "/{accountId}/report")
    public ResponseEntity<AccountReportDto> generateStatisticsDataBetweenDates(@PathVariable Long accountId,
                                                                               @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                                                               @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            throw new StartDateAfterEndDateException(START_DATE_AFTER_END_DATE);
        }

        return ResponseEntity.ok(accountService.generateAccountPdfBetweenDates(accountId, startDate, endDate));
    }


    @CreateAccount(path = "/add/user/{userId}")
    public ResponseEntity<AccountBasicDto> add(@PathVariable Long userId, @Valid @RequestBody AccountRequestDto accountRequestDto) {
        AccountBasicDto account = accountService.createNewAccount(accountRequestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @Operation(
            summary = "block account from the database",
            description = "takes the account ID and block it"
    )
    @PutMapping("/{id}/block")
    public ResponseEntity<Void> blockAccount(@PathVariable Long id) {
        accountService.blockAccount(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "unblock account from the database",
            description = "takes the account ID and unblock it if it is blocked"
    )
    @PutMapping("/{id}/unblock")
    public ResponseEntity<Void> unblockAccount(@PathVariable Long id) {
        accountService.unblockAccount(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "delete account from the database",
            description = "takes the account ID and delete it"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteAccount(@PathVariable Long id) throws AccountNotFoundException {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
