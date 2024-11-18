package bank.app.dto;

import bank.app.model.enums.DocumentType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PrivateInfoRequestDto {

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 100, message = "First name should not exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 100, message = "Last name should not exceed 100 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[\\w]{2,}$", message = "Email should be in valid format")
    @Size(max = 100, message = "Email should not exceed 100 characters")
    private String email;

    private String phone;

    private LocalDate dateOfBirth;

    private DocumentType documentType;

    @NotBlank(message = "Document number cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Document number can only contain letters and digits")
    private String documentNumber;

    private String comment;

    private AddressRequestDto address;

}