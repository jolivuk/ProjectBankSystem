package bank.app.model.entity;

import bank.app.model.enums.Role;
import bank.app.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "private_info_id")
    private PrivateInfo privateInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="manager_id", referencedColumnName = "user_id")
    @JsonIgnore
    private User manager;

    @Column(name="created_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    public User(String username, String password, Status status, Role role, User manager) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
        this.manager = manager;
    }


    public void setStatus(Status status) {
        this.status = status;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPrivateInfo(PrivateInfo privateInfo) {
        this.privateInfo = privateInfo;
    }

    @JsonProperty("privateInfo")
    public PrivateInfo getPrivateInfoForJson() {
        return this.privateInfo;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

}
