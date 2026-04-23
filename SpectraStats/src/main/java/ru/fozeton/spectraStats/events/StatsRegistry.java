package ru.fozeton.spectraStats.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class StatsRegistry {
    public final List<Material> MATERIALS = Arrays.stream(Material.values())
            .filter(Material::isItem)
            .toList();

    public final List<Material> BLOCKS = Arrays.stream(Material.values())
            .filter(m -> m.isBlock() && !m.isAir())
            .toList();

    public final List<Material> ITEMS = StreamSupport
            .stream(Spliterators.spliteratorUnknownSize(Bukkit.recipeIterator(), 0), false)
            .filter(r -> r instanceof ShapedRecipe || r instanceof ShapelessRecipe)
            .map(r -> r.getResult().getType())
            .distinct()
            .toList();

    public final List<Material> FOODS = Arrays.stream(Material.values())
            .filter(m -> m.isEdible() && m.isItem())
            .toList();

    public final List<EntityType> ENTITY = Arrays.stream(EntityType.values())
            .filter(e -> e.getEntityClass() != null && LivingEntity.class.isAssignableFrom(e.getEntityClass()))
            .toList();

    public final List<Statistic> SURVIVAL = Arrays.stream(Statistic.values())
            .filter(s -> s.name().contains("CM") || s == Statistic.USE_ITEM || s == Statistic.SLEEP_IN_BED)
            .toList();

    public final List<Statistic> COMBAT = List.of(
            Statistic.DEATHS,
            Statistic.DAMAGE_DEALT,
            Statistic.DAMAGE_TAKEN,
            Statistic.KILL_ENTITY
    );

    public final List<Statistic> INTERACT = List.of(
            Statistic.CHEST_OPENED,
            Statistic.ENDERCHEST_OPENED,
            Statistic.SHULKER_BOX_OPENED,
            Statistic.FURNACE_INTERACTION,
            Statistic.CRAFTING_TABLE_INTERACTION,
            Statistic.ITEM_ENCHANTED,
            Statistic.FISH_CAUGHT,
            Statistic.MINE_BLOCK,
            Statistic.CRAFT_ITEM,
            Statistic.USE_ITEM
    );

    public final List<Statistic> WORLD = List.of(
            Statistic.ANIMALS_BRED,
            Statistic.RAID_WIN,
            Statistic.TRADED_WITH_VILLAGER,
            Statistic.DROP,
            Statistic.PICKUP
    );
}
