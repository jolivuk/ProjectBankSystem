package bank.app.service.impl;

import bank.app.dto.AddressRequestDto;
import bank.app.model.entity.Address;
import bank.app.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    @Override
    public Address createAddress(AddressRequestDto addressRequestDto) {
        return Address.builder()
                .country(addressRequestDto.country())
                .city(addressRequestDto.city())
                .street(addressRequestDto.street())
                .houseNumber(addressRequestDto.houseNumber())
                .postcode(addressRequestDto.postcode())
                .info(addressRequestDto.info())
                .build();
    }
}
