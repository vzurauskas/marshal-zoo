package com.vzurauskas.experiments.racoon;

import com.vzurauskas.experiments.Racoon;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;

import java.util.UUID;

public class PersistedRacoon implements Racoon {
    private final UUID id;
    private final RacoonRepo repo;
    private final String name;
    private String colour;

    public PersistedRacoon(UUID id, RacoonRepo repo, String name, String colour) {
        this.id = id;
        this.repo = repo;
        this.name = name;
        this.colour = colour;
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public void paint(String colour) {
        this.colour = colour;
    }

    @Override
    public Json json() {
        return new MutableJson()
            .with("name", name)
            .with("colour", colour);
    }

    @Override
    public void save() {
        repo.save(new RacoonRepo.DbEntry(id, name, colour));
    }
}
