package bank.app.controllers;

import bank.app.dto.PrivateInfoRequestDto;
import bank.app.dto.UserRequestDto;
import bank.app.model.enums.Role;
import bank.app.utils.UserTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
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



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/schema.sql")
@Sql("/db/data.sql")
@ActiveProfiles("test")
@WithMockUser(username = "user", password= "user", roles = "user")
public class UserControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllUsersForManagerExceptionTest() throws Exception {

        Long userId = 6L;
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}/customers", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByIdExceptionTest() throws Exception {

        Long userId = 6L;
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUserExceptionTest() throws Exception {

        UserRequestDto requestDto = new UserRequestDto(
                "client3",
                "password123",
                "ACTIVE",
                Role.ROLE_CUSTOMER,
                2L
        );

        MvcResult mvcResult = mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isConflict())
                .andReturn();

    }

    @Test
    void createPrivateInfoExceptionTest() throws Exception {

        Long userId = 5L;
        PrivateInfoRequestDto privateInfo = UserTestData.getPrivateInfoRequestDtoException();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/{id}/private_info/", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(privateInfo)))
                .andExpect(status().isConflict())
                .andReturn();
    }
}
