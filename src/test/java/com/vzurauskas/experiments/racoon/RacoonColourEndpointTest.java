package com.vzurauskas.experiments.racoon;

import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.SmartJson;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static com.vzurauskas.experiments.racoon.RacoonColourEndpoint.*;
import static org.junit.jupiter.api.Assertions.*;

class RacoonColourEndpointTest {
    private final RacoonColourEndpoint endpoint;
    private final Racoons racoons;

    public RacoonColourEndpointTest() {
        this.endpoint = new RacoonColourEndpoint();
        this.racoons = new Racoons(new FakeRacoonRepo());
    }

    @Test
    void paints() {
        UUID id = UUID.randomUUID();
        racoons.create(id, "Bandit", "Black").save();
        ResponseEntity<Json> response = endpoint.put(
            id, new PutColour(racoons, "Red")
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, racoons.size());
        assertEquals("Red", new SmartJson(racoons.byId(id).json()).leaf("colour"));
    }

}