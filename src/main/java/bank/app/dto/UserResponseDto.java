package bank.app.dto;


import java.util.Objects;

public record UserResponseDto(
        Long id,
        String username,
        String password,
        String status,
        String role,
        Long manager,
        PrivateInfoResponseDto privateResponseInfo
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponseDto that = (UserResponseDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(status, that.status) &&
                Objects.equals(role, that.role) &&
                Objects.equals(manager, that.manager) &&
                Objects.equals(privateResponseInfo, that.privateResponseInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, status, role, manager, privateResponseInfo);
    }

}


