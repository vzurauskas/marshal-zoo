package com.vzurauskas.experiments.squirrel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.UUID;

@Repository
public interface SquirrelRepo extends CrudRepository<SquirrelRepo.DbEntry, UUID> {
    @Entity
    @Table(name = "squirrel")
    class DbEntry {
        @Id
        public UUID id;
        public String name;
        public String nut;

        public DbEntry() {}

        public DbEntry(UUID id, String name, String nut) {
            this.id = id;
            this.name = name;
            this.nut = nut;
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
