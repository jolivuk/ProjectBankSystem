package bank.app.annotation;

import bank.app.dto.PrivateInfoRequestDto;
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
        summary = "update private info for user by id  ",
        description = "accepts PrivateInfoRequestDto and returns PrivateInfoResponseDto",
        tags = {"user-controller"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "PrivateInfo object that needs to be created",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PrivateInfoRequestDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Default PrivateInfo Example",
                                        value = """
                                                {
                                                      "firstName": "Vladimir",
                                                      "lastName": "Ivanov",
                                                      "email": "vivanov@example.com",
                                                      "phone": "+4915257776666",
                                                      "dateOfBirth": "1990-01-01",
                                                      "documentType": "PASSPORT_EU",
                                                      "documentNumber": "1234567890",
                                                      "comment": "Test comment",
                                                      "address": null
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Error Example: email already exists",
                                        value = """
                                                {
                                                      "firstName": "Vladimir",
                                                      "lastName": "Ivanov",
                                                      "email": "erika@example.com",
                                                      "phone": "+4915257776666",
                                                      "dateOfBirth": "1990-01-01",
                                                      "documentType": "PASSPORT_EU",
                                                      "documentNumber": "1234567890",
                                                      "comment": "Test comment",
                                                      "address": null
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Error Example: no documentType",
                                        value = """
                                                {
                                                      "firstName": "Vladimir",
                                                      "lastName": "Ivanov",
                                                      "email": "vivanov@example.com",
                                                      "phone": "+4915257776666",
                                                      "dateOfBirth": "1990-01-01",
                                                      "documentType": "PASSPORT",
                                                      "documentNumber": "1234567890",
                                                      "comment": "Test comment",
                                                      "address": null
                                                }
                                                """)
                        }
                )
        ),

        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK. Private info updated",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UserRequestDto.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "409",
                        description = "Data integrity violation: email already exists",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Data integrity violation: Forbidden",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                )
        }
)

public @interface UpdatePrivateInfo {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
