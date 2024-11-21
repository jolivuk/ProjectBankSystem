package bank.app.dto;

import bank.app.model.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PrivateInfoResponseDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private DocumentType documentType;
    private String documentNumber;
    private String comment;
    private AddressResponseDto address;

    // Getters and setters (or use Lombok annotations)

    public PrivateInfoResponseDto(String firstName, String lastName, String email, String phone, LocalDate dateOfBirth,
                                  DocumentType documentType, String documentNumber, String comment, AddressResponseDto address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.comment = comment;
        this.address = address;
    }
}