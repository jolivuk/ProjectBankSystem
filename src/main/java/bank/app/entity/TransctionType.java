package bank.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="transaction_type")
public class TransctionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_type_id")
    private int id;

    @OneToOne
    @JoinColumn(name="fee_type_id")
    private FeeType feeType;

    @Column(name="transaction_type_name")
    private String transactionTypeName;

    @Column(name="transaction_fee")
    private double transactionFee;

    @Column(name="transaction_type_description")
    private String transactionTypeDescription;

    public TransctionType(FeeType feeType, String transactionTypeName, double transactionFee,
                          String transactionTypeDescription) {
        this.feeType = feeType;
        this.transactionTypeName = transactionTypeName;
        this.transactionFee = transactionFee;
        this.transactionTypeDescription = transactionTypeDescription;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

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
