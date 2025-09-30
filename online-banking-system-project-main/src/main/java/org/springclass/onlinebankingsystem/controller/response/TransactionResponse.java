package org.springclass.onlinebankingsystem.controller.response;

import lombok.Getter;
import lombok.Setter;
import org.springclass.onlinebankingsystem.repository.entity.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private Date transactionDate;
    private String transactionId;
    private String transactionType;
    private String fromAccountNumber;
    private String toAccountNumber;
    private Double debitAmount;
    private String currency;
    private Double requestAmount;
    private String requestCurrency;
    private String description;

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.transactionDate = transaction.getTransactionDate();
        this.transactionId = transaction.getTransactionId();
        this.transactionType = transaction.getTransactionType().name();
        if (transaction.getFromAccount() != null) {
            this.fromAccountNumber = transaction.getFromAccount().getAccountNumber();
        }
        if (transaction.getToAccount() != null) {
            this.toAccountNumber = transaction.getToAccount().getAccountNumber();
        }
        this.debitAmount = transaction.getAmount();
        this.currency = transaction.getCurrency().name();
        this.requestAmount = transaction.getRequestAmount();
        this.requestCurrency = transaction.getRequestCurrency();
        if (transaction.getDescription() != null) {
            this.description = transaction.getDescription();
        }
    }

    @Override
    public String toString() {
        return String.format(
                """
                \n------------------------------------------
                Transaction Date   : %s\s
                Transaction ID     : %s\s
                Transaction Type   : %s\s
                From Account       : %s\s
                Debit Amount       : %s %s\s
                To Account         : %s\s
                Amount             : %s %s\s
                Description        : %s\s
                ------------------------------------------
                """,
                new SimpleDateFormat("dd-MMM-yyyy hh:mm aa").format(transactionDate),
                transactionId, transactionType,
                (fromAccountNumber != null ? fromAccountNumber : ""),
                debitAmount, currency,
                (toAccountNumber != null ? toAccountNumber : ""),
                requestAmount, requestCurrency, description
        );
    }
}
