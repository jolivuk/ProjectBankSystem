package bank.app.dto;

import java.util.Objects;

public record AddressResponseDto(
        Long id,
        String country,
        String city,
        String postcode,
        String street,
        String houseNumber,
        String info
) {
}
