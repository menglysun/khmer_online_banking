package org.springclass.onlinebankingsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springclass.onlinebankingsystem.controller.request.AccountCreateRequest;
import org.springclass.onlinebankingsystem.controller.request.AccountUpdateRequest;
import org.springclass.onlinebankingsystem.service.AccountService;
import org.springclass.onlinebankingsystem.shared.object.ResponseObjectMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;
    private final ResponseObjectMap response;

    @PostMapping
    public Map<String, Object> add(@RequestBody AccountCreateRequest request) {
        return response.responseObject(service.saveAccount(request.CreateRequest(request)));
    }

    @PutMapping
    public Map<String, Object> updateRole(@RequestBody AccountUpdateRequest request) {
        return response.responseObject(service.update(request));
    }

    @GetMapping("/list")
    public Map<String, Object> getList(
            @RequestParam(required = false) String query,
            @RequestParam int page,
            @RequestParam int size
    ) {
        var item = service.findAll(query, page, size);
        return response.responseObject(item.getContent(), item.getTotalElements(), page, size);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        return response.responseObject(service.findById(id));
    }

    @GetMapping("/user/{id}")
    public Map<String, Object> getAllAccountsByUserId(@PathVariable Long id) {
        return response.responseObject(service.getAllAccountsByUserId(id));
    }

    @GetMapping("/account-number/{accountNumber}")
    public Map<String, Object> getById(@PathVariable String accountNumber) {
        return response.responseObject(service.findByAccountNumber(accountNumber));
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteUserById(@PathVariable Long id) {
        service.deleteAccount(id);
        return response.responseCodeWithMessage(200, "Delete successfully");
    }
}
