package bank.app.service.impl;

import bank.app.dto.PrivateInfoDto;
import bank.app.dto.PrivateInfoRequestDto;
import bank.app.model.entity.Address;
import bank.app.model.entity.PrivateInfo;
import bank.app.model.entity.User;
import bank.app.repository.PrivateInfoRepository;
import bank.app.service.AddressService;
import bank.app.service.PrivateInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrivateInfoServiceImpl implements PrivateInfoService {
    private final PrivateInfoRepository privateInfoRepository;
    private final AddressService addressService;


    @Override
    public PrivateInfo savePrivateInfo(PrivateInfo privateInfo) {
        return privateInfoRepository.save(privateInfo);
    }


    @Override
    public PrivateInfo createPrivateInfo(PrivateInfoRequestDto privateInfoRequestDto, User user) {
        return privateInfoRepository.save(PrivateInfo.builder()
                .firstName(privateInfoRequestDto.getFirstName())
                .lastName(privateInfoRequestDto.getLastName())
                .email(privateInfoRequestDto.getEmail())
                .phone(privateInfoRequestDto.getPhone())
                .dateOfBirth(privateInfoRequestDto.getDateOfBirth())
                .documentType(privateInfoRequestDto.getDocumentType())
                .documentNumber(privateInfoRequestDto.getDocumentNumber())
                .comment(privateInfoRequestDto.getComment())
                .user(user)
                .build());
    }

}
