package com.vzurauskas.experiments.racoon;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;
import com.vzurauskas.nereides.jackson.SmartJson;
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
public class RacoonsEndpoint {

    @PostMapping
    public ResponseEntity<Json> post(@RequestBody PostRacoons request) {
        return new ResponseEntity<>(request.execute(), HttpStatus.CREATED);
    }

    public static class PostRacoons {
        private final Racoons racoons;
        private final String name;
        private final String colour;

        public PostRacoons(Racoons racoons, String name, String colour) {
            this.name = name;
            this.colour = colour;
            this.racoons = racoons;
        }

        Json execute() {
            racoons.create(UUID.randomUUID(), name, colour).save();
            return new MutableJson().with("result", "Created " + name + "!");
        }
    }

    @JsonComponent
    public static class PostDeserializer extends JsonDeserializer<PostRacoons> {
        private final RacoonRepo repo;

        @Autowired
        public PostDeserializer(RacoonRepo repo) {
            this.repo = repo;
        }

        @Override
        public PostRacoons deserialize(
            JsonParser p, DeserializationContext ctxt
        ) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            return new PostRacoons(
                new Racoons(repo),
                node.get("name").asText(),
                node.get("colour").asText()
            );
        }
    }

    @GetMapping
    public ResponseEntity<ArrayNode> get(@RequestBody GetRacoons resource) {
        return new ResponseEntity<>(resource.execute(), HttpStatus.OK);
    }

    public static class GetRacoons {
        private final Racoons racoons;

        public GetRacoons(Racoons racoons) {
            this.racoons = racoons;
        }

        ArrayNode execute() {
            ArrayNode node = new ObjectMapper().createArrayNode();
            racoons.all().forEach(
                racoon -> node.add(new SmartJson(racoon.json()).objectNode())
            );
            return node;
        }
    }

    @JsonComponent
    public static class GetDeserializer extends JsonDeserializer<GetRacoons> {
        private final RacoonRepo repo;

        @Autowired
        public GetDeserializer(RacoonRepo repo) {
            this.repo = repo;
        }

        @Override
        public GetRacoons deserialize(JsonParser p, DeserializationContext ctxt) {
            return new GetRacoons(new Racoons(repo));
        }
    }
}


