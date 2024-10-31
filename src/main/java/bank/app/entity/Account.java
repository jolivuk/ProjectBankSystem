package bank.app.entity;

import bank.app.entity.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "account")
@NoArgsConstructor
@Getter
public class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_id")
    private Status status;

    @Column(name = "balace")
    private Double balance;

    @Column(name = "created_at")
    private LocalDateTime created_at;


    public Account(User user, Status status, Double balance, LocalDateTime created_at) {
        this.user = user;
        this.status = status;
        this.balance = balance;
        this.created_at = created_at;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
