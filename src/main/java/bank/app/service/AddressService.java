package bank.app.service;

import bank.app.dto.AddressRequestDto;
import bank.app.model.entity.Address;

public interface AddressService {
    Address createAddress(AddressRequestDto addressRequestDto);
}
