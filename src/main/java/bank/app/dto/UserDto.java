package bank.app.dto;

public record UserDto(
        String username,
        String password,
        String status,
        String privateInfo,
        String role,
        Long manager
) {


}
