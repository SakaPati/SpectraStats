package ru.fozeton.spectraStats.events;

import lombok.RequiredArgsConstructor;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

@RequiredArgsConstructor
public class StatisticCollector {
    private final Map<StatKey, StatHandler> handlers;

    public StatisticCollector(StatsRegistry registry) {
        this.handlers = Map.of(
                new StatKey(Statistic.MINE_BLOCK, registry.MATERIALS), (p, s) -> serializeStats(registry.BLOCKS, e -> p.getStatistic(s, e), "BREAK_BLOCKS"),
                new StatKey(Statistic.CRAFT_ITEM, registry.MATERIALS), (p, s) -> serializeStats(registry.ITEMS, e -> p.getStatistic(s, e), "CRAFT_ITEMS"),
                new StatKey(Statistic.DROP, registry.MATERIALS), (p, s) -> serializeStats(registry.MATERIALS, e -> p.getStatistic(s, e), "DROP_ITEMS"),
                new StatKey(Statistic.PICKUP, registry.MATERIALS), (p, s) -> serializeStats(registry.MATERIALS, e -> p.getStatistic(s, e), "PICKUP_ITEMS"),
                new StatKey(Statistic.USE_ITEM, registry.MATERIALS), (p, s) -> serializeStats(registry.BLOCKS, e -> p.getStatistic(s, e), "PLACED_BLOCKS"),
                new StatKey(Statistic.KILL_ENTITY, registry.ENTITY), (p, s) -> serializeStats(registry.ENTITY, e ->
                        e == EntityType.PLAYER ? p.getStatistic(Statistic.PLAYER_KILLS) : p.getStatistic(s, e), "KILLS"),
                new StatKey(Statistic.USE_ITEM, registry.FOODS), (p, s) -> serializeStats(registry.FOODS, e -> p.getStatistic(s, e), "EATEN")
        );
    }

    public Map<String, Object> collect(Player player, List<Statistic> statistics, List<?> ctx) {
        Map<String, Object> statMap = new LinkedHashMap<>();
        int totalSum = 0;
        for (Statistic s : statistics) {
            StatKey key = new StatKey(s, ctx);
            if (handlers.containsKey(key)) {
                statMap.put(s.name(), handlers.get(key).provider(player, s));
                continue;
            }

            int value = player.getStatistic(s);
            if (value == 0) continue;
            if (s.name().contains("DAMAGE")) statMap.put(s.name(), value / 10);
            else if (s.name().contains("CM")) {
                value /= 100;
                totalSum += value;
                statMap.put("TOTAL_DISTANCE_TRAVELED", totalSum);
                statMap.put(s.name(), value);
            } else statMap.put(s.name(), value);
        }
        return statMap;
    }

    private <T> Map<String, Integer> serializeStats(List<T> items, ToIntFunction<T> stats, String totalStatName) {
        Map<String, Integer> mapStat = new LinkedHashMap<>();
        int totalStat = 0;
        for (T item : items) {
            int stat = stats.applyAsInt(item);
            if (stat != 0) {
                mapStat.put(item.toString(), stat);
                totalStat += stat;
            }
        }
        mapStat.put("TOTAL_" + totalStatName, totalStat);
        return mapStat;
    }

    @FunctionalInterface
    public interface StatHandler {
        Object provider(Player player, Statistic statistic);
    }

    record StatKey(
            Statistic stat,
            List<?> category
    ) {
    }
}
