package bank.app.dto;


import java.util.Objects;

public record UserResponseDto(
        Long id,
        String username,
        String password,
        String status,
        String role,
        Long manager,
        PrivateInfoResponseDto privateInfoResponse
) {
}


