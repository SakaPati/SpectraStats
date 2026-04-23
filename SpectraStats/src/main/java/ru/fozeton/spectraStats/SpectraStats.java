package ru.fozeton.spectraStats;

import org.bukkit.plugin.java.JavaPlugin;
import ru.fozeton.spectraStats.events.PlayerConnection;
import ru.fozeton.spectraStats.events.PlayerStatistic;
import ru.fozeton.spectraStats.events.StatisticCollector;
import ru.fozeton.spectraStats.events.StatsRegistry;

import java.util.logging.Logger;

public final class SpectraStats extends JavaPlugin {

    @Override
    public void onEnable() {
        Logger logger = Logger.getLogger("Minecraft");
        HttpSender httpSender = new HttpSender(logger);
        StatsRegistry registry = new StatsRegistry();
        StatisticCollector collector = new StatisticCollector(registry);

        new PlayerStatistic(this, logger, httpSender, registry, collector).statistics();
        getServer().getPluginManager().registerEvents(new PlayerConnection(httpSender), this);
    }

    @Override
    public void onDisable() {
    }
}
