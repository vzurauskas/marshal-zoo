package com.vzurauskas.experiments.racoon;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RacoonResourceTest {


    @Test
    void opensNewAccount() {
        Repo repo = new FakeRepo();
        RacoonResource res = new RacoonResource();
        assertEquals(
            HttpStatus.CREATED,
            res.postRacoon(
                new RacoonResource.Resource("Bandit", "Black")
            ).getStatusCode()
        );
        assertEquals(1, repo.count());
    }
}