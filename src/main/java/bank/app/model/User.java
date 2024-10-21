package bank.app.model;

import jdk.jfr.DataAmount;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String telephone;

}
