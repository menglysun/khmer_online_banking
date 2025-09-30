package org.springclass.onlinebankingsystem.service.implement;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclass.onlinebankingsystem.controller.request.AccountUpdateRequest;
import org.springclass.onlinebankingsystem.exception.CustomException;
import org.springclass.onlinebankingsystem.repository.AccountRepository;
import org.springclass.onlinebankingsystem.repository.entity.Account;
import org.springclass.onlinebankingsystem.repository.entity.enumerate.Currency;
import org.springclass.onlinebankingsystem.service.AccountService;
import org.springclass.onlinebankingsystem.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final UserService userService;

    @Override
    @Transactional
    public Account saveAccount(Account account) {
        var user = userService.find(account.getUser().getId());
        account.setAccountNumber(generateAccountNumber(user.getId(), account.getCurrency()));
        account.setAccountName(user.getLastName() + " " +  user.getFirstName());
        account.setBalance(0.0);
        account.setUser(user);
        return repository.save(account);
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Account> findAll(String query, int page, int size) {
        return repository.findAll((root, cq, cb) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();
            if (query != null && !query.isBlank()) {
                var accountNumber = cb.like(cb.upper(root.get("accountNumber")), "%" + query.toUpperCase() + "%");
                var accountName = cb.like(cb.upper(root.get("accountName")), "%" + query.toUpperCase() + "%");
                predicates.add(cb.or(accountNumber, accountName));
            }
            predicates.add(cb.isTrue(root.get("status")));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public Account findById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new CustomException(404, "Account id %d not found".formatted(id)));
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        final var account = repository.findByAccountNumberAndStatusTrue(accountNumber).orElse(null);
        if (account == null) {
            throw new CustomException(404, "Account number %s not found".formatted(accountNumber));
        }
        return account;
    }

    @Override
    public Account update(AccountUpdateRequest account) {
        final var entity = findById(account.id());
        entity.setAccountNumber(account.accountNumber());
        entity.setAccountName(account.accountName());
        entity.setAccountType(account.accountType());
        return repository.save(entity);
    }

    @Override
    public void updateBalance(Long id, Double balance) {
        final var entity = findById(id);
        entity.setBalance(balance);
        repository.save(entity);
    }

    @Override
    public void deleteAccount(Long id) { repository.deleteById(id); }

    @Override
    public Optional<List<Account>> getAllAccountsByUserId(Long id) {
        return repository.findAllByUserIdAndStatusTrueOrderByIdAsc(id);
    }

    private String generateAccountNumber(Long userId, Currency currency) {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        int zeroAdd = 3 - userId.toString().length();
        accountNumber.append("0".repeat(Math.max(0, zeroAdd)));
        var firstNumber = accountNumber.append(userId);
        firstNumber.append(currency.getCode());
        int nextDigit = 14 - firstNumber.length();
        for (int i = 0; i < nextDigit; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }
}
