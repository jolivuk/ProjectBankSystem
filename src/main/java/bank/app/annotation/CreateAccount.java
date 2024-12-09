package bank.app.annotation;

import bank.app.dto.AddressRequestDto;
import bank.app.dto.UserRequestDto;
import bank.app.exeption.handler.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.POST)
@Operation(
        summary = "Create new account",
        description = "Create a new account for a specific user",
        tags = {"account-controller"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Account object that needs to be created",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = AddressRequestDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Correct Account Example",
                                        value = """
                                                {
                                                  "status": "ACTIVE",
                                                  "balance": 0,
                                                  "iban": "DE44123456789012345678",
                                                  "swift": "DEUTDEFFXXX"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Error: wrong format IBAN",
                                        value = """
                                                {
                                                  "status": "ACTIVE",
                                                  "balance": 0,
                                                  "iban": "DE4№",
                                                  "swift": "DEUTDEFFXXX"
                                                }
                                                """
                                )

                        }
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Transaction created",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UserRequestDto.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Wrong format IBAN",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                )
        }
)


public @interface CreateAccount {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}

