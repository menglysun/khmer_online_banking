package org.springclass.onlinebankingsystem.repository;

import org.springclass.onlinebankingsystem.base.repository.BaseRepository;
import org.springclass.onlinebankingsystem.repository.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account> {
    Optional<Account> findByAccountNumberAndStatusTrue(String accountNumber);
    Optional<List<Account>> findAllByUserIdAndStatusTrueOrderByIdAsc(Long id);
}
