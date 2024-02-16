package com.vzurauskas.experiments.racoon;

import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.SmartJson;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RacoonResourceTest {


    @Test
    void opensNewAccount() {
        RacoonRepo repo = new FakeRacoonRepo();
        RacoonResource res = new RacoonResource();
        ResponseEntity<Json> response = res.postRacoon(
            new RacoonResource.Resource(repo, "Bandit", "Black")
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(new SmartJson(response.getBody()).textual().contains("Bandit"));
        assertEquals(1, repo.count());
    }
}