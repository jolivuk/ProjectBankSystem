package bank.app.dto;

import bank.app.model.entity.PrivateInfo;
import bank.app.model.enums.DocumentType;

import java.time.LocalDate;

public record PrivateInfoDto(
        String firstName,
        String lastName,
        String email,
        String phone,
        LocalDate dateOfBirth,
        DocumentType documentType,
        String documentNumber,
        String comment,
        AddressDto address
) {
    public static PrivateInfoDto fromPrivateInfo(PrivateInfo privateInfo) {
        return new PrivateInfoDto(
                privateInfo.getFirstName(),
                privateInfo.getLastName(),
                privateInfo.getEmail(),
                privateInfo.getPhone(),
                privateInfo.getDateOfBirth(),
                privateInfo.getDocumentType(),
                privateInfo.getDocumentNumber(),
                privateInfo.getComment(),
                privateInfo.getAddress() != null ? AddressDto.fromAddress(privateInfo.getAddress()) : null
        );
    }
}

