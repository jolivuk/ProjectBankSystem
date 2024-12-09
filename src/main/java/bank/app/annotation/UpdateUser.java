package bank.app.annotation;

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
@RequestMapping(method = RequestMethod.PUT)
@Operation(
        summary = "updates the user  ",
        description = "accepts UserRequestDto and returns UserResponseDto",
        tags = {"user-controller"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "User object that needs to be created",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserRequestDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Correct User Example",
                                        value = """
                                                            {
                                                                "username": "NewName",
                                                                "password": "NewPassword",
                                                                "status": "ACTIVE",
                                                                "role": "ROLE_CUSTOMER",
                                                                "manager" : "3"
                                                            }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Error Example: username already exists",
                                        value = """
                                                            {
                                                                "username": "customer3",
                                                                "password": "NewPassword",
                                                                "status": "ACTIVE",
                                                                "role": "ROLE_CUSTOMER",
                                                                "manager" : "3"
                                                            }
                                                """),
                                @ExampleObject(
                                        name = "Error Example: no role",
                                        value = """
                                                            {
                                                                "username": "NewName",
                                                                "password": "NewPassword",
                                                                "status": "ACTIVE",
                                                                "role": "CUSTOMER",
                                                                "manager" : "3"
                                                            }
                                                """)
                        }
                )
        ),

        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "User updated",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UserRequestDto.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Data integrity violation: username already exists",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                )
        }
)

public @interface UpdateUser {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
