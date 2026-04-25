package ru.fozeton.spectraStats.statistic;

import org.bukkit.entity.Player;

import java.util.List;

public record CustomStat(String name, Object list) implements IStat {

    @Override
    public Object getStat(Player player, StatContext ctx) {
        return 
    }
}
