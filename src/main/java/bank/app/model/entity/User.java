package bank.app.model.entity;

import bank.app.model.enums.Role;
import bank.app.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User implements UserDetails {
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

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            optional = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "private_info_id")
    private PrivateInfo privateInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="manager_id", referencedColumnName = "user_id")
    private User manager;


    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Account> accounts = new ArrayList<>();

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && status == user.status && Objects.equals(privateInfo, user.privateInfo) && role == user.role && Objects.equals(manager, user.manager) && Objects.equals(createdAt, user.createdAt) && Objects.equals(lastUpdate, user.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, status, privateInfo, role, manager, createdAt, lastUpdate);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonLocked() {
        return !Status.BLOCKED.equals(status);
    }
    @Override
    public boolean isEnabled() {
        return status.equals(Status.ACTIVE);
    }
}
