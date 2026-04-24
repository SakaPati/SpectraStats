package ru.fozeton.spectraStats.statistic;

import org.bukkit.Statistic;

public record VanillaStat(Statistic statistic) implements IStat<Statistic> {
    @Override
    public boolean isSystem() {
        return true;
    }

    @Override
    public String name() {
        return statistic.name();
    }

    @Override
    public Statistic getStat() {
        return statistic;
    }

    @Override
    public Class<Statistic> getType() {
        return Statistic.class;
    }
}
