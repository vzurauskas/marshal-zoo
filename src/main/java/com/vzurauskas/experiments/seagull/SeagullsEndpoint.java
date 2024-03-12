package com.vzurauskas.experiments.seagull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;
import com.vzurauskas.nereides.jackson.SmartJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/seagulls")
public class SeagullsEndpoint {
    private final Seagulls seagulls;

    public SeagullsEndpoint(SeagullRepo repo) {
        seagulls = new Seagulls(repo);
    }

    public record PostSeagulls(String name) { }

    @PostMapping
    public ResponseEntity<Json> post(@RequestBody PostSeagulls request) {
        seagulls.create(UUID.randomUUID(), request.name).save();
        return new ResponseEntity<>(
            new MutableJson().with("result", "Created " + request.name + "!"),
            HttpStatus.CREATED
        );
    }

    public record GetSeagulls() { }

    @GetMapping
    public ResponseEntity<ArrayNode> get(@RequestBody GetSeagulls request) {
        ArrayNode node = new ObjectMapper().createArrayNode();
        seagulls.all().forEach(
            seagull -> node.add(new SmartJson(seagull.json()).objectNode())
        );
        return new ResponseEntity<>(node, HttpStatus.OK);
    }
}


