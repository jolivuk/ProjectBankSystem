package bank.app.utils;

import bank.app.model.entity.Address;
import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;


public class AddressTestData {
    private static final AtomicLong COUNTER =new AtomicLong(0L);

    private static final Faker FAKER = new Faker(Locale.GERMAN);

    public static Address getAddress() {
        var address = FAKER.address();
        Address expected = new Address();
        expected.setId(COUNTER.incrementAndGet());
        expected.setInfo("info");
        expected.setCity(address.city());
        expected.setCountry(address.country());
        expected.setStreet(address.streetAddress());
        expected.setPostcode(address.zipCode());
        expected.setHouseNumber("5");
        return expected;
    }
}
