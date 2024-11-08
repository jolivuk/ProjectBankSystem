package bank.app.dto;


import bank.app.model.entity.User;
import bank.app.model.enums.Role;

import java.time.LocalDateTime;

public record UserFullDto(
        String username,
        String password,
        String status,
        Role role,
        Long manager,
        PrivateInfoDto privateInfo
) {
    public static UserFullDto fromUser(User user) {
        return new UserFullDto(
                user.getUsername(),
                user.getPassword(),
                user.getStatus().toString(),
                user.getRole(),
                user.getManager() != null ? user.getManager().getId() : null,
                user.getPrivateInfo() != null ? PrivateInfoDto.fromPrivateInfo(user.getPrivateInfo()) : null
        );
    }
    }


