package bank.app.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "bank system project",
                description = "study project", version = "1.0.0",
                contact = @Contact(
                        name = "Ramiz Alasgarov,Nina Janeckova",
                        email = "ramizAlasgarov@gmx.de,ninajaneckova@gmail.com"
                )
        ),
        security = @SecurityRequirement(name = "BearerAuth")
)
@SecurityScheme( name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Enter your JWT token here" )
public class OpenApiConfig {

}
