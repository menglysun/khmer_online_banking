package org.springclass.onlinebankingsystem.controller.response;

import lombok.Value;
import org.springclass.onlinebankingsystem.repository.entity.Role;
import org.springclass.onlinebankingsystem.repository.entity.User;
import org.springclass.onlinebankingsystem.repository.entity.enumerate.Gender;

import java.util.List;

@Value
public class UserResponse {
    Long id;
    String username;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    Gender gender;
    List<Role> roles;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.roles = user.getRoles();
    }
}
