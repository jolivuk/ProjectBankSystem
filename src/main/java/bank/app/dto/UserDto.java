package bank.app.dto;

import bank.app.model.enums.Role;

public record UserDto(
        String username,
        String password,
        String status,
        Role role,
        Long manager
) { }
