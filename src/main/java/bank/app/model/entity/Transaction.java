package bank.app.model.entity;

import bank.app.model.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "account_id")
    @JsonIgnore
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "account_id")
    @JsonIgnore
    private Account receiver;

    @Column(name = "amount")
    private double amount;

    @Column(name = "fee")
    private double fee;

    @Column(name = "comment")
    private String comment;

    @Column(name = "transaction_date")
    @CreationTimestamp
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private TransactionStatus transactionStatus;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionType;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    public Transaction(Account sender, Account receiver, double amount,
                       String comment, TransactionStatus transactionStatus,
                       TransactionType transactionType) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.comment = comment;
        this.transactionStatus = transactionStatus;
        this.transactionType = transactionType;
    }

}
