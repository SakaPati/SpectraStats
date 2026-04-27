package ru.fozeton.spectrastats.backend.model.statistica;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
public class CombatStat {
    Map<@Size(max = 36) String, @Min(0) Integer> properties = new HashMap<>();

    @JsonProperty("ENTITY_KILLED_BY")
    Map<@Size(max = 36) String, @Min(0) Integer> entityKilledBy;

    @JsonProperty("KILL_ENTITY")
    Map<@Size(max = 36) String, @Min(0) Integer> killEntity;

    @JsonAnySetter
    public void add(String key, Integer value) {
        properties.put(key, value);
    }
}
