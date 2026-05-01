package ru.fozeton.spectrastats.backend.model.statistica;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonPropertyOrder({"ENTITY_KILLED_BY", "KILL_ENTITY", "properties"})
public class CombatStat {
    Map<@Size(max = 36) String, @Min(0) Integer> properties = new HashMap<>();

    @JsonProperty("ENTITY_KILLED_BY")
    Map<@Size(max = 36) String, @Min(0) Object> entityKilledBy;

    @JsonProperty("KILL_ENTITY")
    Map<@Size(max = 36) String, @Min(0) Object> killEntity;

    @JsonAnySetter
    public void add(String key, Object value) {
        if (value instanceof Integer val) properties.put(key, val);
    }
}
