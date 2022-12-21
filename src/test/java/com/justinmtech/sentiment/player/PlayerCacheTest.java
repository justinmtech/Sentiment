package com.justinmtech.sentiment.player;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.*;

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
        cache.addPlayer(UUID.randomUUID(), "longusername124958203948");
        assertTrue(cache.getPlayers().size() > 0);
    }

    public void testGetPlayer() {
        cache.addPlayer(uuid, "test");
        assertTrue(cache.getPlayer(uuid).isPresent());
        Optional<SPlayer> player = cache.getPlayer(UUID.randomUUID());
        assertTrue(player.isEmpty());
    }

    public void testAddPlayer() {
        int currentSize = cache.getPlayers().size();
        cache.addPlayer(UUID.randomUUID(), "test two");
        int newSize = cache.getPlayers().size();
        Assert.assertEquals(currentSize + 1, newSize);
    }

    public void testUpdatePlayer() {
        UUID uuidToUpdate = UUID.randomUUID();
        SPlayer sPlayer = new SPlayer("best", uuidToUpdate);

        sPlayer.setOptOut(true);
        Set<String> questionsAnswered = new HashSet<>();
        String answer1 = "yessssss";
        String answer2 = "yes, but i don't know about how i feel about that update. it was bad tbh.";
        questionsAnswered.add(answer1);
        questionsAnswered.add(answer2);
        sPlayer.setQuestionsAnswered(questionsAnswered);


        cache.addPlayer(sPlayer.getUuid(), sPlayer.getName());

        cache.updatePlayer(sPlayer);

        Optional<SPlayer> updatedPlayer = cache.updatePlayer(sPlayer);
        assertTrue(updatedPlayer.isPresent());
        assertTrue(sPlayer.isOptOut());
        assertTrue(sPlayer.getQuestionsAnswered().contains(answer1));
        assertTrue(sPlayer.getQuestionsAnswered().contains(answer2));
    }

    public void testPlayerInCache() {
        UUID uuid = UUID.randomUUID();
        cache.addPlayer(uuid, "test");
        assertTrue(cache.playerInCache(uuid));
    }

    public void testRemovePlayer() {
        UUID uuid = UUID.randomUUID();
        int prevSize = cache.getPlayers().size();
        cache.addPlayer(uuid, "bob");

        int newSize = cache.getPlayers().size();
        assertEquals(prevSize + 1, newSize);

        cache.removePlayer(uuid);
        assertEquals(newSize - 1, cache.getPlayers().size());
    }

    public void testRemoveAllPlayers() {
        cache.addPlayer(UUID.randomUUID(), "one");
        cache.addPlayer(UUID.randomUUID(), "two");
        cache.addPlayer(UUID.randomUUID(), "three");
        assertTrue(cache.getPlayers().size() > 0);
        cache.removeAllPlayers();
        assertTrue(cache.getPlayers().isEmpty());
    }
}