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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/schema.sql")
@Sql("/db/data.sql")
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void findAllUsersTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].status", everyItem(is("ACTIVE"))))
                .andExpect(jsonPath("$[?(@.role=='BANK')]", hasSize(1)))
                .andExpect(jsonPath("$[?(@.role=='MANAGER')]", hasSize(2)))
                .andExpect(jsonPath("$[?(@.role=='CUSTOMER')]", hasSize(3)));

    }


    @Test
    void findUserByIdTest() throws Exception {

            Long userId = 2L;

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("/users/{id}", userId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String actualUserJSON = mvcResult.getResponse().getContentAsString();


            AddressResponseDto address = new AddressResponseDto(
                    "Germany",
                    "Berlin",
                    "10115",
                    "Marienplatz",
                    "7",
                    null
            );

            PrivateInfoResponseDto privateInfo = new PrivateInfoResponseDto(
                    "Max",
                    "Mustermann",
                    "max@example.com",
                    "491234567890",
                    LocalDate.parse("1980-01-01"),
                    DocumentType.PASSPORT_EU,
                    "D12345678",
                    null,
                    address
            );

            UserResponseDto expectedUser = new UserResponseDto(
                    2L,
                    "manager1",
                    "password123",
                    "ACTIVE",
                    "MANAGER",
                    null,
                    privateInfo
            );

            String expectedUserJSON = objectMapper.writeValueAsString(expectedUser);

        System.out.println("actualUserJSON"+actualUserJSON);
        System.out.println("---------------------");
        System.out.println("expectedUserJSON"+expectedUserJSON);

            JSONAssert.assertEquals(expectedUserJSON, actualUserJSON, true);

    }


    @Test
    void deleteUserTest() throws Exception {

        Long userId = 1L;

        MvcResult beforeDelete = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String beforeDeleteJSON = beforeDelete.getResponse().getContentAsString();
        String expectedBeforeJSON = """
           {
               "id": 1,
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
               "id": 1,
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
                1L
        );

        // When
        MvcResult mvcResult = mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String actualUserJSON = mvcResult.getResponse().getContentAsString();

        // Then
        String expectedUserJSON = """
           {
               "username": "validUser123",
               "password": "password123",
               "status": "ACTIVE",
               "role": "CUSTOMER",
               "manager": 1
           }
           """;

        System.out.println("actualUserJSON  -  "+actualUserJSON);
        System.out.println("--------------------------------");
        System.out.println("expectedUserJSON  -  "+expectedUserJSON);
        JSONAssert.assertEquals(expectedUserJSON, actualUserJSON, false);

    }

    @Test
    void findUserByBankTest() throws Exception {


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/bank")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualUsersJSON = mvcResult.getResponse().getContentAsString();



        UserResponseDto userResponseDto = new UserResponseDto(1L,
                "BANKaccount",
                "password123",
                "ACTIVE",
                "BANK",
                null,
                null);

        String expectedUsersJson = objectMapper.writeValueAsString(userResponseDto);

        System.out.println("actualUsersJSON" + actualUsersJSON);
        System.out.println("--------------------------------");
        System.out.println("expectedUsersJson" + expectedUsersJson);

        JSONAssert.assertEquals(expectedUsersJson, actualUsersJSON, true);
    }

    @Test
    void addPrivateInfoToUserTest() throws Exception {

        Long userId = 1L;

        PrivateInfoRequestDto privateInfo = new PrivateInfoRequestDto();
        privateInfo.setFirstName("John");
        privateInfo.setLastName("Smith");
        privateInfo.setEmail("john.smith@example.com");
        privateInfo.setPhone("+491234567899");
        privateInfo.setDateOfBirth(LocalDate.of(1985, 6, 15));
        privateInfo.setDocumentType(DocumentType.PASSPORT_EU);
        privateInfo.setDocumentNumber("D87654321");

        AddressRequestDto address = new AddressRequestDto(
                "Germany",
                "Munich",
                "80331",
                "Karlsplatz",
                "8",
                null
        );

        privateInfo.setAddress(address);

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

        Long userId = 1L;
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
               "id": 1,
               "username": "updatedUser",
               "password": "newPassword123",
               "status": "ACTIVE",
               "role": "CUSTOMER",
               "manager": 2
           }
           """;

        JSONAssert.assertEquals(expectedJSON, actualUpdateJSON, false);

        // Проверка, что данные действительно обновились в базе
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
        Long userId = 1L;
        UserRequestDto requestDto = new UserRequestDto(
                "updatedUser",
                "newPassword123",
                "ACTIVE",
                Role.CUSTOMER,
                2L
        );


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        String actualUserJSON = mvcResult.getResponse().getContentAsString();


        UserResponseDto expectedUser = new UserResponseDto(
                1L,
                "updatedUser",
                "newPassword123",
                "ACTIVE",
                "CUSTOMER",
                2L,
                null
        );

        String expectedUserJSON = objectMapper.writeValueAsString(expectedUser);

        JSONAssert.assertEquals(expectedUserJSON, actualUserJSON, true);


        MvcResult getResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualUpdatedUserJSON = getResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedUserJSON, actualUpdatedUserJSON, true);
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

        // When
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

