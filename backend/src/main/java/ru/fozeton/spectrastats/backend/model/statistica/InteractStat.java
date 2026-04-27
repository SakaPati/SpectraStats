package ru.fozeton.spectrastats.backend.model.statistica;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
public class InteractStat {
    Map<@Size(max = 36) String, @Min(0) Integer> properties = new HashMap<>();

    @JsonProperty("MINE_BLOCK")
    Map<@Size(max = 36) String, @Min(0) Integer> mineBlocks;

    @JsonProperty("CRAFT_ITEM")
    Map<@Size(max = 36) String, @Min(0) Integer> craftItems;

    @JsonProperty("USE_ITEM")
    Map<@Size(max = 36) String, @Min(0) Integer> placedBlocks;


    @JsonAnySetter
    public void add(String key, Integer value) {
        properties.put(key, value);
    }
}
