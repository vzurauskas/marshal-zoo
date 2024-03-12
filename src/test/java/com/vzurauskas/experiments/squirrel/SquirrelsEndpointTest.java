package com.vzurauskas.experiments.squirrel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;
import com.vzurauskas.nereides.jackson.SmartJson;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SquirrelsEndpointTest {
    private final SquirrelsEndpoint endpoint;
    private final Squirrels squirrels;

    public SquirrelsEndpointTest() {
        SquirrelRepo repo = new FakeSquirrelRepo();
        this.endpoint = new SquirrelsEndpoint(repo);
        this.squirrels = new Squirrels(repo);
    }

    @Test
    void gets() {
        UUID chipId = UUID.randomUUID();
        squirrels.create(chipId, "Chip", "nut").save();
        UUID daleId = UUID.randomUUID();
        squirrels.create(daleId, "Dale", "anotherNut").save();
        ResponseEntity<ArrayNode> response = endpoint.get();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ArrayNode body = response.getBody();
        assertEquals(2, body.size());

        JsonNode chip = body.get(0);
        assertEquals(chipId.toString(), chip.get("id").asText());
        assertEquals("Chip", chip.get("name").asText());
        assertEquals("nut", chip.get("nut").asText());

        JsonNode dale = body.get(1);
        assertEquals(daleId.toString(), dale.get("id").asText());
        assertEquals("Dale", dale.get("name").asText());
        assertEquals("anotherNut", dale.get("nut").asText());
    }

    @Test
    void creates() {
        ResponseEntity<Json> response = endpoint.post(
            new MutableJson()
                .with("name", "Chip")
                .with("nut", "nut")
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(new SmartJson(response.getBody()).textual().contains("Chip"));
        assertEquals(1, squirrels.size());
    }
}