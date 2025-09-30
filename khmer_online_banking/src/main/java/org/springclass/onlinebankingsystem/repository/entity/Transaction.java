package org.springclass.onlinebankingsystem.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springclass.onlinebankingsystem.base.entity.BaseEntity;
import org.springclass.onlinebankingsystem.repository.entity.enumerate.Currency;
import org.springclass.onlinebankingsystem.repository.entity.enumerate.TransactionType;

import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "transaction")
public class Transaction extends BaseEntity {
    private String accountNumber;
    private Double amount;
    private String description;
    private String transactionId;
    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Long userId;
    private Double requestAmount;
    private String requestCurrency;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    public Transaction() {}

    public Transaction(String accountNumber, Account fromAccount, Account toAccount, Double amount, TransactionType transactionType,
                       Currency currency, String description, Long userId, Double requestAmount, String requestCurrency
    ) {
        this.accountNumber = accountNumber;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transactionType = transactionType;
        this.currency = currency;
        this.description = description;
        this.userId = userId;
        this.requestAmount = requestAmount;
        this.requestCurrency = requestCurrency;
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
                (fromAccount != null ? fromAccount.getAccountNumber() : ""),
                amount, currency.name(),
                (toAccount != null ? toAccount.getAccountNumber() : ""),
                requestAmount, requestCurrency, description
        );
    }
}
