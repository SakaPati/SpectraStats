package ru.fozeton.spectraStats.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.fozeton.spectraStats.Messages;

import java.time.LocalDateTime;

public record PlayerSendMessage(Messages messages) implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        messages.addMessage(new Messages.Message(
                event.getPlayer().getName(),
                PlainTextComponentSerializer.plainText().serialize(event.message()),
                LocalDateTime.now().toString()
        ));
    }
}
