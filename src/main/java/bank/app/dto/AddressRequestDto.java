package bank.app.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressRequestDto(
        @NotBlank(message = "Country is required")
        @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters")
        @Pattern(regexp = "^[A-Za-z\\s-]+$", message = "Country should contain only letters, spaces and hyphens")
        String country,

        @NotBlank(message = "City is required")
        @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters")
        @Pattern(regexp = "^[A-Za-z\\s-]+$", message = "City should contain only letters, spaces and hyphens")
        String city,

        @NotBlank(message = "Postcode is required")
        @Pattern(regexp = "^[A-Z0-9\\s-]{3,10}$", message = "Invalid postcode format")
        String postcode,

        @NotBlank(message = "Street is required")
        @Size(min = 2, max = 100, message = "Street name must be between 2 and 100 characters")
        String street,

        @NotBlank(message = "House number is required")
        @Pattern(regexp = "^[0-9A-Za-z-/]{1,10}$", message = "Invalid house number format")
        String houseNumber,

        @Size(max = 500, message = "Additional info must not exceed 500 characters")
        String info
) {

}
