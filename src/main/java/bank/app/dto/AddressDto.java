package bank.app.dto;

public record AddressDto(
        String street,
        String city,
        String state,
        String postalCode,
        String country
) {}