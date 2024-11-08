package bank.app.dto;

import bank.app.model.entity.User;
import bank.app.model.enums.Role;

public record UserManagerDto ( Long id, String username,Role role) {
        public static UserManagerDto fromUser(User user) {
            return new UserManagerDto(
                    user.getId(),
                    user.getUsername(),
                    user.getRole()
            );
        }

    }
