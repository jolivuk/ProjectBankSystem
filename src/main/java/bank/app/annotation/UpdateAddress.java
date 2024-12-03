package bank.app.annotation;

import bank.app.dto.AddressRequestDto;
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
                description = "Address object that needs to be created",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = AddressRequestDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Correct Update Address Example",
                                        value = """
                                                {
                                                     "country": "USA",
                                                     "street": "Main St22",
                                                     "city": "New York",
                                                     "houseNumber": "12311",
                                                     "postcode": "10001",
                                                     "info": ""
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Error Example: validation state bad symbols",
                                        value = """
                                                {
                                                     "country": "1561F",
                                                     "street": "Main St22",
                                                     "city": "New York",
                                                     "houseNumber": "12311",
                                                     "postcode": "10001",
                                                     "info": ""
                                                }
                                                """
                                )
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
                        description = "Data integrity violation: bad characters in country name",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                )

        }
)

public @interface UpdateAddress {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
