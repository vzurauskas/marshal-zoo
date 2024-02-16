package com.vzurauskas.experiments.racoon;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class FakeRacoonRepo implements RacoonRepo {
    private final Set<DbEntry> entries;

    public FakeRacoonRepo() {
        this.entries = new HashSet<>();
    }

    @Override
    public <S extends DbEntry> S save(S entity) {
        entries.remove(entity);
        entries.add(entity);
        return entity;
    }

    @Override
    public Optional<DbEntry> findById(UUID uuid) {
        return entries.stream()
            .filter(entity -> entity.id.equals(uuid))
            .findAny();
    }

    @Override
    public long count() {
        return entries.size();
    }

    @Override
    public <S extends DbEntry> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existsById(UUID uuid) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<DbEntry> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<DbEntry> findAllById(Iterable<UUID> uuids) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(UUID uuid) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(DbEntry entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends DbEntry> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }
}

