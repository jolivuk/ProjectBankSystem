package bank.app.entity;

import bank.app.entity.enums.DocumentType;
import bank.app.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="is_active")
    private boolean isActive = true;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "private_info_id")
    private PrivateInfo privateInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_id")
    private Role role;

    @ManyToOne()
    @JoinColumn(name="manager_id",referencedColumnName = "user_id")
    private User manager;

    @Column(name="created_at")
    private LocalDate createdAt;

    public User(String username, String password, boolean isActive,
                PrivateInfo privateInfo, Role role, User manager, LocalDate createdAt) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.privateInfo = privateInfo;
        this.role = role;
        this.manager = manager;
        this.createdAt = createdAt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setPrivateInfo(PrivateInfo privateInfo) {
        this.privateInfo = privateInfo;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
}
