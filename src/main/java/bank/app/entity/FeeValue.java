package bank.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name="fee_value")
public class FeeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fee_value_id")
    private int id;

    @Column(name="fixed_amount")
    private double fixedAmount;

    @Column(name="percentage_rate")
    private double percentageRate;

    @Column(name="min_amount")
    private double minAmount;

    @Column(name="max_amount")
    private double maxAmount;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="fee_type_id")
    private FeeType feeType;

    @OneToOne
    @JoinColumn(name="fee_schedule_id")
    private FeeSchedule feeSchedule;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    public FeeValue(double fixedAmount, double percentageRate, double minAmount, double maxAmount, FeeType feeType,
                    FeeSchedule feeSchedule, LocalDateTime createdAt) {
        this.fixedAmount = fixedAmount;
        this.percentageRate = percentageRate;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.feeType = feeType;
        this.feeSchedule = feeSchedule;
        this.createdAt = createdAt;
    }

    public void setFixedAmount(double fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public void setPercentageRate(double percentageRate) {
        this.percentageRate = percentageRate;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public void setFeeSchedule(FeeSchedule feeSchedule) {
        this.feeSchedule = feeSchedule;
    }
}
