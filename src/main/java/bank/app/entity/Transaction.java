package bank.app.entity;

import bank.app.entity.enums.Role;
import bank.app.entity.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="sender_id", referencedColumnName = "requisites_id")
    private Requisites sender;

    @ManyToOne
    @JoinColumn(name="receiver_id", referencedColumnName = "requisites_id")
    private Requisites receiver;

    @Column(name="amount")
    private double amount;

    @Column(name="fee")
    private double fee;

    @Column(name="comment")
    private String comment;

    @Column(name="transaction_date")
    private ZonedDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private TransactionStatus transactionStatus;

    @Column(name="created_at")
    private ZonedDateTime createdAt;

    @Column(name="last_update")
    private ZonedDateTime lastUpdate;

    public Transaction(Requisites sender, Requisites receiver, double amount, double fee, String comment,
                       ZonedDateTime transactionDate, TransactionStatus transactionStatus, ZonedDateTime createdAt,
                       ZonedDateTime lastUpdate) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.fee = fee;
        this.comment = comment;
        this.transactionDate = transactionDate;
        this.transactionStatus = transactionStatus;
        this.createdAt = createdAt;
        this.lastUpdate = lastUpdate;
    }

    public void setSender(Requisites sender) {
        this.sender = sender;
    }

    public void setReceiver(Requisites receiver) {
        this.receiver = receiver;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setFee(double fee) {
        this.amount = fee;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
