package bank.app.model.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_ADMIN( "ADMIN"),
    ROLE_CUSTOMER("CUSTOMER"),
    ROLE_MANAGER("MANAGER"),
    ROLE_BANK("BANK");

    private final String shortRole;
}
