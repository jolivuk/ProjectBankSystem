package bank.app.dto;

import bank.app.model.enums.DocumentType;

import java.time.LocalDate;

public record PrivateInfoResponseDto (
    Long id,
    String firstName,
    String lastName,
    String email,
    String phone,
    LocalDate dateOfBirth,
    DocumentType documentType,
    String documentNumber,
    String comment,
    AddressResponseDto address)
{

    @Override
    public String toString() {
        return "PrivateInfoDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", documentType=" + documentType +
                ", documentNumber='" + documentNumber + '\'' +
                ", comment='" + comment + '\'' +
                ", address=" + (address != null ? address.toString() : "null") +
                '}';
    }
}