package bank.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "bank")
public class Bank {
    @Id
    @Column(name = "bank_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "swift")
    private int swift;

    public Bank(Address address, String bankName, int swift) {
        this.address = address;
        this.bankName = bankName;
        this.swift = swift;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setSwift(int swift) {
        this.swift = swift;
    }
}
