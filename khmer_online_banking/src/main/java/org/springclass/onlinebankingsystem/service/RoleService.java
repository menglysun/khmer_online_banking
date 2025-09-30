package org.springclass.onlinebankingsystem.service;

import org.springclass.onlinebankingsystem.repository.entity.Role;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role add(Role role);
    Role update(Role role);
    Optional<List<Role>> findAll();
    Role findById(Long id);
    Page<Role> findAll(Optional<String> query, int page, int size);
}
