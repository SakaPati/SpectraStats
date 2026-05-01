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
@JsonPropertyOrder({"MINE_BLOCK", "CRAFT_ITEM", "PLACED_BLOCKS", "properties"})
public class InteractStat {
    Map<@Size(max = 36) String, @Min(0) Integer> properties = new HashMap<>();

    @JsonProperty("MINE_BLOCK")
    Map<@Size(max = 36) String, @Min(0) Object> mineBlocks;

    @JsonProperty("CRAFT_ITEM")
    Map<@Size(max = 36) String, @Min(0) Object> craftItems;

    @JsonProperty("PLACED_BLOCKS")
    @JsonAlias("USE_ITEM")
    Map<@Size(max = 36) String, @Min(0) Object> placedBlocks;


    @JsonAnySetter
    public void add(String key, Object value) {
        if (value instanceof Integer val) properties.put(key, val);
    }
}
