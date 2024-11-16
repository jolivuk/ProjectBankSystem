package bank.app.dto;


import bank.app.model.entity.Address;

public record AddressCreateRequestDto(
        String country,
        String city,
        String postcode,
        String street,
        String houseNumber,
        String info
) {

}
