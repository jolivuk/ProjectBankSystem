package bank.app.utils;

import bank.app.dto.AddressResponseDto;
import bank.app.dto.PrivateInfoResponseDto;
import bank.app.dto.UserResponseDto;
import bank.app.model.entity.Address;
import bank.app.model.entity.PrivateInfo;
import bank.app.model.entity.User;
import bank.app.model.enums.DocumentType;
import bank.app.model.enums.Role;
import bank.app.model.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserTestData {
    public static UserResponseDto returnUser(){
//        Address address = new Address();
//        address.setId(Long.valueOf(1));
//        address.setCountry("Germany");
//        address.setCity("Berlin");
//        address.setPostcode("10115");
//        address.setStreet("Alexanderplatz");
//        address.setHouseNumber("5");
//        address.setInfo(null);
//
//        PrivateInfo privateInfo = new PrivateInfo();
//        privateInfo.setId(Long.valueOf(1));
//        privateInfo.setFirstName("Max");
//        privateInfo.setLastName("Mustermann");
//        privateInfo.setDateOfBirth(LocalDate.of(1980,01,01));
//        privateInfo.setDocumentType(DocumentType.PASSPORT_EU);
//        privateInfo.setDocumentNumber("D12345678");
//        privateInfo.setPhone("491234567890");
//        privateInfo.setEmail("max@example.com");
//        privateInfo.setAddress(address);
//        privateInfo.setCreatedAt(LocalDateTime.parse("2024-05-20T10:00:00"));
//        privateInfo.setLastUpdate(LocalDateTime.parse("2024-05-20T10:00:00"));
//
//
//        User user = new User();
//        user.setId(Long.valueOf(1));
//        user.setUsername("manager1");
//        user.setPassword("password123");
//        user.setPrivateInfo(privateInfo);
//        user.setRole(Role.MANAGER);
//        user.setStatus(Status.ACTIVE);
//        user.setManager(null);
//        user.setCreatedAt(LocalDateTime.parse("2024-05-20T10:00:00"));
//        user.setLastUpdate(LocalDateTime.parse("2024-05-20T10:00:00"));
        AddressResponseDto address = new AddressResponseDto(
                "Germany",
                "Berlin",
                "10115",
                "Alexanderplatz",
                "5",
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
                1L,
                "manager1",
                "password123",
                "ACTIVE",
                "MANAGER",
                null,
                privateInfo
        );

        return expectedUser;
    }
}
