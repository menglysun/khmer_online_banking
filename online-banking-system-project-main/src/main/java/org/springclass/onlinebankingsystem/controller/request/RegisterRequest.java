package org.springclass.onlinebankingsystem.controller.request;

import org.springclass.onlinebankingsystem.repository.entity.Role;
import org.springclass.onlinebankingsystem.repository.entity.User;
import org.springclass.onlinebankingsystem.repository.entity.enumerate.Gender;

import java.util.List;

public record RegisterRequest(
        String username,
        String password,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        Gender gender,
        List<Role> roles) {

    public User RegisterUser() {
        var user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setGender(gender);
        return user;
    }
}
