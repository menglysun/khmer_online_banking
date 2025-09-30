package org.springclass.onlinebankingsystem.repository;

import org.springclass.onlinebankingsystem.base.repository.BaseRepository;
import org.springclass.onlinebankingsystem.repository.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByUsername(String username);
    boolean existsByUsernameAndStatusTrue(String username);
    boolean existsByEmailAndStatusTrue(String email);
    @Query("SELECT u FROM User u WHERE u.username = ?1 OR u.email = ?1 AND u.status = true ")
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);
    @Query("SELECT u FROM User u WHERE (u.username = ?1 OR u.email = ?1) AND u.status = true AND u.enabled = true")
    Optional<User> findByUsernameOrEmailAndStatusTrueAndEnabledTrue(String usernameOrEmail);
}
