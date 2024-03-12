package com.vzurauskas.experiments.seagull;

import com.vzurauskas.experiments.Seagull;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;

import java.util.UUID;

public class PersistedSeagull implements Seagull {
    private final UUID id;
    private final SeagullRepo repo;
    private final String name;
    private int height;

    public PersistedSeagull(UUID id, SeagullRepo repo, String name, int height) {
        this.id = id;
        this.repo = repo;
        this.name = name;
        this.height = height;
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public void fly(int height) {
        this.height = height;
    }

    @Override
    public Json json() {
        return new MutableJson()
            .with("id", id.toString())
            .with("name", name)
            .with("flyingAt", height);
    }

    @Override
    public void save() {
        repo.save(new SeagullRepo.DbEntry(id, name, height));
    }
}
