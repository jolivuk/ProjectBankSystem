package bank.app.service.impl;

import bank.app.dto.AddressRequestDto;
import bank.app.model.entity.Address;
import bank.app.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Override
    public Address createAddress(AddressRequestDto addressRequestDto) {
        log.info("Creating new address for city: {}", addressRequestDto.city());
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
