package ru.fozeton.spectrastats.backend.model.statistica;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
public class WorldStat {
    Map<@Size(max = 36) String, @Min(0) Integer> properties = new HashMap<>();

    @JsonProperty("DROP")
    Map<@Size(max = 36) String, @Min(0) Integer> drops;

    @JsonProperty("PICKUP")
    Map<@Size(max = 36) String, @Min(0) Integer> pickups;

    @JsonAnySetter
    public void add(String key, Integer value) {
        properties.put(key, value);
    }
}
