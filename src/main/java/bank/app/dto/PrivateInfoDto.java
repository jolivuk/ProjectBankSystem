package bank.app.dto;

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
) {}
