package bank.app.util;

import bank.app.dto.AddressRequestDto;
import bank.app.dto.PrivateInfoRequestDto;
import bank.app.model.entity.Address;
import bank.app.model.entity.PrivateInfo;
import bank.app.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class PrivateInfoUtil{

    public static PrivateInfo createPrivateInfo(PrivateInfoRequestDto privateInfoRequestDto, User user) {

        AddressRequestDto addressRequestDto = privateInfoRequestDto.address();
        Address address = createAddress(addressRequestDto);

        log.info("Creating new PrivateInfo for user: {}", user.getId());
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

    public static Address createAddress(AddressRequestDto addressRequestDto) {
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
