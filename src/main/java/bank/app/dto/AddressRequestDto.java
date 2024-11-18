package bank.app.dto;


public record AddressRequestDto(
        String country,
        String city,
        String postcode,
        String street,
        String houseNumber,
        String info
) {

}
