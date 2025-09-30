package org.springclass.onlinebankingsystem.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.springclass.onlinebankingsystem.repository.entity.Role;

import java.util.List;

public class UserRoleRequest {
    @Setter
    @Getter
    private Long id;
    @Setter
    @Getter
    private List<Role> roles;
}
