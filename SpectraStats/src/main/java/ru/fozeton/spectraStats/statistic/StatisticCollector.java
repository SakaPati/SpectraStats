package ru.fozeton.spectraStats.statistic;

import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import ru.fozeton.spectraStats.Messages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

public class StatisticCollector {
    private final Map<StatKey, StatHandler> handlers;

    public StatisticCollector(StatsRegistry registry) {
        this.handlers = Map.of(
                new StatKey(Statistic.DROP, StatContext.WORLD), (p, s) -> serializeStats(registry.MATERIALS, e -> p.getStatistic(s, e), "DROP_ITEMS"),
                new StatKey(Statistic.PICKUP, StatContext.WORLD), (p, s) -> serializeStats(registry.MATERIALS, e -> p.getStatistic(s, e), "PICKUP_ITEMS"),
                new StatKey(Statistic.MINE_BLOCK, StatContext.INTERACT), (p, s) -> serializeStats(registry.BLOCKS, e -> p.getStatistic(s, e), "BREAK_BLOCKS"),
                new StatKey(Statistic.CRAFT_ITEM, StatContext.INTERACT), (p, s) -> serializeStats(registry.RECIPE_ITEMS, e -> p.getStatistic(s, e), "CRAFT_ITEMS"),
                new StatKey(Statistic.USE_ITEM, StatContext.INTERACT), (p, s) -> serializeStats(registry.BLOCKS, e -> p.getStatistic(s, e), "PLACED_BLOCKS"),
                new StatKey(Statistic.USE_ITEM, StatContext.SURVIVAL), (p, s) -> serializeStats(registry.FOODS, e -> p.getStatistic(s, e), "EATEN"),
                new StatKey(Statistic.BREAK_ITEM, StatContext.SURVIVAL), (p, s) -> serializeStats(registry.EQUIPMENT, e -> p.getStatistic(s, e), "BREAK_ITEMS"),
                new StatKey(Statistic.KILL_ENTITY, StatContext.COMBAT), (p, s) -> serializeStats(registry.ENTITY, e -> e == EntityType.PLAYER ? p.getStatistic(Statistic.PLAYER_KILLS) : p.getStatistic(s, e), "KILLS"),
                new StatKey(Statistic.ENTITY_KILLED_BY, StatContext.COMBAT), (p, s) -> serializeStats(registry.ENTITY, e -> p.getStatistic(s, e), "KILLED")
        );
    }

    public Map<String, Object> collect(Player player, List<? extends IStat<?>> statistics, StatContext ctx) {
        Map<String, Object> statMap = new LinkedHashMap<>();
        int totalSum = 0;
        for (IStat<?> s : statistics) {
            if (s.isSystem()) {
                StatKey key = new StatKey((Statistic) s.getStat(), ctx);
                if (handlers.containsKey(key)) {
                    statMap.put(s.name(), handlers.get(key).provider(player, (Statistic) s.getStat()));
                    continue;
                }

                int value = player.getStatistic((Statistic) s.getStat());
                if (value == 0) continue;
                if (s.name().contains("DAMAGE")) statMap.put(s.name(), value / 10);
                else if (s.name().contains("CM")) {
                    value /= 100;
                    totalSum += value;
                    statMap.put("TOTAL_DISTANCE_TRAVELED", totalSum);
                    statMap.put(s.name(), value);
                } else statMap.put(s.name(), value);
            } else if (s.getType() == Messages.Message.class) {
                    List<Messages.Message> messages = ((List<Messages.Message>) s.getStat()).stream()
                            .filter(m -> m.player().equals(player.getName()))
                            .toList();
                    statMap.put(s.name(), messages);
            }
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
            StatContext context
    ) {
    }
}
