package com.vzurauskas.experiments.racoon;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vzurauskas.experiments.Racoon;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/racoons")
public class RacoonColourEndpoint {

    @PutMapping("{id}/colour")
    public ResponseEntity<Json> put(
        @PathVariable UUID id, @RequestBody PutColour request
    ) {
        return new ResponseEntity<>(request.execute(id), HttpStatus.OK);
    }

    public static class PutColour {
        private final String colour;
        private final Racoons racoons;

        public PutColour(Racoons racoons, String colour) {
            this.racoons = racoons;
            this.colour = colour;
        }

        Json execute(UUID id) {
            Racoon racoon = racoons.byId(id);
            racoon.paint(colour);
            racoon.save();
            return new MutableJson().with("result", "Painted!");
        }
    }

    @JsonComponent
    public static class Deserializer extends JsonDeserializer<PutColour> {
        private final RacoonRepo repo;

        @Autowired
        public Deserializer(RacoonRepo repo) {
            this.repo = repo;
        }

        @Override
        public PutColour deserialize(
            JsonParser p, DeserializationContext ctxt
        ) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            return new PutColour(
                new Racoons(repo),
                node.get("colour").asText()
            );
        }
    }
}


