package org.springclass.onlinebankingsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclass.onlinebankingsystem.controller.request.LoginRequest;
import org.springclass.onlinebankingsystem.controller.request.RegisterRequest;
import org.springclass.onlinebankingsystem.service.AuthService;
import org.springclass.onlinebankingsystem.shared.object.ResponseObjectMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ResponseObjectMap responseObjectMap;


    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest) {
        return responseObjectMap.responseObject(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public Map<String, Object> logout() {
        authService.logout();
        return responseObjectMap.responseCodeWithMessage(200, "Logout successful");
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody RegisterRequest registerRequest) {
        return responseObjectMap.responseObject(authService.register(registerRequest));
    }
}
