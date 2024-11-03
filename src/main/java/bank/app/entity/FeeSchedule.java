package bank.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name="fee_schedule")
public class FeeSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fee_schedule_id")
    private int id;

    @Column(name="is_active")
    private boolean isActive;

    @Column(name="valid_from")
    private LocalDateTime validFrom;

    @Column(name="valid_to")
    private LocalDateTime validTo;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    public FeeSchedule(boolean isActive, LocalDateTime validFrom, LocalDateTime validTo, LocalDateTime createdAt) {
        this.isActive = isActive;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }
}
