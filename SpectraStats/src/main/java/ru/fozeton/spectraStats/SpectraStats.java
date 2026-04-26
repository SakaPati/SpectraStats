package ru.fozeton.spectraStats;

import org.bukkit.plugin.java.JavaPlugin;
import ru.fozeton.spectraStats.events.PlayerConnection;
import ru.fozeton.spectraStats.events.PlayerSendMessage;
import ru.fozeton.spectraStats.statistic.PlayerStatistic;
import ru.fozeton.spectraStats.statistic.StatisticCollector;
import ru.fozeton.spectraStats.statistic.StatsRegistry;

import java.util.logging.Logger;

public final class SpectraStats extends JavaPlugin {


    @Override
    public void onEnable() {
        Logger logger = Logger.getLogger("Minecraft");
        HttpSender httpSender = new HttpSender(logger);
        StatsRegistry registry = new StatsRegistry();
        StatisticCollector collector = new StatisticCollector();

        new PlayerStatistic(this, logger, httpSender, registry, collector).statistics();
        getServer().getPluginManager().registerEvents(new PlayerConnection(httpSender), this);
        getServer().getPluginManager().registerEvents(new PlayerSendMessage(registry), this);
    }

    @Override
    public void onDisable() {
    }
}
