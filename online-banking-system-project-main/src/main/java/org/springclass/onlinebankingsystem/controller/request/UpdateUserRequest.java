package org.springclass.onlinebankingsystem.controller.request;

import org.springclass.onlinebankingsystem.repository.entity.Role;
import org.springclass.onlinebankingsystem.repository.entity.User;
import org.springclass.onlinebankingsystem.repository.entity.enumerate.Gender;

import java.util.List;

public record UpdateUserRequest(Long id, String firstName, String lastName, String username,
                                String phoneNumber, String email, Gender gender,
                                List<Role> roles) {
    public User UserRequest() {
        var user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setGender(gender);
        user.addAllRole(roles);
        return user;
    }
}
