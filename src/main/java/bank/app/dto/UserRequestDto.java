package bank.app.dto;

import bank.app.model.enums.Role;
import jakarta.validation.constraints.*;

public record UserRequestDto(
        @NotBlank(message = "First name cannot be blank")
        @Size(max = 50, message = "First name should not exceed 50 characters")
        @Pattern(regexp = "(?!\\d+$)\\w+", message = "Username must contain at least one letter")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        @Pattern(regexp = "[\\w]{8,}",
                message = "Password must be at least 8 characters long")
        String password,


        @NotNull(message = "Role cannot be null")
        String status,

        @NotNull(message = "Role cannot be null")
        Role role,

        @NotNull(message = "Role cannot be null")
        Long manager
) { }
