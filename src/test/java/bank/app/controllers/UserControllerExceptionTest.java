package bank.app.controllers;

import bank.app.dto.PrivateInfoRequestDto;
import bank.app.dto.UserRequestDto;
import bank.app.model.enums.Role;
import bank.app.model.enums.Status;
import bank.app.security.JwtTokenHelper;
import bank.app.util.UserTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/schema.sql")
@Sql("/db/data.sql")
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = "ADMIN")
public class UserControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    private String validToken;

    @BeforeEach
    public void setUp() {
        validToken = jwtTokenHelper.generateToken("admin", "ROLE_ADMIN");
    }


    @Test
    void findAllUsersForManagerExceptionTest() throws Exception {

        Long userId = 6L;
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}/customers", userId)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByIdExceptionTest() throws Exception {

        Long userId = 6L;
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", userId)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUserExceptionTest() throws Exception {

        UserRequestDto requestDto = new UserRequestDto(
                "client3",
                "password123",
                Status.ACTIVE,
                Role.ROLE_CUSTOMER,
                2L
        );

        mockMvc.perform(post("/users/")
                        .header("Authorization", "Bearer " + validToken)
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
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(privateInfo)))
                .andExpect(status().isConflict())
                .andReturn();
    }
}
