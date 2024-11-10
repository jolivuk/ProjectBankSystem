package bank.app.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="transaction_type")
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_type_id")
    private int id;

    @Column(name="transaction_type_name")
    private String transactionTypeName;

    @Column(name="transaction_type_fee")
    private double transactionFee;

    @Column(name="transaction_type_description")
    private String transactionTypeDescription;

    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    public void setTransactionFee(double transactionFee) {
        this.transactionFee = transactionFee;
    }

    public void setTransactionTypeDescription(String transactionTypeDescription) {
        this.transactionTypeDescription = transactionTypeDescription;
    }
}

/**
 *    + PK id : INT
 *    + fee_type : FeeType
 *    + transaction_type_name : varchar(64)
 *    + transaction_fee : DECIMAL(15,2)
 *    + description : varchar(255)
 */
