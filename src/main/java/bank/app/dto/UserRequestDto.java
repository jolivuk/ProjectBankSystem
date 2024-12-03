package bank.app.dto;

import bank.app.model.enums.Role;
import bank.app.model.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

        @Enumerated(EnumType.STRING)
        @NotNull(message = "Status cannot be null")
        Status status,

        @Enumerated(EnumType.STRING)
        @NotNull(message = "Role cannot be null")
        Role role,

        Long manager
) { }
