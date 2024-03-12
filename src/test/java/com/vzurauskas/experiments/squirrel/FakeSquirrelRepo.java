package com.vzurauskas.experiments.squirrel;

import java.util.*;

public final class FakeSquirrelRepo implements SquirrelRepo {
    private final Map<UUID, DbEntry> entries;
    private final List<UUID> insertionOrder;

    public FakeSquirrelRepo() {
        this.entries = new LinkedHashMap<>();
        this.insertionOrder = new ArrayList<>();
    }

    @Override
    public <S extends DbEntry> S save(S entity) {
        if (entries.containsKey(entity.id)) {
            entries.put(entity.id, entity);
        } else {
            insertionOrder.add(entity.id);
            entries.put(entity.id, entity);
        }
        return entity;
    }

    @Override
    public Optional<DbEntry> findById(UUID uuid) {
        return Optional.ofNullable(entries.get(uuid));
    }

    @Override
    public long count() {
        return entries.size();
    }

    @Override
    public Iterable<DbEntry> findAll() {
        List<DbEntry> allEntries = new ArrayList<>();
        for (UUID id : insertionOrder) {
            allEntries.add(entries.get(id));
        }
        return allEntries;
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

