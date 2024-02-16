package com.vzurauskas.experiments.racoon;

import com.vzurauskas.experiments.Racoon;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;

import java.util.UUID;

public class DefaultRacoon implements Racoon {
    private final UUID id;
    private final String iban;
    private final String currency;

    public DefaultRacoon(String iban, String currency) {
        this.id = UUID.randomUUID();
        this.iban = iban;
        this.currency = currency;
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public Json json() {
        return new MutableJson()
            .with("iban", iban)
            .with("currency", currency);
    }
}
