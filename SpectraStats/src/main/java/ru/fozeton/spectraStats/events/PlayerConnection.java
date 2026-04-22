package ru.fozeton.spectraStats.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.fozeton.spectraStats.HttpSender;

public record PlayerConnection(HttpSender sender) implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        sender.postVoid("/player/connect/" + event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        sender.postVoid("/player/disconnect/" + event.getPlayer().getName());
    }
}
