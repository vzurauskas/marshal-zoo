package com.vzurauskas.experiments.squirrel;

import com.vzurauskas.experiments.Squirrel;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.MutableJson;
import com.vzurauskas.nereides.jackson.SmartJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/squirrels")
public class SquirrelNutEndpoint {
    private final Squirrels squirrels;

    public SquirrelNutEndpoint(SquirrelRepo repo) {
        squirrels = new Squirrels(repo);
    }

    @PutMapping("{id}/nut")
    public ResponseEntity<Json> put(
        @PathVariable UUID id, @RequestBody Json request
    ) {
        Squirrel squirrel = squirrels.byId(id);
        String nut = new SmartJson(request).leaf("nut");
        squirrel.takeNut(nut);
        squirrel.save();
        return new ResponseEntity<>(
            new MutableJson().with("result", "Got nut: " + nut),
            HttpStatus.OK
        );
    }
}


