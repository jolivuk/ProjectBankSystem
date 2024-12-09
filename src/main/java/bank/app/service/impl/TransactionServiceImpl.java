package bank.app.service.impl;

import bank.app.dto.TransactionRequestDto;
import bank.app.dto.TransactionResponseDto;
import bank.app.exeption.BalanceException;
import bank.app.exeption.TransactionNotFoundException;
import bank.app.exeption.TransactionTypeException;
import bank.app.exeption.errorMessage.ErrorMessage;
import bank.app.mapper.TransactionMapper;
import bank.app.model.entity.Account;
import bank.app.model.entity.Transaction;
import bank.app.model.entity.TransactionType;
import bank.app.model.enums.TransactionStatus;
import bank.app.repository.AccountRepository;
import bank.app.repository.TransactionRepository;
import bank.app.repository.TransactionTypeRepository;
import bank.app.service.AccountService;
import bank.app.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static bank.app.exeption.errorMessage.ErrorMessage.NOT_ENOUGH_BALANCE;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public Transaction getTransactionById(Long id) {
        log.info("Attempting to get transaction with ID: {}", id);
        if (id == null) {
            log.error("Transaction ID is null");
            throw new IllegalArgumentException(ErrorMessage.INVALID_TRANSATION_ID);
        }
        return transactionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Transaction not found with ID: {}", id);
                    return new TransactionNotFoundException(ErrorMessage.TRANSATION_NOT_FOUND + id);
                });
    }

    @Override
    public void delete(Long id) {
        log.info("Starting to delete transaction with ID: {}", id);
        transactionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot delete - transaction not found with ID: {}", id);
                    return new TransactionNotFoundException(ErrorMessage.TRANSATION_NOT_FOUND + id);
                });
        transactionRepository.deleteById(id);
        log.info("Successfully deleted transaction with ID: {}", id);
    }


    @Override
    public List<TransactionResponseDto> getTransactionsByAccountId(Long accountId) {
        List<Transaction> listTransactions = transactionRepository.findBySenderIdOrReceiverId(accountId, accountId);
        return transactionMapper.adjustedAmountsInTransactions(listTransactions, accountId);
    }

    @Override
    public List<TransactionResponseDto> getTransactionsLastMonthByAccountId(Long accountId) {
        log.info("Fetching transactions for account ID: {}", accountId);
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(30);

        List<Transaction> transactions = transactionRepository.findByDateRangeAndAccount(
                startDate,
                endDate,
                accountId
        );

        log.info("Found {} transactions for account ID: {}", transactions.size(), accountId);
        return transactionMapper.adjustedAmountsInTransactions(transactions, accountId);
    }

    @Override
    public Transaction addNewTransaction(TransactionRequestDto transactionRequestDto) {
        log.info("Starting new transaction from account {} to account {}, amount: {}",
                transactionRequestDto.sender(), transactionRequestDto.receiver(), transactionRequestDto.amount());

        Account sender = accountService.getAccountById(transactionRequestDto.sender());
        Account receiver = accountService.getAccountById(transactionRequestDto.receiver());
        Account bank = accountService.getBankAccount();

        accountService.checkAccount(bank);
        accountService.checkAccount(sender);
        accountService.checkAccount(receiver);

        TransactionType transactionType = transactionTypeRepository
                .findByTransactionTypeName(transactionRequestDto.transactionType())
                .orElseThrow(() -> {
                    log.error("Invalid transaction type: {}", transactionRequestDto.transactionType());
                    return new TransactionTypeException(ErrorMessage.INVALID_TRANSATION_TYPE);
                });


        Transaction transaction = new Transaction(sender, receiver, transactionRequestDto.amount(),
                transactionRequestDto.comment(), TransactionStatus.INITIALIZED, transactionType);

        transaction.setTransactionStatus(TransactionStatus.PROCESSING);
        transaction.setFee(transactionType.getTransactionFee() * transactionRequestDto.amount() / 100);
        transactionRepository.save(transaction);

        double totalSum = transactionRequestDto.amount() + transaction.getFee();

        if (totalSum > sender.getBalance()) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setComment(transactionRequestDto.comment() + " - Not enough balance");
            transactionRepository.save(transaction);
            log.error("Transaction failed - insufficient balance. Account: {}, Required: {}, Available: {}",
                    sender.getId(), totalSum, sender.getBalance());
            throw new BalanceException(NOT_ENOUGH_BALANCE + transactionRequestDto.sender());
        }

        sender.setBalance(sender.getBalance() - totalSum);
        receiver.setBalance(receiver.getBalance() + transactionRequestDto.amount());
        bank.setBalance(bank.getBalance() + transaction.getFee());

        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        Transaction savedTransaction = transactionRepository.save(transaction);

        accountRepository.save(sender);
        accountRepository.save(receiver);
        accountRepository.save(bank);

        log.info("Transaction completed successfully. ID: {}, From: {}, To: {}, Amount: {}, Fee: {}",
                savedTransaction.getId(), sender.getId(), receiver.getId(),
                transactionRequestDto.amount(), transaction.getFee());

        return savedTransaction;
    }
}
