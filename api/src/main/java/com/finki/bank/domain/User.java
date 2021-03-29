package com.finki.bank.domain;

import com.finki.bank.domain.enumerations.Role;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@Entity
@Table(name = "users")
public class User extends TimestampEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Size()
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "activated", nullable = false)
    private boolean activated = false;

    @Column(name = "activation_token")
    private String activationToken;

    @Column(name = "reset_token")
    private String resetToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "owner")
    private List<Account> accounts = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_favourites",
        joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "favourite_id",referencedColumnName = "id"))
    private List<User> favouriteUsers = new ArrayList<>();

//    @Transient
//    private Account getDefaultAccount() {
//        if (accounts == null) {
//            return null;
//        }
//        return accounts.stream().filter().findFirst().orElse(null);
//    }

    @Transient
    private Account getAccountById(Long id) {
        if (id == null) {
            return null;
        }
        if (accounts == null) {
            return null;
        }
        return accounts.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
