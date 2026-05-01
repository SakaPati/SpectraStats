package ru.fozeton.spectrastats.backend.model.statistica;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonPropertyOrder({"EATEN", "BREAK_ITEM", "properties"})
public class SurvivalStat {
    Map<@Size(max = 36) String, @Min(0) Integer> properties = new HashMap<>();

    @JsonProperty("EATEN")
    @JsonAlias("USE_ITEM")
    Map<@Size(max = 36) String, @Min(0) Object> eaten;

    @JsonProperty("BREAK_ITEM")
    Map<@Size(max = 36) String, @Min(0) Object> breakItems;

    @JsonAnySetter
    public void add(String key, Object value) {
        if (value instanceof Integer val) properties.put(key, val);
    }
}