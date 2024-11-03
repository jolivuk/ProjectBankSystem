package bank.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name="account_fee_schedule")
public class AccountFeeSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_fee_schedule_id")
    private int id;

    @OneToOne
    @JoinColumn(name="account_id")
    private Account account;

    @OneToOne
    @JoinColumn(name="fee_schedule_id")
    private FeeSchedule feeSchelude;

    @Column(name="assigned_at")
    private LocalDateTime assignedAt;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    public AccountFeeSchedule(Account account, FeeSchedule feeSchelude, LocalDateTime assignedAt,
                              LocalDateTime createdAt) {
        this.account = account;
        this.feeSchelude = feeSchelude;
        this.assignedAt = assignedAt;
        this.createdAt = createdAt;
    }

    public void setFeeSchelude(FeeSchedule feeSchelude) {
        this.feeSchelude = feeSchelude;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}
