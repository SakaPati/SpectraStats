package ru.fozeton.spectraStats.events;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import ru.fozeton.spectraStats.HttpSender;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public class PlayerStatistic implements Listener {
    private final JavaPlugin plugin;
    private final Logger logger;
    private final HttpSender sender;
    private final Gson gson = new Gson();

    private final List<Material> blocks = Arrays.stream(Material.values())
            .filter(m -> m.isBlock() && !m.isAir())
            .toList();

    private final List<Material> craftingTableItem = StreamSupport
            .stream(Spliterators.spliteratorUnknownSize(Bukkit.recipeIterator(), 0), false)
            .filter(r -> r instanceof ShapedRecipe || r instanceof ShapelessRecipe)
            .map(r -> r.getResult().getType())
            .distinct()
            .toList();

    private final List<Material> foods = Arrays.stream(Material.values())
            .filter(m -> m.isEdible() && m.isItem())
            .toList();

    private final List<Statistic> survivalStats = Arrays.stream(Statistic.values())
            .filter(s -> s.name().contains("CM"))
            .collect(Collectors.toList());

    private final List<Statistic> combatStats = List.of(
            Statistic.DEATHS,
            Statistic.PLAYER_KILLS,
            Statistic.MOB_KILLS,
            Statistic.DAMAGE_DEALT,
            Statistic.DAMAGE_TAKEN
    );

    private final List<Statistic> interactStats = List.of(
            Statistic.ITEM_ENCHANTED,
            Statistic.FISH_CAUGHT,
            Statistic.MINE_BLOCK,
            Statistic.CRAFT_ITEM,
            Statistic.USE_ITEM
    );

    public void statistics() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> Bukkit.getOnlinePlayers()
                .forEach(player -> {
                    Map<String, Object> statMap = new LinkedHashMap<>();
                    statMap.put("player", player.getName());
                    statMap.put("survival", survival(player));
//                    statMap.put("combat", combat(player));
//                    statMap.put("interaction", interaction(player));
                    String json = gson.toJson(statMap);
                    System.out.println(json);

//                    sender.postDate("/player/statistic/", json);
//                    logger.info("Статистика дистанции отправлена");
                }), 0, 15 * 20);
    }

    private String survival(Player player) {
        survivalStats.addLast(Statistic.USE_ITEM);
        Map<String, Object> statMap = new LinkedHashMap<>();
        int totalDist = 0;
        for (Statistic statistic : survivalStats) {
            if (statistic == Statistic.USE_ITEM)
                statMap.put(statistic.name(), serializeMaterialStats(player, statistic, foods, "TOTAL_EATEN"));
            else {
                int stat = player.getStatistic(statistic) / 100;
                statMap.put(statistic.name(), stat);
                totalDist += stat;
            }
        }
        statMap.put("TOTAL_BLOCKS", totalDist);
        return gson.toJson(statMap);
    }

    private String combat(Player player) {
        Map<String, Object> statMap = new LinkedHashMap<>();
        int totalKills = 0;
        for (Statistic statistic : combatStats) {
            int stat = player.getStatistic(statistic);
            if (statistic == Statistic.DAMAGE_DEALT || statistic == Statistic.DAMAGE_TAKEN) stat /= 10;
            if (statistic == Statistic.PLAYER_KILLS || statistic == Statistic.MOB_KILLS) totalKills += stat;
            statMap.put(statistic.name(), stat);
        }
        statMap.put("TOTAL_KILLS", totalKills);
        return gson.toJson(statMap);
    }

    private String interaction(Player player) {
        Map<String, Object> statMap = new LinkedHashMap<>();
        for (Statistic statistic : interactStats) {
            switch (statistic) {
                case MINE_BLOCK ->
                        statMap.put(statistic.name(), serializeMaterialStats(player, statistic, blocks, "TOTAL_BREAK_BLOCKS"));
                case CRAFT_ITEM ->
                        statMap.put(statistic.name(), serializeMaterialStats(player, statistic, craftingTableItem, "TOTAL_CRAFT_ITEMS"));
                case USE_ITEM ->
                        statMap.put(statistic.name(), serializeMaterialStats(player, statistic, blocks, "TOTAL_PLACED_BLOCKS"));
                default -> {
                    int stat = player.getStatistic(statistic);
                    statMap.put(statistic.name(), stat);
                }
            }

        }
        return gson.toJson(statMap);
    }

    private String serializeMaterialStats(Player player, Statistic statistic, List<Material> materials, String totalStatName) {
        Map<String, Integer> mapStat = new HashMap<>();
        int totalStat = 0;
        for (Material m : materials) {
            int stat = player.getStatistic(statistic, m);
            if (stat != 0) {
                mapStat.put(m.toString(), stat);
                totalStat += stat;
            }
        }
        mapStat.put(totalStatName, totalStat);
        return gson.toJson(mapStat);
    }
}
