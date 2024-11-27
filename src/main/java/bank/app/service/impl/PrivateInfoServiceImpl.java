package bank.app.service.impl;

import bank.app.dto.AddressRequestDto;
import bank.app.dto.PrivateInfoRequestDto;
import bank.app.model.entity.Address;
import bank.app.model.entity.PrivateInfo;
import bank.app.model.entity.User;
import bank.app.service.AddressService;
import bank.app.service.PrivateInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrivateInfoServiceImpl implements PrivateInfoService {
    private final AddressService addressService;

    @Override
    public PrivateInfo createPrivateInfo(PrivateInfoRequestDto privateInfoRequestDto, User user) {

        AddressRequestDto addressRequestDto = privateInfoRequestDto.address();
        Address address = addressService.createAddress(addressRequestDto);

        PrivateInfo privateInfo = PrivateInfo.builder()
                .firstName(privateInfoRequestDto.firstName())
                .lastName(privateInfoRequestDto.lastName())
                .email(privateInfoRequestDto.email())
                .phone(privateInfoRequestDto.phone())
                .dateOfBirth(privateInfoRequestDto.dateOfBirth())
                .documentType(privateInfoRequestDto.documentType())
                .documentNumber(privateInfoRequestDto.documentNumber())
                .comment(privateInfoRequestDto.comment())
                .user(user)
                .address(address)
                .build();

        address.setPrivateInfo(privateInfo);
        return privateInfo;
    }

}
