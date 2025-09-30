package org.springclass.onlinebankingsystem.repository.entity.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {
    USD("01",1.0),
    KHR("02",4000.0);

    private final String code;
    private final Double exchangeRate;

    public static Currency find(String name) {
        for (Currency currency : Currency.values()) {
            if (currency.name().equals(name)) {
                return currency;
            }
        }
        return Currency.USD;
    }
}
