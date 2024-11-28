package bank.app.controllers;

import bank.app.dto.*;
import bank.app.model.enums.DocumentType;
import bank.app.model.enums.Status;
import bank.app.model.enums.TransactionTypeName;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/schema.sql")
@Sql("/db/data.sql")
@ActiveProfiles("test")
@WithMockUser(username = "admin", password = "admin", roles = "admin")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBasicAccountInfo() throws Exception {

        Long accountId = 3L;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/{accountId}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        AccountBasicDto actualAccount = objectMapper.readValue(jsonResponse, AccountBasicDto.class);

        AccountBasicDto expectedAccount = new AccountBasicDto(
                3L,
                Status.ACTIVE,
                3000.00,
                "DE89370400440532013858",
                "DEUTDEFF",
                LocalDateTime.of(2024, 11, 21, 11, 05, 00),
                LocalDateTime.of(2024, 11, 21, 11, 05, 00)
        );

        Assertions.assertEquals(actualAccount, expectedAccount);
    }


    @Test
    void getFullAccountInfo() throws Exception {

        AddressResponseDto address = new AddressResponseDto(3L,
                "Germany",
                "Berlin",
                "10115",
                "Alexanderplatz",
                "5",
                null);
        PrivateInfoResponseDto privateInfo = new PrivateInfoResponseDto(3L,
                "Erika",
                "Mustermann",
                "erika@example.com",
                "491234567891",
                LocalDate.of(1985, 5, 10),
                DocumentType.PASSPORT_EU,
                "D87654321",
                null,
                address
        );
        UserResponseDto userResponseDto = new UserResponseDto(
                3L,
                "client1",
                "password123",
                "ACTIVE",
                "CUSTOMER",
                2L,
                privateInfo
        );
        AccountFullDto expectedAccount = new AccountFullDto(
                4L,
                Status.ACTIVE,
                3000.00,
                "DE89370400440532013001",
                "DEUTDEFF",
                LocalDateTime.of(2024, 11, 21, 11, 10, 00),
                LocalDateTime.of(2024, 11, 21, 11, 10, 00),
                userResponseDto);

        Long accountId = 4L;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/{accountId}?full=true", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        AccountFullDto actualAccount = objectMapper.readValue(jsonResponse, AccountFullDto.class);


        Assertions.assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void findAccountsByUserId() throws Exception {

        Long userId = 3L;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        List<AccountBasicDto> actualAccounts = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });


        List<AccountBasicDto> expectedAccounts = List.of(
                new AccountBasicDto(
                        4L,
                        Status.ACTIVE,
                        3000.00,
                        "DE89370400440532013001",
                        "DEUTDEFF",
                        LocalDateTime.of(2024, 11, 21, 11, 10, 00),
                        LocalDateTime.of(2024, 11, 21, 11, 10, 00)),
                new AccountBasicDto(
                        5L,
                        Status.ACTIVE,
                        7000.00,
                        "DE89370400440532013002",
                        "DEUTDEFF",
                        LocalDateTime.of(2024, 11, 21, 11, 15, 00),
                        LocalDateTime.of(2024, 11, 21, 11, 15, 00)
                )
        );

        Assertions.assertEquals(expectedAccounts, actualAccounts);
    }


    @Test
    void findBankAccount() throws Exception {

        UserResponseDto userResponseDto = new UserResponseDto(1L,
                "BANKAccount",
                "password123",
                "ACTIVE",
                "BANK",
                null,
                null);

        AccountFullDto expected = new AccountFullDto(
                1L,
                Status.ACTIVE,
                0.00,
                "DE89370400440532013000",
                "DEUTDEFF",
                LocalDateTime.of(2024, 11, 21, 9, 30, 00),
                LocalDateTime.of(2024, 11, 21, 9, 30, 00),
                userResponseDto
        );


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/bank")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        AccountFullDto actual = objectMapper.readValue(jsonResponse, AccountFullDto.class);


        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getTransactionsByAccountId() throws Exception {

        List<TransactionResponseDto> expectedTransactions = List.of(
                new TransactionResponseDto(
                        1L, 2L, 3L, -200.00, "Monthly transfer",
                        "2024-09-21T11:20",
                        "COMPLETED", TransactionTypeName.TRANSFER
                ),
                new TransactionResponseDto(2L, 2L, 3L, -500.00, "ATM withdrawal",
                        "2024-11-21T11:30",
                        "COMPLETED", TransactionTypeName.WITHDRAWAL
                ),
                new TransactionResponseDto(3L, 3L, 2L, 1500.00, "Salary deposit",
                        "2024-11-21T11:40", "COMPLETED", TransactionTypeName.DEPOSIT
                )
        );

        Long accountId = 2L;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/{accountId}/transactions", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<TransactionResponseDto> actualTransactions = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        Assertions.assertEquals(expectedTransactions, actualTransactions);

    }

    @Test
    void getLastMonthTransactionsByAccount() throws Exception {

        List<TransactionResponseDto> expectedTransactions = List.of(
                new TransactionResponseDto(3L, 3L, 2L, 1500.00, "Salary deposit",
                        "2024-11-21T11:40", "COMPLETED", TransactionTypeName.DEPOSIT
                ),
                new TransactionResponseDto(2L, 2L, 3L, -500.00, "ATM withdrawal",
                        "2024-11-21T11:30",
                        "COMPLETED", TransactionTypeName.WITHDRAWAL
                )
        );

        Long accountId = 2L;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/{accountId}/transactions/last-month", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<TransactionResponseDto> actualTransactions = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        Assertions.assertEquals(actualTransactions, expectedTransactions);
    }

    @Test
    void addAccountTest() throws Exception {

        AccountRequestDto requestDto = new AccountRequestDto(
                Status.ACTIVE,
                100.00,
                "DE22345678901234567890",
                "CAMMDEFF"
        );

        Long userId = 3L;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/add/user/{userId}", userId)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        AccountBasicDto actual = objectMapper.readValue(jsonResponse, AccountBasicDto.class);

        Assertions.assertEquals(6L, actual.getId());
        Assertions.assertEquals(Status.ACTIVE, actual.getStatus());
        Assertions.assertEquals(100.00, actual.getBalance());
        Assertions.assertEquals("DE22345678901234567890", actual.getIban());
        Assertions.assertEquals("CAMMDEFF", actual.getSwift());


    }

    @Test
    void softDeleteAccount() throws Exception {

        Long accountId = 3L;
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        MvcResult afterDelete = mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = afterDelete.getResponse().getContentAsString();
        AccountBasicDto afterDeleteAccountJson = objectMapper.readValue(jsonResponse, AccountBasicDto.class);

        Assertions.assertEquals(3L, afterDeleteAccountJson.getId());
        Assertions.assertEquals(3000.00, afterDeleteAccountJson.getBalance());
        Assertions.assertEquals(Status.DELETED, afterDeleteAccountJson.getStatus());
        Assertions.assertEquals("DE89370400440532013858", afterDeleteAccountJson.getIban());
        Assertions.assertEquals("DEUTDEFF", afterDeleteAccountJson.getSwift());

    }
}