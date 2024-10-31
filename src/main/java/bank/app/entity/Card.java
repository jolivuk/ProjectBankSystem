package bank.app.entity;

import bank.app.entity.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "card")
@ToString
public class Card {
    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "experation_date")
    private LocalDateTime localDateTime;

    @Column(name = "card_number")
    private int cardNumber;

    @Column(name = "card_type")
    private String cardType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_id")
    private Status status;

    @Column(name = "security_code")
    private int securityCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Card(Account account, LocalDateTime localDateTime, int cardNumber,
                String cardType, Status status, int securityCode, LocalDateTime createdAt) {
        this.account = account;
        this.localDateTime = localDateTime;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.status = status;
        this.securityCode = securityCode;
        this.createdAt = createdAt;
    }


    public void setAccount(Account account) {
        this.account = account;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }


}
