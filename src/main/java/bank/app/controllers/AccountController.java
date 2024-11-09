package bank.app.controllers;

import bank.app.dto.AccountBasicDto;
import bank.app.dto.AccountFullDto;
import bank.app.model.entity.Account;
import bank.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> softDeleteAccount(@PathVariable Long id) {
//        accountService.softDeleteAccount(id);
//        return ResponseEntity.noContent().build();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountBasicDto> getBasicAccountInfo(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getBasicAccountInfo(id));
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<AccountFullDto> getFullAccountInfo(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getFullAccountInfo(id));
    }
}
