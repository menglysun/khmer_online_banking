package org.springclass.onlinebankingsystem.repository;

import org.springclass.onlinebankingsystem.base.repository.BaseRepository;
import org.springclass.onlinebankingsystem.repository.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction> {
    @Query("SELECT t FROM Transaction t WHERE t.accountNumber = ?1 AND t.userId = ?2 AND t.status = true ORDER BY t.id DESC")
    Optional<List<Transaction>> findAllByUserIdAndAccountNumber(String accountNumber, Long userId);
}
