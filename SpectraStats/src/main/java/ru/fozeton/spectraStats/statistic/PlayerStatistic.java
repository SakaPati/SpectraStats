package ru.fozeton.spectraStats.statistic;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class PlayerStatistic implements Listener {
    private final JavaPlugin plugin;
    private final Logger logger;
    private final HttpSender sender;
    private final StatsRegistry registry;
    private final StatisticCollector collector;
    private final Gson gson = new Gson();

    public void statistics() {
        Bukkit.getAsyncScheduler().runAtFixedRate(plugin, (scheduledTask) -> {
            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

            for (Player player : players) {
                Map<String, Object> statMap = new LinkedHashMap<>();
                statMap.put("player", player.getName());
                statMap.put("survival", survival(player));
                statMap.put("combat", combat(player));
                statMap.put("interaction", interaction(player));
                statMap.put("world", world(player));
                String json = gson.toJson(statMap);
                logger.info(json);

//                    sender.postDate("/player/statistic/", json);
            }
            registry.messageTracker.clear();
        }, 0, 15, TimeUnit.SECONDS);
    }

    private Map<String, Object> survival(Player player) {
        return collector.collect(player, registry.SURVIVAL);
    }

    private Map<String, Object> combat(Player player) {
        return collector.collect(player, registry.COMBAT);
    }

    private Map<String, Object> interaction(Player player) {
        return collector.collect(player, registry.INTERACT);
    }

    private Map<String, Object> world(Player player) {
        return collector.collect(player, registry.WORLD);
    }
}
