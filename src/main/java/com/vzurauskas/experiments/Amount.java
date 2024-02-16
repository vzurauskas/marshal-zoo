package com.vzurauskas.experiments;

import com.fasterxml.jackson.databind.JsonNode;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public final class Amount {
    private static final DecimalFormat format = new DecimalFormat("#,##0.00");

    private final BigDecimal amount;
    private final String currency;

    public Amount(JsonNode json) {
        this(
            new BigDecimal(json.get("amount").textValue()),
            json.get("currency").textValue()
        );
    }

    public Amount(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Amount plus(Amount other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies don't match.");
        }
        return new Amount(amount.add(other.amount), currency);
    }

    public Amount negate() {
        return new Amount(amount.negate(), currency);
    }

    public Json json() {
        return new MutableJson()
            .with("amount", format.format(amount))
            .with("currency", currency);
    }

    public String currency() {
        return currency;
    }

    public BigDecimal amount() {
        return amount;
    }
}
