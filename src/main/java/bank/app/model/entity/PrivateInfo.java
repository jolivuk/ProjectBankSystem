package bank.app.model.entity;

import bank.app.model.enums.DocumentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "private_info")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PrivateInfo {

    @Id
    @Column(name = "private_info_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "comment")
    private String comment;


    @OneToOne(
            mappedBy = "privateInfo",
            cascade = CascadeType.ALL,
            optional = false)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @MapsId
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivateInfo that = (PrivateInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(dateOfBirth, that.dateOfBirth) && documentType == that.documentType && Objects.equals(documentNumber, that.documentNumber) && Objects.equals(comment, that.comment) && Objects.equals(address, that.address) && Objects.equals(createdAt, that.createdAt) && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phone, dateOfBirth, documentType, documentNumber, comment, address, createdAt, lastUpdate);
    }

    @Override
    public String toString() {
        return "PrivateInfo{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", documentType=" + documentType +
                ", documentNumber='" + documentNumber + '\'' +
                ", comment='" + comment + '\'' +
                ", address=" + address +
                ", createdAt=" + createdAt +
                ", lastUpdate=" + lastUpdate +
                ", user=" + user +
                '}';
    }
}
