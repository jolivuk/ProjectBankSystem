package bank.app.mapper;

import bank.app.dto.AddressRequestDto;
import bank.app.dto.AddressResponseDto;
import bank.app.model.entity.Address;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class AddressMapper {

    public Address toAddress(AddressRequestDto dto) {
        return Address.builder().country(dto.country())
                .city(dto.city())
                .postcode(dto.postcode())
                .street(dto.street())
                .houseNumber(dto.houseNumber())
                .info(dto.info())
                .build();
    }

    public AddressResponseDto toAddressResponseDto(Address address) {
        return new AddressResponseDto(
                address.getCountry(),
                address.getCity(),
                address.getPostcode(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getInfo()
        );

    }
}
