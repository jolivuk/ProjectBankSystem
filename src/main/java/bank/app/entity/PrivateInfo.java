package bank.app.entity;

import bank.app.entity.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="private_info")
public class PrivateInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="private_info_id")
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type_id")
    private DocumentType documentType;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name="comment")
    private String comment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name="created_at")
    private LocalDate createdAt;

    public PrivateInfo(LocalDate createdAt, Address address, String comment, LocalDate birthDate,
                       String phone, String email, String lastName, String firstName) {
        this.createdAt = createdAt;
        this.address = address;
        this.comment = comment;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
