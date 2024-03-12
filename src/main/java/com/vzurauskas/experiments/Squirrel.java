package com.vzurauskas.experiments;

import com.vzurauskas.nereides.jackson.Json;

import java.util.UUID;

public interface Squirrel {
    UUID id();
    void takeNut(String nut);
    Json json();
    void save();
}
