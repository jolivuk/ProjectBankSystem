package bank.app.controllers;

import bank.app.model.entity.User;
import bank.app.utils.ExpectedData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/schema.sql")
@Sql("/db/data.sql")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findById() throws Exception {
        User expectedUser = ExpectedData.returnUser();

        String userJson = objectMapper.writeValueAsString(expectedUser);

        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/users/{id}", expectedUser.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userJson))
                        .andExpect(status().isOk())
                        .andReturn();

        String actualUserJSON = mvcResult.getResponse().getContentAsString();

        User actualUser = objectMapper.readValue(actualUserJSON, User.class);
        System.out.println("Expected user: " + expectedUser);
        System.out.println("Actual user: " + actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
    }
}