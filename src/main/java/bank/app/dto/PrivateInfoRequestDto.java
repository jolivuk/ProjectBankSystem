package bank.app.dto;

import bank.app.model.enums.DocumentType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PrivateInfoRequestDto (
        @NotBlank(message = "First name cannot be blank")
        @Size(max = 100, message = "First name should not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "First name can only contain letters, spaces and hyphens")
    String firstName,

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 100, message = "Last name should not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "Last name can only contain letters, spaces and hyphens")
    String lastName,

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[\\w]{2,}$", message = "Email should be in valid format")
    @Size(max = 100, message = "Email should not exceed 100 characters")
    String email,

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits")
    String phone,

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    LocalDate dateOfBirth,

    @NotNull(message = "Document type cannot be null")
    DocumentType documentType,

    @NotBlank(message = "Document number cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Document number can only contain letters and digits")
    @Size(min = 5, max = 20, message = "Document number must be between 5 and 20 characters")
    String documentNumber,

    @Size(max = 500, message = "Comment should not exceed 500 characters")
    String comment,

    AddressRequestDto address) {

    @Override
    public String toString() {
        return "PrivateInfoRequestDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", documentType=" + documentType +
                ", documentNumber='" + documentNumber + '\'' +
                ", comment='" + comment + '\'' +
                ", address=" + address +
                '}';
    }
}