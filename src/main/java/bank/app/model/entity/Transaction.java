package bank.app.model.entity;

import bank.app.model.enums.TransactionStatusName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="transactions")

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
    private BigDecimal fee;

    @Column(name="comment")
    private String comment;

    @Column(name="transaction_date")
    private ZonedDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "transaction_status_id")
    private TransactionStatus transactionStatus;

    @Column(name="created_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;


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

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
