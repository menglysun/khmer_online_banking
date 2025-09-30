package org.springclass.onlinebankingsystem.service.implement;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclass.onlinebankingsystem.configuration.AuditingConfig;
import org.springclass.onlinebankingsystem.controller.request.UserRoleRequest;
import org.springclass.onlinebankingsystem.controller.response.UserResponse;
import org.springclass.onlinebankingsystem.exception.CustomException;
import org.springclass.onlinebankingsystem.repository.UserRepository;
import org.springclass.onlinebankingsystem.repository.entity.User;
import org.springclass.onlinebankingsystem.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final AuditingConfig auditingConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameOrEmail(username).orElseThrow(() ->
                new CustomException(404, "Username or email does not exist!!!"));
    }

    @Override
    public User find(Long id) {
        return userRepository.findByIdAndStatusTrue(id).orElseThrow(() ->
                new CustomException(404, "User not found!"));
    }

    @Override
    public Page<UserResponse> findAll(String query, int page, int size) {
        return userRepository.findAll((root, cq, cb) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();
            if (query != null && !query.isBlank()) {
                var username = cb.like(cb.upper(root.get("username")), "%" + query.toUpperCase() + "%");
                var firstName = cb.like(cb.upper(root.get("firstName")), "%" + query.toUpperCase() + "%");
                var lastName = cb.like(cb.upper(root.get("lastName")), "%" + query.toUpperCase() + "%");
                var email = cb.like(cb.upper(root.get("email")), "%" + query.toUpperCase() + "%");
                predicates.add(cb.or(username, firstName, lastName, email));
            }
            predicates.add(cb.isTrue(root.get("status")));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))).map(UserResponse::new);
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    @Override
    public User update(User user) {
        var data = find(user.getId());
        data.setUsername(user.getUsername());
        data.setEmail(user.getEmail());
        data.setFirstName(user.getFirstName());
        data.setLastName(user.getLastName());
        data.setPhoneNumber(user.getPhoneNumber());
        data.setGender(user.getGender());
        if (user.getRoles() != null) {
            data.addAllRole(user.getRoles());
        }
        log.info("===>>> Update");
        return userRepository.save(data);
    }

    @Override
    public void delete(Long id) {
        var user = find(id);
        user.setStatus(false);
        user.setEnabled(false);
        userRepository.save(user);
    }


    @Override
    public User getUserInfo() {
        var user = auditingConfig.getCurrentAuditor().orElseThrow(() ->
                new CustomException(401, "Jwt expired or user not exist"));
        return findUserEnabled(user.getUsername());
    }

    @Override
    public User findUserEnabled(String username) {
        return userRepository.findByUsernameOrEmailAndStatusTrueAndEnabledTrue(username).orElseThrow(() ->
                new CustomException(406, "This user has been logout, Please login again."));
    }

    @Override
    public Boolean checkUserEnabled(String username) {
        var user = userRepository.findByUsernameOrEmailAndStatusTrueAndEnabledTrue(username);
        return user.isPresent();
    }

    @Override
    public User assignRole(UserRoleRequest request) {
        var data = find(request.getId());
        data.addAllRole(request.getRoles());
        log.info("===>>> Assign role to user");
        return userRepository.save(data);
    }
}