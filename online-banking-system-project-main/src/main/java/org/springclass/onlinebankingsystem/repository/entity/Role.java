package org.springclass.onlinebankingsystem.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springclass.onlinebankingsystem.base.entity.BaseEntity;
import org.springclass.onlinebankingsystem.repository.entity.enumerate.RoleType;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends BaseEntity {
    private String name;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public Role() {}

    public Role(String name, RoleType role) {
        this.name = name;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role = " + role ;
    }
}
