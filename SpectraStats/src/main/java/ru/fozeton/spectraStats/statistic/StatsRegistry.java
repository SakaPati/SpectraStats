package ru.fozeton.spectraStats.statistic;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import ru.fozeton.spectraStats.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class StatsRegistry {
    public final List<VanillaStat> COMBAT;
    public final List<IStat<?>> INTERACT;
    public final List<VanillaStat> WORLD;
    public final List<VanillaStat> SURVIVAL;

    public final List<Material> MATERIALS = Arrays.stream(Material.values())
            .filter(Material::isItem)
            .toList();

    public final List<Material> BLOCKS = Arrays.stream(Material.values())
            .filter(m -> m.isBlock() && !m.isAir())
            .toList();

    public final List<Material> RECIPE_ITEMS = StreamSupport
            .stream(Spliterators.spliteratorUnknownSize(Bukkit.recipeIterator(), 0), false)
            .filter(r -> r instanceof ShapedRecipe || r instanceof ShapelessRecipe)
            .map(r -> r.getResult().getType())
            .distinct()
            .toList();

    public final List<Material> EQUIPMENT = Arrays.stream(Material.values())
            .filter(m -> m.getMaxDurability() > 0)
            .toList();

    public final List<Material> FOODS = Arrays.stream(Material.values())
            .filter(m -> m.isEdible() && m.isItem())
            .toList();

    public final List<EntityType> ENTITY = Arrays.stream(EntityType.values())
            .filter(e -> e.getEntityClass() != null && LivingEntity.class.isAssignableFrom(e.getEntityClass()))
            .toList();

    public StatsRegistry(Messages messages) {
        this.COMBAT = List.of(
                new VanillaStat(Statistic.DEATHS),
                new VanillaStat(Statistic.ENTITY_KILLED_BY),
                new VanillaStat(Statistic.DAMAGE_DEALT),
                new VanillaStat(Statistic.DAMAGE_TAKEN),
                new VanillaStat(Statistic.KILL_ENTITY)
        );

        this.INTERACT = List.of(
                new VanillaStat(Statistic.CHEST_OPENED),
                new VanillaStat(Statistic.ENDERCHEST_OPENED),
                new VanillaStat(Statistic.SHULKER_BOX_OPENED),
                new VanillaStat(Statistic.FURNACE_INTERACTION),
                new VanillaStat(Statistic.CRAFTING_TABLE_INTERACTION),
                new VanillaStat(Statistic.ITEM_ENCHANTED),
                new VanillaStat(Statistic.FISH_CAUGHT),
                new VanillaStat(Statistic.MINE_BLOCK),
                new VanillaStat(Statistic.CRAFT_ITEM),
                new VanillaStat(Statistic.USE_ITEM),
                new CustomStat<>("CHAT_MESSAGE", messages.getMessages(), Messages.Message.class)
        );

        this.WORLD = List.of(
                new VanillaStat(Statistic.ANIMALS_BRED),
                new VanillaStat(Statistic.RAID_WIN),
                new VanillaStat(Statistic.TRADED_WITH_VILLAGER),
                new VanillaStat(Statistic.DROP),
                new VanillaStat(Statistic.PICKUP)
        );

        Set<Statistic> SURVIVAL_WHITELIST = Set.of(
                Statistic.USE_ITEM,
                Statistic.SLEEP_IN_BED,
                Statistic.BREAK_ITEM
        );

        this.SURVIVAL = Arrays.stream(Statistic.values())
                .filter(s -> s.name().contains("CM") || SURVIVAL_WHITELIST.contains(s))
                .map(VanillaStat::new)
                .toList();
    }
}
