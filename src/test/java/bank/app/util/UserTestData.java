package bank.app.util;

import bank.app.dto.*;
import bank.app.model.enums.DocumentType;

import java.time.LocalDate;
import java.util.List;

public class UserTestData {
    public static UserResponseDto getUserResponseDto() {
        AddressResponseDto address = new AddressResponseDto(
                2L,
                "Germany",
                "Berlin",
                "10115",
                "Marienplatz",
                "7",
                null
        );

        PrivateInfoResponseDto privateInfo = new PrivateInfoResponseDto(
                2L,
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
                "ROLE_MANAGER",
                null,
                privateInfo
        );

        return expectedUser;
    }

    public static UserResponseDto getDeletedUserResponseDto(){
        AddressResponseDto address = new AddressResponseDto(
                2L,
                "Germany",
                "Berlin",
                "10115",
                "Marienplatz",
                "7",
                null
        );

        PrivateInfoResponseDto privateInfo = new PrivateInfoResponseDto(
                2L,
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
                "DELETED",
                "ROLE_MANAGER",
                null,
                privateInfo
        );

        return expectedUser;
    }

    public static UserResponseDto getUserResponseDtoUpdate(){
        UserResponseDto expectedUser = getUserResponseDto();
        UserResponseDto responseUpdatedDto = new UserResponseDto(
                2L,
                "updatedUser",
                "newPassword123",
                "ACTIVE",
                "ROLE_MANAGER",
                null,
                expectedUser.privateInfoResponse()
        );
        return responseUpdatedDto;
    }

    public static PrivateInfoRequestDto getPrivateInfoRequestDto(){
        AddressRequestDto addressRequestDto = new AddressRequestDto(
                "Germany",
                "Munich",
                "80331",
                "Karlsplatz",
                "8",
                null
        );

        return new PrivateInfoRequestDto("John", "Smith","john.smith@example.com",
                "+491234567899",LocalDate.of(1985, 6, 15),DocumentType.PASSPORT_EU,
                "D87654321",null,addressRequestDto);
    }

    public static PrivateInfoRequestDto getPrivateInfoRequestDtoException(){
        AddressRequestDto addressRequestDto = new AddressRequestDto(
                "Germany",
                "Munich",
                "80331",
                "Karlsplatz",
                "8",
                null
        );

        return new PrivateInfoRequestDto("John", "Smith","max@example.com",
                "+491234567899",LocalDate.of(1985, 6, 15),DocumentType.PASSPORT_EU,
                "D87654321",null,addressRequestDto);
    }

    public static PrivateInfoRequestDto getPrivateInfoRequestDtoUpdate(){

        AddressRequestDto addressRequestDto = new AddressRequestDto( "Germany", "Berlin",
                "10115", "Marienplatz", "7", null);

        return new PrivateInfoRequestDto("Maxim", "NewMustermann","newmax@example.com",
                "+491111111111",LocalDate.of(1980, 1, 1),DocumentType.ID_CARD,
                "ID1777777",null, addressRequestDto);
    }

    public static PrivateInfoResponseDto getPrivateInfoResponseDto(){

        AddressResponseDto addressResponseDto = new AddressResponseDto( 2L,"Germany", "Berlin",
                    "10115", "Marienplatz", "7", null);

        return new PrivateInfoResponseDto(2L,
                "Maxim","NewMustermann","newmax@example.com","+491111111111",
                LocalDate.of(1980, 1, 1),
                DocumentType.ID_CARD,"ID1777777",null, addressResponseDto);
    }

    public static UserResponseDto getUserResponseWithPrivateInfoDto(){

        AddressResponseDto addressResponseDto = new AddressResponseDto( 5L,"Germany", "Munich",
                "80331", "Karlsplatz", "8", null);

        PrivateInfoResponseDto privateInfoResponseDto = new PrivateInfoResponseDto(5L,
                "John","Smith","john.smith@example.com","+491234567899",
                LocalDate.of(1985, 6, 15),
                DocumentType.PASSPORT_EU,"D87654321",null, addressResponseDto);

        UserResponseDto user = new UserResponseDto(
                5L,"client3", "password3", "ACTIVE", "ROLE_CUSTOMER", 2L,
                privateInfoResponseDto
        );

        return user;
    }

    public static List<UserResponseDto> getAllUsers(){
        UserResponseDto user1 = new UserResponseDto(
                1L,"BANKAccount", "password123", "ACTIVE", "ROLE_BANK", null,
                null
        );

        //User2
        AddressResponseDto address2 = new AddressResponseDto(2L,
                "Germany","Berlin","10115","Marienplatz","7",null);

        PrivateInfoResponseDto privateInfo2 = new PrivateInfoResponseDto( 2L,
                "Max",
                "Mustermann",
                "max@example.com",
                "491234567890",
                LocalDate.parse("1980-01-01"),
                DocumentType.PASSPORT_EU,
                "D12345678",
                null,
                address2
        );

        UserResponseDto user2 = new UserResponseDto(
                2L,
                "manager1",
                "password123",
                "ACTIVE",
                "ROLE_MANAGER",
                null,
                privateInfo2
        );

        //User3
        AddressResponseDto address3 = new AddressResponseDto(3L,
                "Germany","Berlin","10115","Alexanderplatz","5",null);

        PrivateInfoResponseDto privateInfo3 = new PrivateInfoResponseDto(
                3L,
                "Erika",
                "Mustermann",
                "erika@example.com",
                "491234567891",
                LocalDate.parse("1985-05-10"),
                DocumentType.PASSPORT_EU,
                "D87654321",
                null,
                address3
        );

        UserResponseDto user3 = new UserResponseDto(
                3L,
                "client1",
                "password123",
                "ACTIVE",
                "ROLE_CUSTOMER",
                2L,
                privateInfo3
        );

        //User4
        AddressResponseDto address4 = new AddressResponseDto(
                4L, "Germany","Munich","80331","Marienplatz","10",null);

        PrivateInfoResponseDto privateInfo4 = new PrivateInfoResponseDto(
                4L,
                "Hans",
                "Muller",
                "hans@example.com",
                "491234567892",
                LocalDate.parse("1975-08-15"),
                DocumentType.ID_CARD,
                "ID123456",
                null,
                address4
        );

        UserResponseDto user4 = new UserResponseDto(
                4L,
                "client2",
                "password123",
                "ACTIVE",
                "ROLE_CUSTOMER",
                2L,
                privateInfo4
        );

        UserResponseDto user5 = new UserResponseDto(
                5L,"client3", "password3", "ACTIVE", "ROLE_CUSTOMER", 2L,
                null
        );

        return List.of(user1, user2, user3, user4,user5);
    }

    public static List<UserResponseDto> getAllUsersForManager(){

        //User1
        AddressResponseDto address1 = new AddressResponseDto(3L,
                "Germany","Berlin","10115","Alexanderplatz","5",null);

        PrivateInfoResponseDto privateInfo1 = new PrivateInfoResponseDto(
                3L,
                "Erika",
                "Mustermann",
                "erika@example.com",
                "491234567891",
                LocalDate.parse("1985-05-10"),
                DocumentType.PASSPORT_EU,
                "D87654321",
                null,
                address1
        );

        UserResponseDto user1 = new UserResponseDto(
                3L,
                "client1",
                "password123",
                "ACTIVE",
                "ROLE_CUSTOMER",
                2L,
                privateInfo1
        );

        //User2
        AddressResponseDto address2 = new AddressResponseDto(
                4L, "Germany","Munich","80331","Marienplatz","10",null);

        PrivateInfoResponseDto privateInfo2 = new PrivateInfoResponseDto(
                4L,
                "Hans",
                "Muller",
                "hans@example.com",
                "491234567892",
                LocalDate.parse("1975-08-15"),
                DocumentType.ID_CARD,
                "ID123456",
                null,
                address2
        );

        UserResponseDto user2= new UserResponseDto(
                4L,
                "client2",
                "password123",
                "ACTIVE",
                "ROLE_CUSTOMER",
                2L,
                privateInfo2
        );

        //User3
        UserResponseDto user3 = new UserResponseDto(
                5L,"client3", "password3", "ACTIVE", "ROLE_CUSTOMER", 2L,
                null
        );

        return List.of(user1, user2,user3);
    }
}
