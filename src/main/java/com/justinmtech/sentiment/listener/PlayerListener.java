package com.justinmtech.sentiment.listener;

import com.justinmtech.sentiment.player.PlayerCache;
import com.justinmtech.sentiment.file.PlayerFileManager;
import com.justinmtech.sentiment.player.SPlayer;
import com.justinmtech.sentiment.Sentiment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;
import java.util.UUID;

public class PlayerListener implements Listener {
    private final PlayerCache cache;
    private final PlayerFileManager playerFileManager;

    public PlayerListener(Sentiment plugin) {
        this.cache = plugin.getCache();
        this.playerFileManager = plugin.getPlayerFileManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        Optional<SPlayer> player = getPlayerFileManager().getPlayer(uuid);
        player.ifPresent(sPlayer -> getCache().addPlayer(uuid, sPlayer.getName()));
    }

    public PlayerCache getCache() {
        return cache;
    }

    public PlayerFileManager getPlayerFileManager() {
        return playerFileManager;
    }
}
