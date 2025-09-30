package org.springclass.onlinebankingsystem.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.springclass.onlinebankingsystem.repository.entity.Account;
import org.springclass.onlinebankingsystem.repository.entity.User;
import org.springclass.onlinebankingsystem.repository.entity.enumerate.AccountType;
import org.springclass.onlinebankingsystem.repository.entity.enumerate.Currency;

@Getter
@Setter
public class AccountCreateRequest {
    private AccountType accountType;
    private Currency currency;
    private UserIdRequest user;

    public AccountCreateRequest(AccountType accountType,  Currency currency, UserIdRequest user) {
        this.accountType = accountType;
        this.currency = currency;
        this.user = user;
    }

    public record UserIdRequest(Long userId) {
    }

    public Account CreateRequest (AccountCreateRequest request) {
        Account account = new Account();
        account.setAccountType(request.accountType);
        account.setCurrency(request.currency);
        User user = new User();
        user.setId(request.user.userId);
        account.setUser(user);
        return account;
    }
}
