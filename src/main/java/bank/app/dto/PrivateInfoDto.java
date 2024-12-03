package bank.app.dto;

import bank.app.model.enums.DocumentType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PrivateInfoDto(
        @NotBlank(message = "First name cannot be blank")
        @Size(max = 50, message = "First name should not exceed 50 characters")
        @Pattern(regexp = "^[A-Za-z]+$", message = "First name should contain only letters")
        String firstName,

        @NotBlank(message = "Last name name cannot be blank")
        @Size(max = 50, message = "Last name should not exceed 50 characters")
        @Pattern(regexp = "^[A-Za-z]+$", message = "Last name should contain only letters")
        String lastName,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[\\w]{2,}$", message = "Email should be in valid format")
        @Size(max = 100, message = "Email should not exceed 100 characters")
        String email,

        @NotBlank(message = "Phone number cannot be blank")
        @Pattern(regexp = "^\\+?\\d{9,15}$", message = "Phone number should be in valid format (e.g., +1234567890 or 1234567890)")
        String phone,

        @NotNull(message = "Date of birth cannot be null")
        @Past(message = "Date of birth must be a past date")
        LocalDate dateOfBirth,

        @NotNull(message = "Document type cannot be null")
        DocumentType documentType,

        @NotBlank(message = "Document number cannot be blank")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Document number can only contain letters and digits")
        String documentNumber,

        String comment,
        AddressRequestDto address
) {

}

