package bank.app.service;

import bank.app.dto.AddressRequestDto;
import bank.app.dto.AddressResponseDto;

public interface AddressService {
    AddressResponseDto getAddressById(Long id);
    AddressResponseDto createAddress(AddressRequestDto addressRequestDto);
}
