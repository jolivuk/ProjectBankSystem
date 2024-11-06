package bank.app.controllers;

import bank.app.service.AccountService;
import bank.app.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
//    private final AccountService accountService;
//
//    @Autowired
//    public AccountController(AccountService accountService) {
//        this.accountService = accountService;
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> softDeleteAccount(@PathVariable Long id) {
//        accountService.softDeleteAccount(id);
//        return ResponseEntity.noContent().build();
//    }


}
