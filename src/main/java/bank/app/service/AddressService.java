package bank.app.service;

import bank.app.dto.AddressDto;
import bank.app.model.entity.Address;

public interface AddressService {
    Address saveAddress(Address address);
    Address getAddressById(Long id);
    Address createAddress(AddressDto addressDto);
}