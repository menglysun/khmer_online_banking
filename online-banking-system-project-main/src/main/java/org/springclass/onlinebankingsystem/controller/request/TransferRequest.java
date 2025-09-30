package org.springclass.onlinebankingsystem.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TransferRequest(
        @NotNull
        String fromAccountNumber,
        @NotNull
        String toAccountNumber,
        @NotNull
        @Min(0)
        Double amount,
        @NotNull
        String currency,
        String description
) {

}
