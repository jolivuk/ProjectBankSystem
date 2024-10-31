package bank.app.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private int iban;

    public Requisites(Bank bank, Account account, int iban) {
        this.bank = bank;
        this.account = account;
        this.iban = iban;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setIban(int iban) {
        this.iban = iban;
    }

}
