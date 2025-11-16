package com.upc.quadrapp.iam.domain.model.aggregates;

import com.upc.quadrapp.iam.domain.model.entities.Role;
import com.upc.quadrapp.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User extends AuditableAbstractAggregateRoot<User> {

    @Getter
    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @Getter
    @Email
    @NotBlank
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @Getter
    @NotBlank
    @Size(max = 50)
    private String firstName;

    @Getter
    @NotBlank
    @Size(max = 50)
    private String lastName;

    @Getter
    @NotBlank
    @Size(max = 120)
    private String password;

    @Getter
    @Setter
    private boolean isEmailVerified = false;

    @Getter
    @Setter
    private LocalDateTime lastLoginAt;

    @Getter
    @Setter
    @Size(max = 20)
    private String plan = "basic";

    @Getter
    private boolean acceptTerms;

    @Getter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() { this.roles = new HashSet<>();}

    public User(String username, String password) {
        this();
        this.username = username;
        this.email = username; // fallback
        this.password = password;
    }

    public User(String email, String password, String firstName, String lastName, boolean acceptTerms) {
        this();
        this.email = email;
        this.username = email; // use email as username
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.acceptTerms = acceptTerms;
    }

    public User(String email, String password, String firstName, String lastName, boolean acceptTerms, List<Role> roles) {
        this(email, password, firstName, lastName, acceptTerms);
        addRoles(roles);
    }

    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public User addRoles(List<Role> roles) {
        var validatedRoles = Role.validateRoleSet(roles);
        this.roles.addAll(validatedRoles);
        return this;
    }
}
