package bank.app.controllers;

import bank.app.dto.AccountBasicDto;
import bank.app.model.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/schema.sql")
@Sql("/db/data.sql")
@ActiveProfiles("test")
class AccountControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Test
    void getBasicAccountInfo() throws Exception {

    }

    @Test
    void findAccountsByUserId() {

    }

    @Test
    void findBankAccount() {
    }

    @Test
    void getTransactionsByAccountId() {
    }

    @Test
    void getLastMonthTransactionsByAccount() {
    }

    @Test
    void add() {
    }
}