package ru.fozeton.spectraStats.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.fozeton.spectraStats.statistic.StatsRegistry;

import java.time.LocalDateTime;

public record PlayerSendMessage(StatsRegistry registry) implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        registry.messageTracker.add(event.getPlayer().getName(), new StatsRegistry.Message(
                PlainTextComponentSerializer.plainText().serialize(event.message()),
                LocalDateTime.now().toString()));
    }
}
