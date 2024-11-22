package bank.app.controllers;


import bank.app.dto.*;
import bank.app.model.enums.DocumentType;
import bank.app.model.enums.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
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
import java.time.LocalDate;
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
    void findUserByIdTest() throws Exception {

            Long userId = 2L;

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("/users/{id}", userId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();
            UserResponseDto actualUserJSON = objectMapper.readValue(jsonResponse, UserResponseDto.class);
            UserResponseDto expectedUser = getUserResponseDto();

            Assertions.assertEquals(actualUserJSON, expectedUser);
    }


    @Test
    void deleteUserTest() throws Exception {

        Long userId = 3L;

        MvcResult beforeDelete = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String beforeDeleteJSON = beforeDelete.getResponse().getContentAsString();
        String expectedBeforeJSON = """
           {
               "id": 3,
               "status": "ACTIVE"
           }
           """;
        JSONAssert.assertEquals(expectedBeforeJSON, beforeDeleteJSON, false);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult afterDelete = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String afterDeleteJSON = afterDelete.getResponse().getContentAsString();
        String expectedAfterJSON = """
           {
               "id": 3,
               "status": "DELETED"
           }
           """;
        JSONAssert.assertEquals(expectedAfterJSON, afterDeleteJSON, false);
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
                5L,
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

    @Test
    void addPrivateInfoToUserTest() throws Exception {

        Long userId = 2L;

        PrivateInfoRequestDto privateInfo = getPrivateInfoRequestDto();

        // When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/{id}/private_info/add", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(privateInfo)))
                .andExpect(status().isOk())
                .andReturn();

        String actualUserJSON = mvcResult.getResponse().getContentAsString();

        // Then
        String expectedUserJSON = """
           {
               "privateResponseInfo": {
                   "firstName": "John",
                   "lastName": "Smith",
                   "email": "john.smith@example.com",
                   "phone": "+491234567899",
                   "dateOfBirth": "1985-06-15",
                   "documentType": "PASSPORT_EU",
                   "documentNumber": "D87654321",
                   "address": {
                       "country": "Germany",
                       "city": "Munich",
                       "postcode": "80331",
                       "street": "Karlsplatz",
                       "houseNumber": "8",
                       "info": null
                   }
               }
           }
           """;

        JSONAssert.assertEquals(expectedUserJSON, actualUserJSON, false);
    }

    @Test
    void updateUserByIDTest() throws Exception {

        Long userId = 4L;
        UserRequestDto requestDto = new UserRequestDto(
                "updatedUser",
                "newPassword123",
                "ACTIVE",
                Role.CUSTOMER,
                2L
        );

        // When
        MvcResult updateResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        String actualUpdateJSON = updateResult.getResponse().getContentAsString();

        // Then
        String expectedJSON = """
           {
               "id": 4,
               "username": "updatedUser",
               "password": "newPassword123",
               "status": "ACTIVE",
               "role": "CUSTOMER",
               "manager": 2
           }
           """;

        JSONAssert.assertEquals(expectedJSON, actualUpdateJSON, false);

        MvcResult getResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualGetJSON = getResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJSON, actualGetJSON, false);
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

        PrivateInfoResponseDto expectedPrivateInfoResponseDto = getPrivateInfoResponseDto();

        String expectedPrivateInfoJSON = objectMapper.writeValueAsString(expectedPrivateInfoResponseDto);

        MvcResult getResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}/private_info", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualUpdatedPrivateInfoJSON = getResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedPrivateInfoJSON, actualUpdatedPrivateInfoJSON, true);
    }

    @Test
    void updateAddressToUserTest() throws Exception {
        // Given
        Long userId = 2L;

        MvcResult beforeUpdate = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        AddressRequestDto addressDto = new AddressRequestDto(
                "Germany",
                "Munich",
                "80331",
                "Karlsplatz",
                "8",
                null
        );

        MvcResult afterUpdate = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}/private_info/address", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        System.out.println("Before update: " + beforeUpdate.getResponse().getContentAsString());
        System.out.println("--------------------------------");
        System.out.println("After update: " + afterUpdate.getResponse().getContentAsString());
        System.out.println("--------------------------------");

    }


}

