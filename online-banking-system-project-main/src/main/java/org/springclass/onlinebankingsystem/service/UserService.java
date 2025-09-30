package org.springclass.onlinebankingsystem.service;

import jakarta.annotation.Nullable;
import org.springclass.onlinebankingsystem.controller.request.UserRoleRequest;
import org.springclass.onlinebankingsystem.controller.response.UserResponse;
import org.springclass.onlinebankingsystem.repository.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserResponse> findAll(@Nullable String query, int page, int size);
    User find(Long id);
    void createUser(User user);
    User update(User user);
    void delete(Long id);
    User getUserInfo();
    User findUserEnabled(String username);
    Boolean checkUserEnabled(String username);
    User assignRole(UserRoleRequest request);
}
