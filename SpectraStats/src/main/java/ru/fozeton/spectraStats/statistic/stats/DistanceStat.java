package ru.fozeton.spectraStats.statistic.stats;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import ru.fozeton.spectraStats.statistic.IStat;

public class DistanceStat implements IStat {
    private final Statistic[] statistics = {
            Statistic.WALK_ONE_CM,
            Statistic.WALK_ON_WATER_ONE_CM,
            Statistic.FALL_ONE_CM,
            Statistic.CLIMB_ONE_CM,
            Statistic.FLY_ONE_CM,
            Statistic.WALK_UNDER_WATER_ONE_CM,
            Statistic.MINECART_ONE_CM,
            Statistic.BOAT_ONE_CM,
            Statistic.PIG_ONE_CM,
            Statistic.HORSE_ONE_CM,
            Statistic.SPRINT_ONE_CM,
            Statistic.CROUCH_ONE_CM,
            Statistic.AVIATE_ONE_CM,
            Statistic.SWIM_ONE_CM,
            Statistic.STRIDER_ONE_CM
    };

    @Override
    public String name() {
        return "TOTAL_DISTANCE_TRAVELED";
    }

    @Override
    public Object getStat(Player player) {
        int totalSum = 0;

        for (Statistic s : statistics) totalSum += player.getStatistic(s);
        return totalSum / 100;
    }
}
