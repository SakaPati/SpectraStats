package ru.fozeton.spectraStats.statistic;

import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StatisticCollector {
    public Map<String, Object> collect(Player player, List<? extends IStat> statistics) {
        Map<String, Object> statMap = new LinkedHashMap<>();
        for (IStat s : statistics) {
            Object stat = s.getStat(player);

            if (stat instanceof Integer value) statMap.put(s.name(), value);
            else statMap.put(s.name(), stat);
        }
        return statMap;
    }
}
