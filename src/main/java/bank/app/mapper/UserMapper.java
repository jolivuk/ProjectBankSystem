package bank.app.mapper;

import bank.app.dto.UserResponseDto;
import bank.app.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PrivateInfoMapper privateInfoMapper;

    public UserMapper(PrivateInfoMapper privateInfoMapper) {
        this.privateInfoMapper = privateInfoMapper;
    }

    public UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getStatus().toString(),
                user.getRole().toString(),
                user.getManager() != null ? user.getManager().getId() : null,
                user.getPrivateInfo() != null ? privateInfoMapper.toDto(user.getPrivateInfo()) : null
        );
    }
}
