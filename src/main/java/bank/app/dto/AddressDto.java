package bank.app.dto;


import bank.app.model.entity.Address;

public record AddressDto(
        String country,
        String city,
        String postcode,
        String street,
        String houseNumber,
        String info
) {
    public static AddressDto fromAddress(Address address) {
        return new AddressDto(
                address.getCountry(),
                address.getCity(),
                address.getPostcode(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getInfo()
        );
    }
}
