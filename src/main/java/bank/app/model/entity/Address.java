package bank.app.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name="address")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address {

    @Id
    @Column(name="address_id")
    private Long id;

    @Column(name="country")
    private String country;

    @Column(name="city")
    private String city;

    @Column(name="postcode")
    private String postcode;

    @Column(name="street")
    private String street;

    @Column(name="house_number")
    private String houseNumber;

    @Column(name="info")
    private String info;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "private_info_id")
    @MapsId
    private PrivateInfo privateInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(country, address.country) && Objects.equals(city, address.city) && Objects.equals(postcode, address.postcode) && Objects.equals(street, address.street) && Objects.equals(houseNumber, address.houseNumber) && Objects.equals(info, address.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, postcode, street, houseNumber, info);
    }

    @Override
    public String toString() {
        return houseNumber + " " + street + " " + postcode +  " " + city + " " + country;
    }
}
