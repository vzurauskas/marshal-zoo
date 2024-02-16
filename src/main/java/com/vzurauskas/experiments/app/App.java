package com.vzurauskas.experiments.app;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vzurauskas.nereides.jackson.Json;
import com.vzurauskas.nereides.jackson.SmartJson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("com.vzurauskas.experiments")
@EnableJpaRepositories("com.vzurauskas.experiments")
@EntityScan("com.vzurauskas.experiments")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> builder.serializerByType(Json.class, new JsonSerializer<Json>() {
            @Override
            public void serialize(
                Json value, JsonGenerator gen, SerializerProvider serializers
            ) throws IOException {
                gen.writeRaw(new SmartJson(value).textual());
            }
        });
	}
}
