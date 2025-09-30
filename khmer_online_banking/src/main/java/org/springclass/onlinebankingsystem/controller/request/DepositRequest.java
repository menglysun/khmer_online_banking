package org.springclass.onlinebankingsystem.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DepositRequest(
        @NotNull
        String accountNumber,
        @NotNull
        @Min(0)
        Double amount,
        @NotNull
        String currency
) {}
