package org.springclass.onlinebankingsystem.service;

import org.springclass.onlinebankingsystem.controller.request.DepositRequest;
import org.springclass.onlinebankingsystem.controller.request.TransferRequest;
import org.springclass.onlinebankingsystem.controller.request.WithdrawRequest;
import org.springclass.onlinebankingsystem.controller.response.TransactionResponse;
import org.springclass.onlinebankingsystem.repository.entity.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    void save(Transaction transactions);
    Optional<List<TransactionResponse>> getAllByAccountId(String accountNumber, Long userId);
    Page<TransactionResponse> getAllByAccountId(String accountNumber, Long userId, int page, int size);
    List<Transaction> findAll();
    /** Transaction Option */
    void transfer(TransferRequest request);
    void deposit(DepositRequest request);
    void withdraw(WithdrawRequest request);
}
