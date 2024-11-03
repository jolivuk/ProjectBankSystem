package bank.app.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "fee_schedule_name")
    private String name;



    @Column(name = "fee_schedule_description")
    private String description;

    @Column(name="is_active")
    private boolean isActive;

    @Column(name="valid_from")
    private LocalDateTime validFrom;

    @Column(name="valid_to")
    private LocalDateTime validTo;

    @Column(name="created_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    public FeeSchedule(String name,String description,boolean isActive, LocalDateTime validFrom, LocalDateTime validTo, LocalDateTime createdAt) {
        this.name= name;
        this.description=description;
        this.isActive = isActive;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
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
