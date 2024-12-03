package bank.app.annotation;

import bank.app.dto.PrivateInfoRequestDto;
import bank.app.dto.UserRequestDto;
import bank.app.exeption.handler.ResponseExceptionHandler;
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
        summary = "adds the user's private field to the database ",
        description = "takes PrivateInfoRequestDto and returns UserResponseDto" +
                " which contains updated information about the user",
        tags = {"user-controller"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "User object that needs to be created",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PrivateInfoRequestDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Default User Example - Response 201",
                                        value = """
                                {
                                     "firstName": "Vladimir",
                                     "lastName": "Ivanov",
                                     "email": "vivanov@example.com",
                                     "phone": "+4915257666666",
                                     "dateOfBirth": "1990-01-01",
                                     "documentType": "PASSPORT_EU",
                                     "documentNumber": "1234567890",
                                     "comment": "string",
                                     "address": {
                                                "country": "USA",
                                                "city": "New York",
                                                "postcode": "10001",
                                                "street": "Main St",
                                                "houseNumber": "123",
                                                "info": "string"
                                     }
                                }
                                """
                                ),
                                @ExampleObject(
                                        name = "User with existed email Example - Response 403 ",
                                        value = """
                                {
                                     "firstName": "Vladimir",
                                     "lastName": "Ivanov",
                                     "email": "john.doe@example.com",
                                     "phone": "456456484565",
                                     "dateOfBirth": "1990-01-01",
                                     "documentType": "PASSPORT_EU",
                                     "documentNumber": "1234567890",
                                     "comment": "string",
                                     "address": {
                                                "country": "USA",
                                                "city": "New York",
                                                "postcode": "10001",
                                                "street": "Main St",
                                                "houseNumber": "123",
                                                "info": "string"
                                     }
                                }
                                """
                                )
                        }
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "PrivateInfo created",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UserRequestDto.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "409",
                        description = "Data integrity violation: Email already exist",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ResponseExceptionHandler.class)
                        )
                )
        }
)
public @interface AddPrivateInfo {
    @AliasFor(annotation = RequestMapping.class,attribute = "path")
    String[] path() default {};
}
