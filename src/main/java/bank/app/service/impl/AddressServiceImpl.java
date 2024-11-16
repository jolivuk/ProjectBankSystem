package bank.app.service.impl;

import bank.app.dto.AddressCreateRequestDto;
import bank.app.dto.AddressResponseDto;
import bank.app.mapper.AddressMapper;
import bank.app.model.entity.Address;
import bank.app.repository.AddressRepository;
import bank.app.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;


    @Override
    public AddressResponseDto getAddressById(Long id) {
        return addressRepository.findById(id)
                .map(addressMapper::toAddressResponseDto)
                .orElseThrow(() -> new IllegalArgumentException("Address not found with id: " + id));
    }

    @Override
    public AddressResponseDto createAddress(AddressCreateRequestDto dto) {
        Address created = addressRepository.save(addressMapper.toAddress(dto));
       return addressMapper.toAddressResponseDto(created);
    }
}
