package com.vzurauskas.experiments.squirrel;

import com.vzurauskas.experiments.Racoon;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;

import java.util.UUID;

public class PersistedSquirrel implements Racoon {
    private final UUID id;
    private final SquirrelRepo repo;
    private final String name;
    private String nut;

    public PersistedSquirrel(UUID id, SquirrelRepo repo, String name, String nut) {
        this.id = id;
        this.repo = repo;
        this.name = name;
        this.nut = nut;
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public void paint(String nut) {
        this.nut = nut;
    }

    @Override
    public Json json() {
        return new MutableJson()
            .with("id", id.toString())
            .with("name", name)
            .with("nut", nut);
    }

    @Override
    public void save() {
        repo.save(new SquirrelRepo.DbEntry(id, name, nut));
    }
}
