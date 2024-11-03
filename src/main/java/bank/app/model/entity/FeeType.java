package bank.app.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="fee_type")
public class FeeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fee_type_id")
    private int id;

    @Column(name="fee_code")
    private String feeCode;

    @Column(name="fee_name")
    private String feeName;

    @Column(name="fee_description")
    private String description;

    @Column(name="calculation_method")
    private String calculationMethod;

    public FeeType(String feeCode, String feeName, String description, String calculation_method) {
        this.feeCode = feeCode;
        this.feeName = feeName;
        this.description = description;
        this.calculationMethod = calculation_method;
    }

    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalculation_method(String calculation_method) {
        this.calculationMethod = calculation_method;
    }
}
