package bank.app.model.entity;

import bank.app.model.enums.RoleName;
import bank.app.model.enums.StatusName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")

//@DynamicUpdate ее тоже можно
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;


    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    // @MapsId = почитать
    @JoinColumn(name = "private_info_id")
    private PrivateInfo privateInfo;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne()
    @JoinColumn(name="manager_id",referencedColumnName = "user_id")
    private User manager;

    @Column(name="created_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;


    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
}
