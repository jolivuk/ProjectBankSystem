package bank.app.dto;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    // Метод для проверки пароля
    public boolean matchesPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, this.password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) return false;
        UserResponseDto that = (UserResponseDto) o;

        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(status, that.status) &&
                Objects.equals(role, that.role) &&
                Objects.equals(manager, that.manager) &&
                Objects.equals(privateInfoResponse, that.privateInfoResponse) &&
                passwordEncoder.matches(password,that.password );
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, username, status, role, manager, privateInfoResponse, password);
    }

}


