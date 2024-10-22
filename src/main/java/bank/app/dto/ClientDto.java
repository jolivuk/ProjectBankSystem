package bank.app.dto;

public record ClientDto(String firstName,
                        String lastName,
                        String email,
                        String username,
                        String password,
                        String telephone) {
}

