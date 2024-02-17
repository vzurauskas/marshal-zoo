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
import org.springframework.context.ApplicationContext;
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
        @PathVariable UUID id, @RequestBody RacoonColourResource resource
    ) {
        return new ResponseEntity<>(resource.put(id), HttpStatus.OK);
    }

    public static class RacoonColourResource {
        private final String colour;
        private final Racoons racoons;

        public RacoonColourResource(Racoons racoons, String colour) {
            this.racoons = racoons;
            this.colour = colour;
        }

        Json put(UUID id) {
            Racoon racoon = racoons.byId(id);
            racoon.paint(colour);
            racoon.save();
            return new MutableJson().with("result", "Painted!");
        }
    }

    @JsonComponent
    public static class Deserializer extends JsonDeserializer<RacoonColourResource> {
        private final RacoonRepo repo;

        @Autowired
        public Deserializer(ApplicationContext applicationContext) {
            this.repo = applicationContext.getBean(RacoonRepo.class);
        }

        @Override
        public RacoonColourResource deserialize(
            JsonParser p, DeserializationContext ctxt
        ) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            return new RacoonColourResource(
                new Racoons(repo),
                node.get("colour").asText()
            );
        }
    }
}


