package bank.app.service;

import bank.app.dto.AddressCreateRequestDto;
import bank.app.dto.AddressResponseDto;
import bank.app.model.entity.Address;

public interface AddressService {
    AddressResponseDto getAddressById(Long id);
    AddressResponseDto createAddress(AddressCreateRequestDto addressCreateRequestDto);
}
