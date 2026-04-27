package ru.fozeton.spectraStats.statistic;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.fozeton.spectraStats.HttpSender;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class PlayerStatistic implements Listener {
    private final JavaPlugin plugin;
    private final Logger logger;
    private final HttpSender sender;
    private final StatsRegistry registry;
    private final StatisticCollector collector;
    private final Gson gson = new Gson();
    private final Map<String, List<IStat>> CATEGORIES;

    public PlayerStatistic(JavaPlugin plugin, Logger logger, HttpSender sender, StatsRegistry registry, StatisticCollector collector) {
        this.plugin = plugin;
        this.logger = logger;
        this.sender = sender;
        this.registry = registry;
        this.collector = collector;

        this.CATEGORIES = Map.of(
                "SURVIVAL", registry.SURVIVAL,
                "COMBAT", registry.COMBAT,
                "INTERACT", registry.INTERACT,
                "WORLD", registry.WORLD
        );
    }

    public void statistics() {
        Bukkit.getAsyncScheduler().runAtFixedRate(plugin, (scheduledTask) -> {
            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

            for (Player player : players) {
                Map<String, Object> statMap = new LinkedHashMap<>();
                statMap.put("player", player.getName());

                for (Map.Entry<String, List<IStat>> entry : CATEGORIES.entrySet()) {
                    statMap.put(entry.getKey().toLowerCase(), collector.collect(player, entry.getValue()));
                }

                String json = gson.toJson(statMap);
                logger.info(json);

//                    sender.postDate("/player/statistic/", json);
            }
            registry.getMessageTracker().clear();
        }, 0, 15, TimeUnit.SECONDS);
    }
}