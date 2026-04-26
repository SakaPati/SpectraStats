package ru.fozeton.spectraStats.statistic;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import ru.fozeton.spectraStats.statistic.stats.DistanceStat;
import ru.fozeton.spectraStats.statistic.stats.MessageStat;
import ru.fozeton.spectraStats.statistic.stats.VanillaStat;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class StatsRegistry {
    public final EventTracker<Message> messageTracker = new EventTracker<>();

    public final List<VanillaStat> COMBAT;
    public final List<IStat> INTERACT;
    public final List<VanillaStat> WORLD;
    public final List<IStat> SURVIVAL;

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

    public StatsRegistry() {
        this.COMBAT = List.of(
                VanillaStat.builder(Statistic.DEATHS, StatContext.COMBAT).build(),
                VanillaStat.builder(Statistic.ENTITY_KILLED_BY, StatContext.COMBAT).entityTypes(ENTITY).build(),
                VanillaStat.builder(Statistic.DAMAGE_DEALT, StatContext.COMBAT).build(),
                VanillaStat.builder(Statistic.DAMAGE_TAKEN, StatContext.COMBAT).build(),
                VanillaStat.builder(Statistic.KILL_ENTITY, StatContext.COMBAT).entityTypes(ENTITY).build()
        );

        this.INTERACT = List.of(
                VanillaStat.builder(Statistic.CHEST_OPENED, StatContext.INTERACT).build(),
                VanillaStat.builder(Statistic.ENDERCHEST_OPENED, StatContext.INTERACT).build(),
                VanillaStat.builder(Statistic.SHULKER_BOX_OPENED, StatContext.INTERACT).build(),
                VanillaStat.builder(Statistic.FURNACE_INTERACTION, StatContext.INTERACT).build(),
                VanillaStat.builder(Statistic.CRAFTING_TABLE_INTERACTION, StatContext.INTERACT).build(),
                VanillaStat.builder(Statistic.ITEM_ENCHANTED, StatContext.INTERACT).build(),
                VanillaStat.builder(Statistic.FISH_CAUGHT, StatContext.INTERACT).build(),
                VanillaStat.builder(Statistic.MINE_BLOCK, StatContext.INTERACT).materials(BLOCKS).build(),
                VanillaStat.builder(Statistic.CRAFT_ITEM, StatContext.INTERACT).materials(RECIPE_ITEMS).build(),
                VanillaStat.builder(Statistic.USE_ITEM, StatContext.INTERACT).materials(BLOCKS).build(),
                new MessageStat("CHAT_MESSAGE", messageTracker.getStorage())
        );

        this.WORLD = List.of(
                VanillaStat.builder(Statistic.ANIMALS_BRED, StatContext.WORLD).build(),
                VanillaStat.builder(Statistic.RAID_WIN, StatContext.WORLD).build(),
                VanillaStat.builder(Statistic.TRADED_WITH_VILLAGER, StatContext.WORLD).build(),
                VanillaStat.builder(Statistic.DROP, StatContext.WORLD).materials(MATERIALS).build(),
                VanillaStat.builder(Statistic.PICKUP, StatContext.WORLD).materials(MATERIALS).build()
        );

        this.SURVIVAL = List.of(
                VanillaStat.builder(Statistic.WALK_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.WALK_ON_WATER_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.FALL_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.CLIMB_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.FLY_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.WALK_UNDER_WATER_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.MINECART_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.BOAT_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.PIG_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.HORSE_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.SPRINT_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.CROUCH_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.AVIATE_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.SWIM_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.STRIDER_ONE_CM, StatContext.SURVIVAL).build(),
                VanillaStat.builder(Statistic.SLEEP_IN_BED, StatContext.SURVIVAL).build(),
                new DistanceStat(),
                VanillaStat.builder(Statistic.USE_ITEM, StatContext.SURVIVAL).materials(FOODS).build(),
                VanillaStat.builder(Statistic.BREAK_ITEM, StatContext.SURVIVAL).materials(EQUIPMENT).build()
        );
    }

    public record Message(String message, String timestamp) {
    }
}
