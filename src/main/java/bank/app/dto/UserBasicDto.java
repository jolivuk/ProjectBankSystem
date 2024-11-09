package bank.app.dto;

import bank.app.model.enums.Role;

public record UserBasicDto(
        String username,
        String password,
        String status,
        Role role,
        Long manager
) { }
