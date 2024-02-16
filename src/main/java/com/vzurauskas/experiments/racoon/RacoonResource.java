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

@RestController
@RequestMapping("/racoons")
public class RacoonResource {

    @PostMapping
    public ResponseEntity<Json> postRacoon(@RequestBody Resource resource) {
        return new ResponseEntity<>(resource.post(), HttpStatus.CREATED);
    }

    public static class Resource {
        private final String name;
        private final String colour;
        private final Repo repo;

        public Resource(Repo repo, String name, String colour) {
            this.name = name;
            this.colour = colour;
            this.repo = repo;
        }

        Json post() {
            repo.save(new PersistedRacoon(name, colour).dbEntry());
            return new MutableJson().with("result", "Created " + name + "!");
        }
    }

    @JsonComponent
    public static class Deserializer extends JsonDeserializer<Resource> {
        private final Repo repo;

        @Autowired
        public Deserializer(ApplicationContext applicationContext) {
            this.repo = applicationContext.getBean(Repo.class);
        }

        @Override
        public Resource deserialize(
            JsonParser p, DeserializationContext ctxt
        ) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            return new Resource(
                repo,
                node.get("name").asText(),
                node.get("colour").asText()
            );
        }
    }
}


