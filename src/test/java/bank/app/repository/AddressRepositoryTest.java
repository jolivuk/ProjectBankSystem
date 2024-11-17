package bank.app.repository;

import bank.app.model.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bank.app.utils.AddressTestData.getAddress;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class AddressRepositoryTest {
    private static final Address ADDRESS1 = getAddress();
    private static final Address ADDRESS2 = getAddress();
    private static final Address ADDRESS3 = getAddress();

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        addressRepository.save(ADDRESS1);
        addressRepository.save(ADDRESS2);
        addressRepository.save(ADDRESS3);
    }

    @Test
    void getAddressById() {
        Address expected = ADDRESS3;
        Address actual = addressRepository.getAddressById(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    void getAllAddressByCountry() {
        Address expected = ADDRESS3;
        Address expected2 = ADDRESS2.toBuilder()
                .id(null)
                .country(expected.getCountry())
                .build();
        addressRepository.save(expected2);
        List<Address> actual = addressRepository.getAllAddressByCountry(expected.getCountry());
        Set<Address> expectedSet = addressRepository.findAll().stream()
                        .filter(e -> e.getCountry().equals(expected.getCountry())).collect(Collectors.toSet());
        assertEquals(expectedSet,new HashSet<>(actual));
        assertTrue(expectedSet.contains(expected));
        assertTrue(expectedSet.contains(expected2));
    }
}