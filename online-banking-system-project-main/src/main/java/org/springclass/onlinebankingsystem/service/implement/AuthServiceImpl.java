package org.springclass.onlinebankingsystem.service.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclass.onlinebankingsystem.controller.request.LoginRequest;
import org.springclass.onlinebankingsystem.controller.request.RegisterRequest;
import org.springclass.onlinebankingsystem.controller.response.AuthResponse;
import org.springclass.onlinebankingsystem.exception.CustomException;
import org.springclass.onlinebankingsystem.repository.UserRepository;
import org.springclass.onlinebankingsystem.repository.entity.User;
import org.springclass.onlinebankingsystem.service.AuthService;
import org.springclass.onlinebankingsystem.service.RoleService;
import org.springclass.onlinebankingsystem.shared.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userService;
    private final RoleService roleService;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        log.info("=====>>> Login Request: {}", loginRequest.getUsernameOrEmail());
        final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsernameOrEmail());

        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new CustomException(403, "Incorrect password!!!");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsernameOrEmail(), loginRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials", e);
        }

        final String token = jwtUtil.generateToken(userDetails);
        enableUser(userDetails.getUsername());
        return new AuthResponse(token, userDetails.getUsername());
    }

    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {
        log.info("=====>>> Register Request: {}", registerRequest);
        if (userRepository.existsByUsernameAndStatusTrue(registerRequest.username())) {
            throw new CustomException(404, "Username already exists!");
        }

        if (userRepository.existsByEmailAndStatusTrue(registerRequest.email())) {
            throw new CustomException(404, "Email already exists!");
        }

        User user = registerRequest.RegisterUser();
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setEnabled(true);
        registerRequest.roles().forEach(r -> {
            var role = roleService.findById(r.getId());
            user.addRole(role);
        });
        userService.createUser(user);

        final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        final String token = jwtUtil.generateToken(userDetails);
        return new AuthResponse(token, user.getUsername());
    }

    @Override
    public void logout() {
        var user = userService.getUserInfo();
        user.setEnabled(false);
        userRepository.save(user);
    }

    private void enableUser(String username) {
        var user = userRepository.findByUsernameOrEmail(username);
        if (user.isPresent()) {
            user.get().setEnabled(true);
            userRepository.save(user.get());
        }
        log.info("User \n{}", user);
    }
}
