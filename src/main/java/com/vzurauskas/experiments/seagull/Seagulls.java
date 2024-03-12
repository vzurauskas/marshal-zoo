package com.vzurauskas.experiments.seagull;

import com.vzurauskas.experiments.Seagull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Seagulls {
    private final SeagullRepo repo;

    public Seagulls(SeagullRepo repo) {
        this.repo = repo;
    }

    public Seagull create(UUID id, String name) {
        return new PersistedSeagull(id, repo, name, 0);
    }

    public Seagull byId(UUID id) {
        SeagullRepo.DbEntry seagull = repo.findById(id).orElseThrow();
        return new PersistedSeagull(seagull.id, repo, seagull.name, seagull.height);
    }

    public Collection<Seagull> all() {
        List<Seagull> seagulls = new ArrayList<>();
        repo.findAll().forEach(
            seagull -> seagulls.add(
                new PersistedSeagull(seagull.id, repo, seagull.name, seagull.height)
            )
        );
        return seagulls;
    }

    public int size() {
        return (int) repo.count();
    }
}
