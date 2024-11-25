package bank.app.controllers;

import bank.app.dto.AccountBasicDto;
import bank.app.dto.AccountResponseDto;
import bank.app.model.enums.Status;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/schema.sql")
@Sql("/db/data.sql")
@ActiveProfiles("test")
@WithMockUser(username = "admin", password= "admin", roles = "admin")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBasicAccountInfo() throws Exception {

    }

    @Test
    void findAccountsByUserId() throws Exception {

        /**
         * {
         *     "id": 1,
         *     "status": "ACTIVE",
         *     "balance": 0,
         *     "iban": "DE89370400440532013000",
         *     "swift": "DEUTDEFF",
         *     "createdAt": "2024-11-25T02:08:54",
         *     "lastUpdate": "2024-11-25T02:08:54"
         *   }
         */
        Long userId = 1L;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        List<AccountResponseDto> actualAccounts = objectMapper.readValue(
                jsonResponse,new TypeReference<>() {}
        );

        List<AccountResponseDto> expectedAccounts = List.of(
                new AccountResponseDto(
                        1L,
                        "ACTIVE",
                        0,
                        "DE89370400440532013000",
                        "DEUTDEFF"
                )

        );

        Assertions.assertEquals(expectedAccounts, actualAccounts);
    }

    @Test
    void findBankAccount() {

    }

    @Test
    void getTransactionsByAccountId() throws Exception {
        /**
         * {
         *     "transactionId": 1,
         *     "sender": 4,
         *     "receiver": 2,
         *     "amount": 200,
         *     "comment": "Monthly transfer",
         *     "transactionDate": "2023-12-05T01:26:16",
         *     "transactionStatus": "COMPLETED",
         *     "transactionType": "Transfer"
         *   },
         */
        Long accountId = 2L;

        String accountBasicJson = mockMvc.perform(MockMvcRequestBuilders
                       .get("/accounts/{accountId}/transactions", accountId)
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andReturn().getResponse().getContentAsString();

        System.out.println(accountBasicJson);
        System.out.println("================================================");

        AccountBasicDto accountBasicDto = objectMapper.readValue(accountBasicJson, AccountBasicDto.class);

        System.out.println(accountBasicDto);



    }

    @Test
    void getLastMonthTransactionsByAccount() {
    }

    @Test
    void add() {

    }
}