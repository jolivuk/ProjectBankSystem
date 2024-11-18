package bank.app.dto;


public record UserResponseDto(
        Long id,
        String username,
        String password,
        String status,
        String role,
        Long manager,
        PrivateInfoResponseDto privateResponseInfo
) {
}


