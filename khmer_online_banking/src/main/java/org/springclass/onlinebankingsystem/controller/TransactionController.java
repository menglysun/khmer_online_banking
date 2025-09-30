package org.springclass.onlinebankingsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springclass.onlinebankingsystem.controller.request.DepositRequest;
import org.springclass.onlinebankingsystem.controller.request.TransferRequest;
import org.springclass.onlinebankingsystem.controller.request.WithdrawRequest;
import org.springclass.onlinebankingsystem.service.TransactionService;
import org.springclass.onlinebankingsystem.shared.object.ResponseObjectMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;
    private final ResponseObjectMap response;

    @PostMapping("/transfer")
    public Map<String, Object> transfer(@Valid @RequestBody TransferRequest request) {
        service.transfer(request);
        return response.responseCodeWithMessage(200, "Transfer Success");
    }

    @PostMapping("/deposit")
    public Map<String, Object> deposit(@Valid @RequestBody DepositRequest request) {
        service.deposit(request);
        return response.responseCodeWithMessage(200, "Deposit Success");
    }

    @PostMapping("/withdraw")
    public Map<String, Object> withdraw(@Valid @RequestBody WithdrawRequest request) {
        service.withdraw(request);
        return response.responseCodeWithMessage(200, "Withdraw Success");
    }

    @GetMapping("/list/user/{userId}/account/{accountNumber}")
    public Map<String, Object> getListTransactionByAccountId(
            @PathVariable Long userId,
            @PathVariable String accountNumber,
            @RequestParam int page,
            @RequestParam int size
    ) {
        var item = service.getAllByAccountId(accountNumber, userId, page, size);
        return response.responseObject(item.getContent(), item.getTotalElements(), page, size);
    }
}
