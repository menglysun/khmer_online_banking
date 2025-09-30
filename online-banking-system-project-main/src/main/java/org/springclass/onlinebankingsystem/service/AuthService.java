package org.springclass.onlinebankingsystem.service;

import org.springclass.onlinebankingsystem.controller.request.LoginRequest;
import org.springclass.onlinebankingsystem.controller.request.RegisterRequest;
import org.springclass.onlinebankingsystem.controller.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
    void logout();
}
