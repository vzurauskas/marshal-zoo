package com.vzurauskas.experiments.racoon;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface Repo extends CrudRepository<Repo.DbEntry, UUID> {
    @Entity
    @Table(name = "account")
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
