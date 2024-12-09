package bank.app.annotation;

import bank.app.dto.TransactionRequestDto;
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
        summary = "Create new transaction",
        description = "accepts TransactionRequestDto and returns TransactionResponseDto",
        tags = {"transaction-controller"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Transaction object that needs to be created",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TransactionRequestDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Correct Transaction Example",
                                        value = """
                                                    {
                                                           "sender":5,
                                                           "receiver":4,
                                                           "amount": 500.0,
                                                           "transactionType": "DEPOSIT",
                                                           "comment": "Salary deposit"
                                                      }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Error: not enough balance",
                                        value = """
                                                    {
                                                           "sender":3,
                                                           "receiver":4,
                                                           "amount": 3500.0,
                                                           "transactionType": "DEPOSIT",
                                                           "comment": "Salary deposit"
                                                      }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Error: no receiver exists",
                                        value = """
                                                    {
                                                           "sender":3,
                                                           "receiver":8,
                                                           "amount": 3500.0,
                                                           "transactionType": "DEPOSIT",
                                                           "comment": "Salary deposit"
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
                        responseCode = "500",
                        description = "Not enough balance",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "No account found",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                )

        }
)
public @interface CreateTransaction {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}

