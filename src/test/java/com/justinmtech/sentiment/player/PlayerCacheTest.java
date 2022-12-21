package com.justinmtech.sentiment.player;

import junit.framework.TestCase;

import java.util.Map;
import java.util.UUID;

public class PlayerCacheTest extends TestCase {
    private PlayerCache cache;
    private UUID uuid;

    public void setUp() throws Exception {
        super.setUp();
        cache = new PlayerCache();
        uuid = UUID.randomUUID();
    }

    public void tearDown() throws Exception {
        cache = null;
    }

    public void testGetPlayers() {
        Map<UUID, SPlayer> players = cache.getPlayers();
        assertTrue(players.isEmpty());
    }

    public void testGetPlayer() {
        cache.addPlayer(uuid, "test");
        assertTrue(cache.getPlayer(uuid).isPresent());
        assertTrue(cache.getPlayer(UUID.randomUUID()).isEmpty());
    }

    public void testAddPlayer() {
    }

    public void testUpdatePlayer() {
    }

    public void testPlayerInCache() {
    }

    public void testRemovePlayer() {
    }
}