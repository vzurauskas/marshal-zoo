package com.vzurauskas.experiments.seagull;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.UUID;

@Repository
public interface SeagullRepo extends CrudRepository<SeagullRepo.DbEntry, UUID> {
    @Entity
    @Table(name = "seagull")
    class DbEntry {
        @Id
        public UUID id;
        public String name;
        public int height;

        public DbEntry() {}

        public DbEntry(UUID id, String name, int height) {
            this.id = id;
            this.name = name;
            this.height = height;
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
