package com.vzurauskas.experiments.racoon;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.UUID;

@Repository
public interface RacoonRepo extends CrudRepository<RacoonRepo.DbEntry, UUID> {
    @Entity
    @Table(name = "racoon")
    class DbEntry {
        @Id
        public UUID id;
        public String name;
        public String colour;

        public DbEntry() {}

        public DbEntry(UUID id, String name, String colour) {
            this.id = id;
            this.name = name;
            this.colour = colour;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DbEntry dbEntry = (DbEntry) o;
            return Objects.equals(id, dbEntry.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
