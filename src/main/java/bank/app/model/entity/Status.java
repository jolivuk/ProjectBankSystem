package bank.app.model.entity;

import bank.app.model.enums.RoleName;
import bank.app.model.enums.StatusName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_name")
    private StatusName statusName;



}
