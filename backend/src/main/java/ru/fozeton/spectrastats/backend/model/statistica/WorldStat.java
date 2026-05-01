package ru.fozeton.spectrastats.backend.model.statistica;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonPropertyOrder({"DROP", "PICKUP", "properties"})
public class WorldStat {
    Map<@Size(max = 36) String, @Min(0) Integer> properties = new HashMap<>();

    @JsonProperty("DROP")
    Map<@Size(max = 36) String, @Min(0) Object> drops;

    @JsonProperty("PICKUP")
    Map<@Size(max = 36) String, @Min(0) Object> pickups;

    @JsonAnySetter
    public void add(String key, Object value) {
        if (value instanceof Integer val) properties.put(key, val);
    }
}
