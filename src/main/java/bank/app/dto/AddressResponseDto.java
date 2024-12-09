package bank.app.dto;

public record AddressResponseDto(
        Long id,
        String country,
        String city,
        String postcode,
        String street,
        String houseNumber,
        String info
) {
    @Override
    public String toString() {
        return houseNumber + " " + street + " " + postcode +
                " " + city + " " + country;
    }
}
