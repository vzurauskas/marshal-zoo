package com.vzurauskas.experiments.racoon;

import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.SmartJson;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RacoonsResourceTest {
    private final RacoonsEndpoint endpoint;
    private final Racoons racoons;

    public RacoonsResourceTest() {
        this.endpoint = new RacoonsEndpoint();
        this.racoons = new Racoons(new FakeRacoonRepo());
    }

    @Test
    void creates() {
        ResponseEntity<Json> response = endpoint.post(
            new RacoonsEndpoint.RacoonsResource(racoons, "Bandit", "Black")
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(new SmartJson(response.getBody()).textual().contains("Bandit"));
        assertEquals(1, racoons.size());
    }
}