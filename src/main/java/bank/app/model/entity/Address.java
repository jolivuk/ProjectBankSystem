package bank.app.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name="created_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
    public Address(String country, String city, String postcode, String street,
                   String houseNumber, String info, LocalDateTime createdAt) {
        this.country = country;
        this.city = city;
        this.postcode = postcode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.info = info;
        this.createdAt = createdAt;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
