package bank.app.controllers;

import bank.app.annotation.CreateTransaction;
import bank.app.dto.TransactionRequestDto;
import bank.app.dto.TransactionResponseDto;
import bank.app.mapper.TransactionMapper;
import bank.app.model.entity.Transaction;
import bank.app.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@RolesAllowed("ADMIN")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Operation(
            summary = "Get transaction by id",
            description = "Returns <TransactionResponseDto> information by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> getInformationById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transactionMapper.toDto(transaction));
    }


    @Operation(
            summary = "Delete transaction by id",
            description = "accept transaction id and return ResponseEntity\\<Void\\>"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @CreateTransaction(path = "/")
    public ResponseEntity<TransactionResponseDto> addTransaction(@RequestBody TransactionRequestDto transactionRequestDto) {
        Transaction transaction = transactionService.addNewTransaction(transactionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionMapper.toDto(transaction));

    }
}
