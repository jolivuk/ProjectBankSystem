package bank.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login_info")
@RequiredArgsConstructor
public class LoginController {

    @GetMapping
    public String getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return "Current user: " + authentication.getName() +
                    "\nRoles: " + authentication.getAuthorities().toString();
        }

        return "No user authenticated";
    }

    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    public Map<String, Object> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> userDetails = new HashMap<>();

        userDetails.put("username", authentication.getName());
        userDetails.put("authorities", authentication.getAuthorities());
        userDetails.put("isAuthenticated", authentication.isAuthenticated());
        userDetails.put("principal", authentication.getPrincipal().toString());

        return userDetails;
    }

}
