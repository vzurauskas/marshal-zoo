package com.vzurauskas.experiments.seagull;

import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.SmartJson;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeagullHeightEndpointTest {
    private final SeagullHeightEndpoint endpoint;
    private final Seagulls seagulls;

    public SeagullHeightEndpointTest() {
        SeagullRepo repo = new FakeSeagullRepo();
        this.endpoint = new SeagullHeightEndpoint(repo);
        this.seagulls = new Seagulls(repo);
    }

    @Test
    void paints() {
        UUID id = UUID.randomUUID();
        seagulls.create(id, "Gulliver").save();
        ResponseEntity<Json> response = endpoint.put(
            id, new SeagullHeightEndpoint.SeagullHeightResource(17)
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, seagulls.size());
        assertEquals(17, new SmartJson(seagulls.byId(id).json()).leafAsInt("flyingAt"));
    }

}