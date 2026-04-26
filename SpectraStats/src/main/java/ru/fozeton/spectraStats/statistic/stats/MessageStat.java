package ru.fozeton.spectraStats.statistic.stats;

import org.bukkit.entity.Player;
import ru.fozeton.spectraStats.statistic.IStat;
import ru.fozeton.spectraStats.statistic.StatsRegistry;

import java.util.List;
import java.util.Map;

public record MessageStat(String name, Map<String, List<StatsRegistry.Message>> listMap) implements IStat {
    @Override
    public Object getStat(Player player) {
        return listMap.get(player.getName());
    }
}
