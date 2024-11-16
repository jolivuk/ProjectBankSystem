package bank.app.dto;


public record AddressResponseDto(
        String country,
        String city,
        String postcode,
        String street,
        String houseNumber,
        String info
) {

}
