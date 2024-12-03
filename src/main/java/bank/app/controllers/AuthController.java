package bank.app.controllers;

import bank.app.exeption.errorMessage.ErrorMessage;
import bank.app.security.JwtTokenHelper;
import bank.app.service.AuthenticationService;
import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private AuthenticationService authenticationService;
    private AuthenticationManager authenticationManager;
    private JwtTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        if (!authenticationService.authenticateUser(username, password)) {
            throw new RuntimeException(ErrorMessage.INVALID_CREDENTIALS);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            UserDetails userDetails = (UserDetails) authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)).getPrincipal();

            return jwtTokenHelper.generateToken(userDetails.getUsername(), userDetails.getAuthorities().toString());
        } catch (AuthenticationException e) {
            throw new RuntimeException(ErrorMessage.INVALID_CREDENTIALS);
        }
    }
}
