package bank.app.controllers;


import bank.app.controllers.handler.ErrorResponse;
import bank.app.dto.TransactionRequestDto;
import bank.app.dto.TransactionResponseDto;
import bank.app.dto.UserRequestDto;
import bank.app.dto.UserResponseDto;
import bank.app.model.enums.Role;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
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

import java.time.LocalDateTime;

import static bank.app.utils.UserTestData.getUserResponseDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/schema.sql")
@Sql("/db/data.sql")
@ActiveProfiles("test")
@WithMockUser(username = "admin", password= "admin", roles = "admin")
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getInformationById() throws Exception {

        Long transactionId = 2L;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        String jsonResponse = mvcResult.getResponse().getContentAsString();

        TransactionResponseDto actualTransactionJSON = objectMapper.readValue(jsonResponse, TransactionResponseDto.class);

        TransactionResponseDto expectedTransaction = new TransactionResponseDto(
                2L,2L,3L,500,"ATM withdrawal",
                "2024-11-21T11:30",
                "COMPLETED","Withdrawal"
        );

        Assertions.assertEquals(actualTransactionJSON, expectedTransaction);

    }

    @Test
    void delete() throws Exception {
        Long transactionId = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/transactions/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        ErrorResponse errorResponse = objectMapper.readValue(jsonResponse, ErrorResponse.class);

        Assertions.assertEquals(400, errorResponse.getStatus());
        Assertions.assertEquals("Transaction with id 1 not founded", errorResponse.getMessage());
    }

    @Test
    void addTransaction() throws Exception {
        TransactionRequestDto requestDto = new TransactionRequestDto(
                2L,
                4L,
                200,
                "Transfer friend",
                "Transfer"
        );

        MvcResult mvcResult = mockMvc.perform(post("/transactions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseJSON = mvcResult.getResponse().getContentAsString();

        TransactionResponseDto actualTransactionJSON = objectMapper.readValue(responseJSON, TransactionResponseDto.class);

        Assertions.assertEquals(6L, actualTransactionJSON.getTransactionId());
        Assertions.assertEquals(2L, actualTransactionJSON.getSender());
        Assertions.assertEquals(4L, actualTransactionJSON.getReceiver());
        Assertions.assertEquals(200, actualTransactionJSON.getAmount());
        Assertions.assertEquals("Transfer friend", actualTransactionJSON.getComment());
        Assertions.assertEquals("COMPLETED",actualTransactionJSON.getTransactionStatus());
        Assertions.assertEquals("Transfer", actualTransactionJSON.getTransactionType());
    }
}