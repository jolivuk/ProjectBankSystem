package bank.app.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
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

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name="fee_schedule_id")
    private FeeSchedule feeSchedule;


    @Column(name="assigned_at")
    private LocalDateTime assignedAt;

    @Column(name="created_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    public AccountFeeSchedule(Account account, FeeSchedule feeSchelude, LocalDateTime assignedAt,
                              LocalDateTime createdAt) {
        this.account = account;
        this.feeSchedule = feeSchelude;
        this.assignedAt = assignedAt;
        this.createdAt = createdAt;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setFeeSchelude(FeeSchedule feeSchelude) {
        this.feeSchedule = feeSchelude;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}
