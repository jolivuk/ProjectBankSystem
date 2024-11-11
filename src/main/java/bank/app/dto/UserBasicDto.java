package bank.app.dto;

import bank.app.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserBasicDto(
        @NotBlank(message = "First name cannot be blank")
        @Size(max = 50, message = "First name should not exceed 50 characters")
        @Pattern(regexp = "(?!\\d+$)\\w+", message = "Username must contain at least one letter")
        String username,
        String password,
        String status,
        Role role,
        Long manager
) { }
