package com.justinmtech.sentiment.player;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PlayerCache {
    private final Map<UUID, SPlayer> players;

    public PlayerCache() {
        this.players = new HashMap<>();
    }

    private void loadPlayer(UUID uuid) {

    }

    private void savePlayer(UUID uuid) {

    }

    public Map<UUID, SPlayer> getPlayers() {
        return players;
    }

    public Optional<SPlayer> getPlayer(@NotNull UUID uuid) {
        SPlayer sPlayer = getPlayers().get(uuid);
        if (sPlayer == null) return Optional.empty();
        return Optional.of(getPlayers().get(uuid));
    }

    public void addPlayer(@NotNull UUID uuid, @NotNull String name) {
        getPlayers().put(uuid, new SPlayer(name, uuid));
    }

    public Optional<SPlayer> updatePlayer(SPlayer player) {
        if (playerInCache(player.getUuid())) {
            SPlayer updatedPlayer;
            try {
                updatedPlayer = getPlayers().replace(player.getUuid(), player);
                if (updatedPlayer != null) {
                    return Optional.of(updatedPlayer);
                }
            } catch (Exception e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean playerInCache(@NotNull UUID uuid) {
        return getPlayers().containsKey(uuid);
    }

    public SPlayer removePlayer(UUID uuid) {
        return getPlayers().remove(uuid);
    }

    public void removeAllPlayers() {
        getPlayers().clear();
    }
}
