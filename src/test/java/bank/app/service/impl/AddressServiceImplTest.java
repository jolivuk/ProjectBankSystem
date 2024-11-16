package bank.app.service.impl;

import bank.app.dto.AddressCreateRequestDto;
import bank.app.dto.AddressResponseDto;
import bank.app.mapper.AddressMapper;
import bank.app.model.entity.Address;
import bank.app.repository.AddressRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AddressServiceImplTest {
    private long counter = 0L;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepository addressRepository;

    @Spy
    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        Mockito.reset(addressRepository);
    }

    @AfterEach
    void tearDown() {

    }


    @Test
    void getAddressById_addressFound_addressNotNull() {
        Address expected = getAddress(); // 1
        Address testAddress = getAddress(); // 2
        assertNotEquals(expected, testAddress); //

        Mockito.when(addressRepository.findById(eq(expected.getId()))).thenReturn(Optional.of(expected));

        Mockito.when(addressRepository.findById(eq(testAddress.getId()))).thenReturn(Optional.of(testAddress));

        AddressResponseDto actual = addressService.getAddressById(expected.getId());

        assertEquals(addressMapper.toAddressResponseDto(expected), actual);
    }

    @Test
    void getAddressById_addressFound_throwIllegalArgumentException() {
        Address testAddress = getAddress(); // 11
        long wrongId = -1L;
        // 11                                  // 11
        Mockito.when(addressRepository.findById(eq(testAddress.getId()))).thenReturn(Optional.of(testAddress));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> addressService.getAddressById(wrongId));
        assertEquals("Address not found with id: " + wrongId, exception.getMessage());
    }


    @Test
    void createAddress() {
        Address expected = getAddress();
        Address forMock = expected.toBuilder().id(null).build();
        AddressCreateRequestDto dto = new AddressCreateRequestDto(
                expected.getCountry(),
                expected.getCity(),
                expected.getPostcode(),
                expected.getStreet(),
                expected.getHouseNumber(),
                expected.getInfo());

        Mockito.when(addressRepository.save(eq(forMock))).thenReturn(expected);
        AddressResponseDto actual = addressService.createAddress(dto);

        assertEquals(addressMapper.toAddressResponseDto(expected), actual);
    }

    private Address getAddress() {
        Address expected = new Address();
        expected.setId(++counter);
        expected.setInfo("info");
        expected.setCity("Berlin");
        expected.setCountry("Germany");
        expected.setStreet("Alexanderplatz");
        expected.setPostcode(String.valueOf(ThreadLocalRandom.current().nextInt(1, 10_000)));
        expected.setHouseNumber("5");
        return expected;
    }
}