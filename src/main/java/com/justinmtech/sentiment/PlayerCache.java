package com.justinmtech.sentiment;

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

    public void addPlayer(@NotNull UUID uuid, @NotNull String name) {
        getPlayers().put(uuid, new SPlayer(name, uuid));
    }

    public Optional<SPlayer> updatePlayer(SPlayer player) {
        if (playerInCache(player.getUuid())) {
            SPlayer updatedPlayer = getPlayers().replace(player.getUuid(), player);
            if (updatedPlayer != null) {
                return Optional.of(updatedPlayer);
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
}
