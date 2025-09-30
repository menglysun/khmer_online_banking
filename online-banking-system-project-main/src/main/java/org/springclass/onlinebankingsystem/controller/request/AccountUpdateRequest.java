package org.springclass.onlinebankingsystem.controller.request;

import org.springclass.onlinebankingsystem.repository.entity.enumerate.AccountType;

public record AccountUpdateRequest(
        Long id,
        String accountName,
        String accountNumber,
        AccountType accountType
) {}
