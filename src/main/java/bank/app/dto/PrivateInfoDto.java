package bank.app.dto;

import bank.app.model.entity.PrivateInfo;
import bank.app.model.enums.DocumentType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PrivateInfoDto(
        @NotBlank(message = "First name cannot be blank")
        @Size(max = 50, message = "First name should not exceed 50 characters")
        @Pattern(regexp = "^[A-Za-z]+$", message = "First name should contain only letters")
        String firstName,
        @NotBlank(message = "Last name name cannot be blank")
        @Size(max = 50, message = "Last name should not exceed 50 characters")
        @Pattern(regexp = "^[A-Za-z]+$", message = "Last name name should contain only letters")
        String lastName,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[\\w]{2,}$", message = "Email should be in valid format")
        @Size(max = 100, message = "Email should not exceed 100 characters")
        String email,
        String phone,
        LocalDate dateOfBirth,
        DocumentType documentType,
        String documentNumber,
        String comment,
        AddressCreateRequestDto address
) {
    public static PrivateInfoDto fromPrivateInfo(PrivateInfo privateInfo) { // todo == privateInfoMapper
        return null;
//        return new PrivateInfoDto(
//                privateInfo.getFirstName(),
//                privateInfo.getLastName(),
//                privateInfo.getEmail(),
//                privateInfo.getPhone(),
//                privateInfo.getDateOfBirth(),
//                privateInfo.getDocumentType(),
//                privateInfo.getDocumentNumber(),
//                privateInfo.getComment(),
//                privateInfo.getAddress() != null ? AddressCreateRequestDto.fromAddress(privateInfo.getAddress()) : null
//        );
    }
}

