package bank.app.dto;


public record AddressDto(
        String country,
        String city,
        String postcode,
        String street,
        String houseNumber,
        String info
) {}