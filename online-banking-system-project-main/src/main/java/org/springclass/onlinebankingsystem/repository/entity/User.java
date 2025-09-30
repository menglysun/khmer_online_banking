package org.springclass.onlinebankingsystem.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springclass.onlinebankingsystem.base.entity.BaseEntity;
import org.springclass.onlinebankingsystem.repository.entity.enumerate.Gender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Setter
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity implements UserDetails {

    @Getter
    @Column(unique = true, nullable = false)
    private String username;

    @Getter
    @Column(nullable = false)
    private String password;

    @Getter
    @Column(nullable = false)
    private String email;

    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private String phoneNumber;

    @Getter
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Getter
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // join table
            joinColumns = @JoinColumn(name = "user_id"), // FK to User
            inverseJoinColumns = @JoinColumn(name = "role_id") // FK to Role
    )
    private Set<Role> roles = new HashSet<>();

    // Constructors
    public User() {}

    public User(String username, String password, String email, String firstName, String lastName,
                String phoneNumber, Gender gender, Set<Role> roles
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.roles = roles;
    }

    // UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roles.stream().findFirst().get().getRole().name()));
    }

    public List<Role> getRoles() {
        return this.roles.stream().toList();
    }

    // helper methods
    public void addAllRole(List<Role> roles) {
        this.roles.addAll(roles);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    @Override
    public String toString() {
        return "\n---------------------------------------\n" +
                "ID = " + getId() + "\n" +
                "Username = " + username + "\n" +
                "First Name = " + firstName + "\n" +
                "Last Name = " + lastName + "\n" +
                "Phone Number = " + phoneNumber + "\n" +
                "Gender = " + gender + "\n" +
                "Roles = " + roles.toString() +
                "\n---------------------------------------\n";
    }
}