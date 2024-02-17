package com.vzurauskas.experiments.racoon;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.SmartJson;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RacoonsEndpointTest {
    private final RacoonsEndpoint endpoint;
    private final Racoons racoons;

    public RacoonsEndpointTest() {
        this.endpoint = new RacoonsEndpoint();
        this.racoons = new Racoons(new FakeRacoonRepo());
    }

    @Test
    void gets() {
        UUID banditId = UUID.randomUUID();
        racoons.create(banditId, "Bandit", "Black").save();
        UUID rascalId = UUID.randomUUID();
        racoons.create(rascalId, "Rascal", "Grey").save();
        ResponseEntity<ArrayNode> response = endpoint.get(
            new RacoonsEndpoint.GetRacoons(racoons)
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ArrayNode body = response.getBody();
        assertEquals(2, body.size());

        JsonNode bandit = body.get(0);
        assertEquals(banditId.toString(), bandit.get("id").asText());
        assertEquals("Bandit", bandit.get("name").asText());
        assertEquals("Black", bandit.get("colour").asText());

        JsonNode rascal = body.get(1);
        assertEquals(rascalId.toString(), rascal.get("id").asText());
        assertEquals("Rascal", rascal.get("name").asText());
        assertEquals("Grey", rascal.get("colour").asText());
    }

    @Test
    void creates() {
        ResponseEntity<Json> response = endpoint.post(
            new RacoonsEndpoint.PostRacoons(racoons, "Bandit", "Black")
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(new SmartJson(response.getBody()).textual().contains("Bandit"));
        assertEquals(1, racoons.size());
    }
}