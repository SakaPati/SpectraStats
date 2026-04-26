package ru.fozeton.spectraStats.statistic;

import org.bukkit.entity.Player;

public interface IStat {
    String name();
    Object getStat(Player player);
}
