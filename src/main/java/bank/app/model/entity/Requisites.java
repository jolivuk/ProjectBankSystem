package bank.app.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "requisites")
public class Requisites {

    @Id
    @Column(name = "requisites_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "bank_id")
    @ManyToOne
    private Bank bank;

    @JoinColumn(name = "account_id")
    @OneToOne
    private Account account;

    @Column(name = "iban")
    private String iban;

    @Column(name="created_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    public Requisites(Bank bank, Account account, String iban, LocalDateTime createdAt, LocalDateTime lastUpdate) {
        this.bank = bank;
        this.account = account;
        this.iban = iban;
        this.createdAt = createdAt;
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

}
