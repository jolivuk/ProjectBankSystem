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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressResponseDto that = (AddressResponseDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(country, that.country) &&
                Objects.equals(city, that.city) &&
                Objects.equals(postcode, that.postcode) &&
                Objects.equals(street, that.street) &&
                Objects.equals(houseNumber, that.houseNumber) &&
                Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, postcode, street, houseNumber, info);
    }
}
