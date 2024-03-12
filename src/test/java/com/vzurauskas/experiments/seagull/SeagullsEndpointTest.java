package com.vzurauskas.experiments.seagull;

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

class SeagullsEndpointTest {
    private final SeagullsEndpoint endpoint;
    private final Seagulls seagulls;

    public SeagullsEndpointTest() {
        SeagullRepo repo = new FakeSeagullRepo();
        this.endpoint = new SeagullsEndpoint(repo);
        this.seagulls = new Seagulls(repo);
    }

    @Test
    void gets() {
        UUID gulliverId = UUID.randomUUID();
        seagulls.create(gulliverId, "Gulliver").save();
        UUID marinaId = UUID.randomUUID();
        seagulls.create(marinaId, "Marina").save();
        ResponseEntity<ArrayNode> response = endpoint.get();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ArrayNode body = response.getBody();
        assertEquals(2, body.size());

        JsonNode gulliver = body.get(0);
        assertEquals(gulliverId.toString(), gulliver.get("id").asText());
        assertEquals("Gulliver", gulliver.get("name").asText());
        assertEquals(0, gulliver.get("flyingAt").asInt());

        JsonNode marina = body.get(1);
        assertEquals(marinaId.toString(), marina.get("id").asText());
        assertEquals("Marina", marina.get("name").asText());
        assertEquals(0, marina.get("flyingAt").asInt());
    }

    @Test
    void creates() {
        ResponseEntity<Json> response = endpoint.post(
            new SeagullsEndpoint.PostSeagulls("Gulliver")
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(new SmartJson(response.getBody()).textual().contains("Gulliver"));
        assertEquals(1, seagulls.size());
    }
}