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
@Table(name = "banks")
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
    private String swift;

    @Column(name="created_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    public Bank(Address address, String bankName, String swift, LocalDateTime createdAt) {
        this.address = address;
        this.bankName = bankName;
        this.swift = swift;
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }
}
