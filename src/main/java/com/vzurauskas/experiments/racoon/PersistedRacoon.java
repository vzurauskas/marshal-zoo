package com.vzurauskas.experiments.racoon;

import com.vzurauskas.experiments.Racoon;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;

import java.util.UUID;

public class PersistedRacoon implements Racoon {
    private final UUID id;
    private final String name;
    private final String colour;

    public PersistedRacoon(String name, String colour) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.colour = colour;
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public Json json() {
        return new MutableJson()
            .with("name", name)
            .with("colour", colour);
    }

    public Repo.DbEntry dbEntry() {
        return new Repo.DbEntry(id, name, colour);
    }
}
