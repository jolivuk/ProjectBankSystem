package bank.app.controllers;


import bank.app.dto.*;
import bank.app.model.enums.Role;
import bank.app.model.enums.Status;
import bank.app.security.JwtTokenHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.jupiter.api.Assertions;
import java.util.List;
import static bank.app.utils.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/schema.sql")
@Sql("/db/data.sql")
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = "ADMIN")
class UserControllerTest {
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
    void findAllUsersTest() throws Exception {

        List<UserResponseDto> expected = getAllUsers();

        String allUsersJson = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<UserResponseDto> actualUsers = objectMapper.readValue(allUsersJson,
                new TypeReference<>() {
        });

        Assertions.assertEquals(expected, actualUsers);
    }

    @Test
    void findAllUsersForManagerTest() throws Exception {
        List<UserResponseDto> expected = getAllUsersForManager();

        Long userId = 2L;

        String allUsersJson = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}/customers", userId)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<UserResponseDto> actualAllUsersByManagerDto = objectMapper.readValue(allUsersJson,
                new TypeReference<>() {
                });


        Assertions.assertEquals(expected, actualAllUsersByManagerDto);
    }

    @Test
    void findUserByIdTest() throws Exception {
        UserResponseDto expectedUser = getUserResponseDto();

            Long userId = expectedUser.id();

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("/users/{id}", userId)
                            .header("Authorization", "Bearer " + validToken)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status() .isOk())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();
            UserResponseDto actualUserJSON = objectMapper.readValue(jsonResponse, UserResponseDto.class);


            Assertions.assertEquals(expectedUser,actualUserJSON);
    }

    @Test
    void deleteUserTest() throws Exception {
        UserResponseDto expectedDeletedUser = getDeletedUserResponseDto();

        Long userId = 2L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/{id}", userId)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult afterDelete = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", userId)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = afterDelete.getResponse().getContentAsString();
        UserResponseDto actualAfterDeleteUserJson = objectMapper.readValue(jsonResponse, UserResponseDto.class);



        Assertions.assertEquals(expectedDeletedUser,actualAfterDeleteUserJson);
    }

    @Test
    void createUserTest() throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserResponseDto expectedUserDTO = new UserResponseDto(
                6L,
                "validUser123",
                "password123",
                "ACTIVE",
                "ROLE_CUSTOMER",
                2L,
                null
        );


        UserRequestDto requestDto = new UserRequestDto(
                "validUser123",
                "password123",
                Status.ACTIVE,
                Role.ROLE_CUSTOMER,
                2L
        );

        MvcResult mvcResult = mockMvc.perform(post("/users/")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseJSON = mvcResult.getResponse().getContentAsString();
        UserResponseDto actualUserJSON = objectMapper.readValue(responseJSON, UserResponseDto.class);

        Assertions.assertEquals(expectedUserDTO, actualUserJSON);

    }

    @Test
    void findUserByBankTest() throws Exception {
        UserResponseDto expectedUserDto = new UserResponseDto(1L,
                "BANKAccount",
                "password123",
                "ACTIVE",
                "ROLE_BANK",
                null,
                null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/bank")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseJSON = mvcResult.getResponse().getContentAsString();
        UserResponseDto actualUserJSON = objectMapper.readValue(responseJSON, UserResponseDto.class);

        Assertions.assertEquals(expectedUserDto, actualUserJSON);
    }

    @Transactional
    @Test
    void addPrivateInfoToUserTest() throws Exception {
        UserResponseDto expectedUser = getUserResponseWithPrivateInfoDto();

        Long userId = 5L;

        PrivateInfoRequestDto privateInfo = getPrivateInfoRequestDto();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/{id}/private_info/", userId)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(privateInfo)))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        UserResponseDto actualUserResponseDto = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        Assertions.assertEquals(expectedUser, actualUserResponseDto);
    }

    @Test
    void updateUserByIDTest() throws Exception {
        UserResponseDto expectedUserResponseDto = getUserResponseDtoUpdate();

        Long userId = 2L;
        UserRequestDto requestDto = new UserRequestDto(
                "updatedUser",
                "newPassword123",
                Status.ACTIVE,
                Role.ROLE_MANAGER,
                null
        );

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}", userId)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        UserResponseDto actualUserResponseDto = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        Assertions.assertEquals(expectedUserResponseDto, actualUserResponseDto);

    }

    @Test
    void updatePrivateInfoToUserTest() throws Exception {
        PrivateInfoResponseDto expectedPrivateInfoResponseDto = getPrivateInfoResponseDto();

        Long userId = 2L;
        PrivateInfoRequestDto privateInfoRequestDto = getPrivateInfoRequestDtoUpdate();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}/private_info", userId)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(privateInfoRequestDto)))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        UserResponseDto actualUserResponseDto = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        PrivateInfoResponseDto actualPrivateInfoResponseDto =actualUserResponseDto.privateInfoResponse();

        Assertions.assertEquals(expectedPrivateInfoResponseDto, actualPrivateInfoResponseDto);
    }

    @Test
    void updateAddressToUserTest() throws Exception {
        AddressResponseDto expectedAddressDto = new AddressResponseDto(
                2L,
                "Germany",
                "Munich",
                "80331",
                "Karlsplatz",
                "8",
                null
        );
        Long userId = 2L;
        AddressRequestDto addressDto = new AddressRequestDto("Germany", "Munich", "80331",
                "Karlsplatz",
                "8",
                null
        );

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}/private_info/address", userId)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        UserResponseDto actualUserJSON = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        AddressResponseDto actualAddressResponseDto = actualUserJSON.privateInfoResponse().address();

        Assertions.assertEquals(expectedAddressDto, actualAddressResponseDto );
    }
}

