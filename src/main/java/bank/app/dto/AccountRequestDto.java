package bank.app.dto;

import bank.app.model.enums.Status;
import jakarta.validation.constraints.*;

public record AccountRequestDto(

        Status status,

        Double balance,

        @NotBlank(message = "IBAN cannot be blank")
        @Size(min = 15, max = 34, message = "IBAN must be between 15 and 34 characters")
        @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}$",
                message = "Invalid IBAN format. It must start with two letters, followed by two digits, " +
                        "and contain 11 to 30 alphanumeric characters.")
        String iban,

        @Pattern(regexp = "^[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z0-9]{3})?$",
                message = "Invalid SWIFT/BIC code")
        String swift) {

}
