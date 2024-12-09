package bank.app.annotation;

import bank.app.exeption.handler.ResponseExceptionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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
@RequestMapping(method = RequestMethod.GET)
@Operation(
        summary = "Get Account report in PDF format by dates",
        description = "Get all transactions this month and all sum",
        parameters = {
                @Parameter(
                        name = "accountId",
                        description = "ID of the account",
                        example = "3"
                ),
                @Parameter(
                        name = "startDate",
                        description = "Start date of the report (format: dd-MM-yyyy)",
                        example = "01-01-2024"
                ),
                @Parameter(
                        name = "endDate",
                        description = "End date of the report (format: dd-MM-yyyy)",
                        example = "08-12-2024"
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Account ReportPdf created",
                        content = @Content(mediaType = "application/pdf")
                ),
                @ApiResponse(
                        responseCode = "409",
                        description = "No user with id",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ResponseExceptionHandler.class)
                        )
                )
        }
)
public @interface AccountReportPdf {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
