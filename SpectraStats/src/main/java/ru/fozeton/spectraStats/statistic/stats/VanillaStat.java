package ru.fozeton.spectraStats.statistic.stats;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import ru.fozeton.spectraStats.statistic.IStat;
import ru.fozeton.spectraStats.statistic.StatContext;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

public record VanillaStat(
        Statistic statistic,
        List<Material> materials,
        List<EntityType> entityTypes,
        StatContext context) implements IStat {

    public static Builder builder(Statistic statistic, StatContext context) {
        return new Builder(statistic, context);
    }

    @Override
    public String name() {
        return statistic.name();
    }

    @Override
    public Object getStat(Player player) {
        int value;

        if (materials != null) {
            switch (statistic) {
                case USE_ITEM -> {
                    if (context == StatContext.SURVIVAL)
                        return serializeStats(materials, m -> player.getStatistic(statistic, m), "EATEN");
                    return serializeStats(materials, m -> player.getStatistic(statistic, m), "PLACED_BLOCKS");
                }
                case BREAK_ITEM -> {
                    return serializeStats(materials, m -> player.getStatistic(statistic, m), "BREAK_ITEMS");
                }
                case DROP -> {
                    return serializeStats(materials, m -> player.getStatistic(statistic, m), "DROP_ITEMS");
                }
                case PICKUP -> {
                    return serializeStats(materials, m -> player.getStatistic(statistic, m), "PICKUP_ITEMS");
                }
                case MINE_BLOCK -> {
                    return serializeStats(materials, m -> player.getStatistic(statistic, m), "BREAK_BLOCKS");
                }
                case CRAFT_ITEM -> {
                    return serializeStats(materials, m -> player.getStatistic(statistic, m), "CRAFT_ITEMS");
                }
                default -> {
                    return null;
                }
            }

        } else if (entityTypes != null) {
            if (statistic == Statistic.ENTITY_KILLED_BY)
                return serializeStats(entityTypes, e -> player.getStatistic(statistic, e), "KILLED");
            else
                return serializeStats(entityTypes, e -> e == EntityType.PLAYER ? player.getStatistic(Statistic.PLAYER_KILLS) : player.getStatistic(statistic, e), "KILLS");
        } else value = player.getStatistic(statistic);

        if (statistic.name().contains("DAMAGE")) value /= 10;
        if (statistic.name().contains("CM")) value /= 100;

        return value;
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

    public static class Builder {
        private final Statistic statistic;
        private final StatContext context;
        private List<Material> materials;
        private List<EntityType> entityTypes;

        public Builder(Statistic statistic, StatContext context) {
            this.statistic = statistic;
            this.context = context;
        }

        public Builder materials(List<Material> materials) {
            this.materials = materials;
            return this;
        }

        public Builder entityTypes(List<EntityType> entityTypes) {
            this.entityTypes = entityTypes;
            return this;
        }

        public VanillaStat build() {
            return new VanillaStat(statistic, materials, entityTypes, context);
        }
    }
}
