package ru.fozeton.spectraStats;

import org.bukkit.plugin.java.JavaPlugin;
import ru.fozeton.spectraStats.events.PlayerConnection;
import ru.fozeton.spectraStats.events.PlayerStatistic;

import java.util.logging.Logger;

public final class SpectraStats extends JavaPlugin {
    private final Logger LOGGER = Logger.getLogger("Minecraft");
    private final HttpSender httpSender = new HttpSender(LOGGER);

    @Override
    public void onEnable() {
        new PlayerStatistic(this, LOGGER, httpSender).statistics();
        getServer().getPluginManager().registerEvents(new PlayerConnection(httpSender), this);
    }

    @Override
    public void onDisable() {
    }
}
