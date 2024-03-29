package com.vzurauskas.experiments;

import com.vzurauskas.nereides.jackson.Json;

import java.util.UUID;

public interface Racoon {
    UUID id();
    void paint(String colour);
    Json json();
    void save();
}
