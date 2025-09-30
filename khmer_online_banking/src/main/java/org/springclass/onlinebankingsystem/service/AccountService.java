package org.springclass.onlinebankingsystem.service;

import jakarta.annotation.Nullable;
import org.springclass.onlinebankingsystem.controller.request.AccountUpdateRequest;
import org.springclass.onlinebankingsystem.repository.entity.Account;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account saveAccount(Account account);
    Account update(AccountUpdateRequest account);
    void updateBalance(Long id, Double balance);
    List<Account> findAll();
    Page<Account> findAll(@Nullable String query, int page, int size);
    Account findById(Long id);
    Account findByAccountNumber(String accountNumber);
    void deleteAccount(Long id);
    Optional<List<Account>> getAllAccountsByUserId(Long id);
}
