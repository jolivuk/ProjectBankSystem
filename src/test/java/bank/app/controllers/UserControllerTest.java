package bank.app.controllers;


import bank.app.dto.*;
import bank.app.model.enums.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.MediaType;
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
@WithMockUser(username = "user", password= "user", roles = "user")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void findAllUsersTest() throws Exception {

        List<UserResponseDto> expected = getAllUsers();

        String allUsersJson = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<UserResponseDto> allUsersDto = objectMapper.readValue(allUsersJson,
                new TypeReference<>() {
        });

        Assertions.assertEquals(expected, allUsersDto);
    }

    @Test
    void findAllUsersForManagerTest() throws Exception {

        Long userId = 2L;


        String allUsersJson = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}/customers", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<UserResponseDto> allUsersByManagerDto = objectMapper.readValue(allUsersJson,
                new TypeReference<>() {
                });

        List<UserResponseDto> expected = getAllUsersForManager();

        Assertions.assertEquals(expected, allUsersByManagerDto);
    }

    @Test
    void findUserByIdTest() throws Exception {

            Long userId = 2L;

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("/users/{id}", userId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status() .isOk())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();
            UserResponseDto actualUserJSON = objectMapper.readValue(jsonResponse, UserResponseDto.class);
            UserResponseDto expectedUser = getUserResponseDto();

            Assertions.assertEquals(actualUserJSON, expectedUser);
    }

    @Test
    void deleteUserTest() throws Exception {

        Long userId = 2L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult afterDelete = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        String jsonResponse = afterDelete.getResponse().getContentAsString();
        UserResponseDto afterDeleteUserJson = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        UserResponseDto expectedDeletedUser = getDeletedUserResponseDto();

        Assertions.assertEquals(afterDeleteUserJson, expectedDeletedUser);
    }

    @Test
    void createUserTest() throws Exception {
        UserRequestDto requestDto = new UserRequestDto(
                "validUser123",
                "password123",
                "ACTIVE",
                Role.CUSTOMER,
                2L
        );

        MvcResult mvcResult = mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseJSON = mvcResult.getResponse().getContentAsString();

        UserResponseDto actualUserJSON = objectMapper.readValue(responseJSON, UserResponseDto.class);

        UserResponseDto expectedUserDTO = new UserResponseDto(
                6L,
                "validUser123",
                "password123",
                "ACTIVE",
                "CUSTOMER",
                2L,
                null
        );

        Assertions.assertEquals(expectedUserDTO, actualUserJSON);
    }

    @Test
    void findUserByBankTest() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/bank")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseJSON = mvcResult.getResponse().getContentAsString();
        UserResponseDto actualUserJSON = objectMapper.readValue(responseJSON, UserResponseDto.class);

        UserResponseDto expectedUserDto = new UserResponseDto(1L,
                "BANKAccount",
                "password123",
                "ACTIVE",
                "BANK",
                null,
                null);

        Assertions.assertEquals(expectedUserDto, actualUserJSON);
    }

    @Transactional
    @Test
    void addPrivateInfoToUserTest() throws Exception
    {

        Long userId = 5L;

        PrivateInfoRequestDto privateInfo = getPrivateInfoRequestDto();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/{id}/private_info/", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(privateInfo)))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        UserResponseDto actualUserResponseDto = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        UserResponseDto expectedUser = getUserResponseWithPrivateInfoDto();
        Assertions.assertEquals(expectedUser, actualUserResponseDto);
    }

    @Test
    void updateUserByIDTest() throws Exception {

        Long userId = 2L;
        UserRequestDto requestDto = new UserRequestDto(
                "updatedUser",
                "newPassword123",
                "ACTIVE",
                Role.MANAGER,
                null
        );

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        UserResponseDto actualUserResponseDto = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        UserResponseDto expectedUserResponseDto = getUserResponseDtoUpdate();
        Assertions.assertEquals(expectedUserResponseDto, actualUserResponseDto);

    }

    @Test
    void updatePrivateInfoToUserTest() throws Exception {
        Long userId = 2L;

        PrivateInfoRequestDto privateInfoRequestDto = getPrivateInfoRequestDtoUpdate();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}/private_info", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(privateInfoRequestDto)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        UserResponseDto actualUserResponseDto = objectMapper.readValue(jsonResponse, UserResponseDto.class);

        PrivateInfoResponseDto actualPrivateInfoResponseDto =actualUserResponseDto.privateInfoResponse();

        PrivateInfoResponseDto expectedPrivateInfoResponseDto = getPrivateInfoResponseDto();

        Assertions.assertEquals(expectedPrivateInfoResponseDto, actualPrivateInfoResponseDto);
    }

    @Test
    void updateAddressToUserTest() throws Exception {

        Long userId = 2L;

        AddressRequestDto addressDto = new AddressRequestDto(
                "Germany",
                "Munich",
                "80331",
                "Karlsplatz",
                "8",
                null
        );

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}/private_info/address", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        UserResponseDto actualUserJSON = objectMapper.readValue(jsonResponse, UserResponseDto.class);
        AddressResponseDto actualAddressResponseDto = actualUserJSON.privateInfoResponse().address();

        AddressResponseDto expectedAddressDto = new AddressResponseDto(
                2L,
                "Germany",
                "Munich",
                "80331",
                "Karlsplatz",
                "8",
                null
        );

        Assertions.assertEquals(expectedAddressDto, actualAddressResponseDto );
    }
}

