package com.vzurauskas.experiments.squirrel;

import com.vzurauskas.experiments.Squirrel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Squirrels {
    private final SquirrelRepo repo;

    public Squirrels(SquirrelRepo repo) {
        this.repo = repo;
    }

    public Squirrel create(UUID id, String name, String colour) {
        return new PersistedSquirrel(id, repo, name, colour);
    }

    public Squirrel byId(UUID id) {
        SquirrelRepo.DbEntry squirrel = repo.findById(id).orElseThrow();
        return new PersistedSquirrel(squirrel.id, repo, squirrel.name, squirrel.nut);
    }

    public Collection<Squirrel> all() {
        List<Squirrel> squirrels = new ArrayList<>();
        repo.findAll().forEach(
            squirrel -> squirrels.add(
                new PersistedSquirrel(squirrel.id, repo, squirrel.name, squirrel.nut)
            )
        );
        return squirrels;
    }

    public int size() {
        return (int) repo.count();
    }
}
