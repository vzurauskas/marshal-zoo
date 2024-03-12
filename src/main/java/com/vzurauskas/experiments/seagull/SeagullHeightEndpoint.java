package com.vzurauskas.experiments.seagull;

import com.vzurauskas.experiments.Seagull;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/seagulls")
public class SeagullHeightEndpoint {
    private final Seagulls seagulls;

    public SeagullHeightEndpoint(SeagullRepo repo) {
        seagulls = new Seagulls(repo);
    }

    public record SeagullHeightResource(int height) { }

    @PutMapping("{id}/height")
    public ResponseEntity<Json> put(
        @PathVariable UUID id, @RequestBody SeagullHeightResource request
    ) {
        Seagull seagull = seagulls.byId(id);
        seagull.fly(request.height);
        seagull.save();
        return new ResponseEntity<>(
            new MutableJson().with("result", "Flying at " + request.height),
            HttpStatus.OK
        );
    }
}


