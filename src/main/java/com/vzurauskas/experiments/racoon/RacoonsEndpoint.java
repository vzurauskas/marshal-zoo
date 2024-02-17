package com.vzurauskas.experiments.racoon;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/racoons")
public class RacoonsEndpoint {

    @PostMapping
    public ResponseEntity<Json> post(@RequestBody RacoonsResource resource) {
        return new ResponseEntity<>(resource.post(), HttpStatus.CREATED);
    }

    public static class RacoonsResource {
        private final Racoons racoons;
        private final String name;
        private final String colour;

        public RacoonsResource(Racoons racoons, String name, String colour) {
            this.name = name;
            this.colour = colour;
            this.racoons = racoons;
        }

        Json post() {
            racoons.create(UUID.randomUUID(), name, colour).save();
            return new MutableJson().with("result", "Created " + name + "!");
        }
    }

    @JsonComponent
    public static class Deserializer extends JsonDeserializer<RacoonsResource> {
        private final RacoonRepo repo;

        @Autowired
        public Deserializer(ApplicationContext applicationContext) {
            this.repo = applicationContext.getBean(RacoonRepo.class);
        }

        @Override
        public RacoonsResource deserialize(
            JsonParser p, DeserializationContext ctxt
        ) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            return new RacoonsResource(
                new Racoons(repo),
                node.get("name").asText(),
                node.get("colour").asText()
            );
        }
    }
}


