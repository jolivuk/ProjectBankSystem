package bank.app.mapper;

import bank.app.dto.PrivateInfoResponseDto;
import bank.app.model.entity.PrivateInfo;
import org.springframework.stereotype.Component;

@Component
public class PrivateInfoMapper {

    private final AddressMapper addressMapper;

    public PrivateInfoMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public PrivateInfoResponseDto toDto(PrivateInfo privateInfo) {
        return new PrivateInfoResponseDto(
                privateInfo.getId(),
                privateInfo.getFirstName(),
                privateInfo.getLastName(),
                privateInfo.getEmail(),
                privateInfo.getPhone(),
                privateInfo.getDateOfBirth(),
                privateInfo.getDocumentType(),
                privateInfo.getDocumentNumber(),
                privateInfo.getComment(),
                privateInfo.getAddress() != null ? addressMapper.toAddressResponseDto(privateInfo.getAddress()) : null
        );
    }
}