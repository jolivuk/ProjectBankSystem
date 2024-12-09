package bank.app.model.entity;


import bank.app.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "iban")
    private String iban;

    @Column(name = "swift")
    private String swift;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    public Account(User user, String iban, String swift, Status status, Double balance) {
        this.user = user;
        this.iban = iban;
        this.swift = swift;
        this.status = status;
        this.balance = balance;
    }

    public boolean isBlocked() {
        return status == Status.BLOCKED;
    }

    public boolean isDeleted() {
        return status == Status.DELETED;
    }

}
