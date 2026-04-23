package ru.fozeton.spectraStats.events;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.fozeton.spectraStats.HttpSender;

import java.util.LinkedHashMap;
import java.util.Map;
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
        Bukkit.getScheduler().runTaskTimer(plugin, () -> Bukkit.getOnlinePlayers()
                .forEach(player -> {
                    Map<String, Object> statMap = new LinkedHashMap<>();
                    statMap.put("player", player.getName());
                    statMap.put("survival", survival(player));
                    statMap.put("combat", combat(player));
                    statMap.put("interaction", interaction(player));
                    statMap.put("world", world(player));
                    String json = gson.toJson(statMap);
                    logger.info(json);

                    sender.postDate("/player/statistic/", json);
                    logger.info("Статистика отправлена");
                }), 0, 15 * 20);
    }

    private Map<String, Object> survival(Player player) {
        return collector.collect(player, registry.SURVIVAL, registry.FOODS);
    }

    private Map<String, Object> combat(Player player) {
        return collector.collect(player, registry.COMBAT, registry.ENTITY);
    }

    private Map<String, Object> interaction(Player player) {
        return collector.collect(player, registry.INTERACT, registry.MATERIALS);
    }

    private Map<String, Object> world(Player player) {
        return collector.collect(player, registry.WORLD, registry.MATERIALS);
    }
}
