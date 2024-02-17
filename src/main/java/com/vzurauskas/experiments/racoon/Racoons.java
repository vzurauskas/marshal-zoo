package com.vzurauskas.experiments.racoon;

import com.vzurauskas.experiments.Racoon;

import java.util.UUID;

public class Racoons {
    private final RacoonRepo repo;

    public Racoons(RacoonRepo repo) {
        this.repo = repo;
    }

    public Racoon create(UUID id, String name, String colour) {
        return new PersistedRacoon(id, repo, name, colour);
    }

    public Racoon byId(UUID id) {
        RacoonRepo.DbEntry racoon = repo.findById(id).orElseThrow();
        return new PersistedRacoon(racoon.id, repo, racoon.name, racoon.colour);
    }

    public int size() {
        return (int) repo.count();
    }
}
