package bank.app.service.impl;

import bank.app.dto.AddressDto;
import bank.app.model.entity.Address;
import bank.app.repository.AddressRepository;
import bank.app.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }


    @Override
    public Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found with id: " + id));
    }

    @Override
    public Address createAddress(AddressDto addressDto) {
        return addressRepository.save(Address.builder().country(addressDto.country())
                .city(addressDto.city())
                .postcode(addressDto.postcode())
                .street(addressDto.street())
                .houseNumber(addressDto.houseNumber())
                .info(addressDto.info())
                .build());
    }
}
