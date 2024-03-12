package com.vzurauskas.experiments.squirrel;

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
@RequestMapping("/squirrels")
public class SquirrelsEndpoint {
    private final Squirrels squirrels;

    public SquirrelsEndpoint(SquirrelRepo repo) {
        squirrels = new Squirrels(repo);
    }

    @PostMapping
    public ResponseEntity<Json> post(@RequestBody Json request) {
        SmartJson json = new SmartJson(request);
        squirrels.create(
            UUID.randomUUID(), json.leaf("name"), json.leaf("nut")
        ).save();
        return new ResponseEntity<>(
            new MutableJson().with("result", "Created " + json.leaf("name") + "!"),
            HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ArrayNode> get() {
        ArrayNode node = new ObjectMapper().createArrayNode();
        squirrels.all().forEach(
            squirrel -> node.add(new SmartJson(squirrel.json()).objectNode())
        );
        return new ResponseEntity<>(node, HttpStatus.OK);
    }
}


