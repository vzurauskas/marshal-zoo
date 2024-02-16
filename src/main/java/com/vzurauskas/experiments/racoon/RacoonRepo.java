package com.vzurauskas.experiments.racoon;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RacoonRepo extends CrudRepository<RacoonRepo.DbEntry, UUID> {
    @Entity
    @Table(name = "racoon")
    class DbEntry {
        @Id
        public UUID id;
        public String iban;
        public String currency;

        public DbEntry() {}

        public DbEntry(UUID id, String iban, String currency) {
            this.id = id;
            this.iban = iban;
            this.currency = currency;
        }
    }
}
