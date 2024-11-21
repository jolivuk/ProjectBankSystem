package bank.app.controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/schema.sql")
@Sql("/db/data.sql")
@ActiveProfiles("test")
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getInformationById() throws Exception {

        Long transactionId = 1L;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualTransactionJson = mvcResult.getResponse().getContentAsString();

        String expectedJson = """
            {
                "transactionId": 1,
                "sender": 5,
                "receiver": 2,
                "amount": 200.0,
                "comment": "Monthly transfer",
                "transactionStatus": "COMPLETED",
                "transactionType": "TransactionType(id=1, transactionTypeName=Transfer, transactionFee=2.5, transactionTypeDescription=Standard bank transfer)"
            }
            """;

        JSONAssert.assertEquals(expectedJson, actualTransactionJson, JSONCompareMode.LENIENT);


        JsonNode jsonNode = objectMapper.readTree(actualTransactionJson);
        String transactionDate = jsonNode.get("transactionDate").asText();
        assertNotNull(transactionDate);
        assertTrue(transactionDate.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?"));
    }







    @Test
    void delete() throws Exception {
        Long transactionId = 1L;

        String expectedBeforeJSON = """
            {
                "transactionId": 1,
                "sender": 5,
                "receiver": 2,
                "amount": 200.0,
                "comment": "Monthly transfer",
                "transactionStatus": "COMPLETED",
                "transactionType": "TransactionType(id=1, transactionTypeName=Transfer, transactionFee=2.5, transactionTypeDescription=Standard bank transfer)"
            }
            """;

        String actualBeforeJSON = mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expectedBeforeJSON, actualBeforeJSON, JSONCompareMode.LENIENT);


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/transactions/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualErrorJSON = mvcResult.getResponse().getContentAsString();
        String expectedErrorJSON = """
            {
                "status": 400,
                "message": "transaction with id 1 not founded"
            }
            """;

        JSONAssert.assertEquals(expectedErrorJSON, actualErrorJSON, false);
    }

    @Test
    void addTransaction() throws Exception {

    }
}