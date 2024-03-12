package com.vzurauskas.experiments.squirrel;

import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;
import com.vzurauskas.nereides.jackson.SmartJson;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SquirrelNutEndpointTest {
    private final SquirrelNutEndpoint endpoint;
    private final Squirrels squirrels;

    public SquirrelNutEndpointTest() {
        SquirrelRepo repo = new FakeSquirrelRepo();
        this.endpoint = new SquirrelNutEndpoint(repo);
        this.squirrels = new Squirrels(repo);
    }

    @Test
    void paints() {
        UUID id = UUID.randomUUID();
        squirrels.create(id, "Chip", "nut").save();
        ResponseEntity<Json> response = endpoint.put(
            id, new MutableJson().with("nut", "nut")
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, squirrels.size());
        assertEquals("nut", new SmartJson(squirrels.byId(id).json()).leaf("nut"));
    }

}