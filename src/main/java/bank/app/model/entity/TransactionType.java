package bank.app.model.entity;

import bank.app.model.enums.TransactionTypeName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="transaction_type")
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_type_id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name="transaction_type_name")
    private TransactionTypeName transactionTypeName;

    @Column(name="transaction_type_fee")
    private double transactionFee;

    @Column(name="transaction_type_description")
    private String transactionTypeDescription;

    public void setTransactionTypeName(TransactionTypeName transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    public void setTransactionFee(double transactionFee) {
        this.transactionFee = transactionFee;
    }

    public void setTransactionTypeDescription(String transactionTypeDescription) {
        this.transactionTypeDescription = transactionTypeDescription;
    }

    public void setId(int id) {
        this.id = id;
    }
}
