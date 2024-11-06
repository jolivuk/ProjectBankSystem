package bank.app.model.entity;

import bank.app.model.enums.TransactionStatusName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_status")
public class TransactionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_status_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status_name")
    private TransactionStatusName transactionStatusName;


    public void setTransaction(TransactionStatusName transaction) {
        this.transactionStatusName = transaction;
    }
}
