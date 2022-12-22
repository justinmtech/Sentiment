package com.justinmtech.sentiment.file;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.justinmtech.sentiment.Sentiment;
import com.justinmtech.sentiment.player.SPlayer;
import junit.framework.TestCase;

import java.util.Optional;
import java.util.UUID;

public class PlayerFileManagerTest extends TestCase {
    private ServerMock server;
    private Sentiment plugin;

    public void setUp() throws Exception {
        super.setUp();
        server = MockBukkit.mock();
        plugin = MockBukkit.load(Sentiment.class);
    }

    public void tearDown() {
        server = null;
        plugin = null;
    }

    public void testSetPlayer() {
        SPlayer sPlayer = new SPlayer("name", UUID.randomUUID());
        plugin.getPlayerFileManager().setPlayer(sPlayer);
        Optional<SPlayer> player = plugin.getPlayerFileManager().getPlayer(sPlayer.getUuid());
        assertTrue(player.isPresent());
    }

    public void testGetPlayer() {
        UUID uuid = UUID.randomUUID();
        SPlayer sPlayer = new SPlayer("bestusername290", uuid);
        plugin.getPlayerFileManager().setPlayer(sPlayer);
        Optional<SPlayer> emptyPlayer = plugin.getPlayerFileManager().getPlayer(UUID.randomUUID());
        assertTrue(emptyPlayer.isEmpty());
        Optional<SPlayer> player = plugin.getPlayerFileManager().getPlayer(uuid);
        assertTrue(player.isPresent());
    }

    public void testRemovePlayer() {
        UUID uuid = UUID.randomUUID();
        SPlayer sPlayer = new SPlayer("thegoatoKay", uuid);
        plugin.getPlayerFileManager().setPlayer(sPlayer);
        Optional<SPlayer> player = plugin.getPlayerFileManager().getPlayer(uuid);
        assertTrue(player.isPresent());
        plugin.getPlayerFileManager().removePlayer(uuid);
        Optional<SPlayer> removedPlayer = plugin.getPlayerFileManager().getPlayer(uuid);
        assertTrue(removedPlayer.isEmpty());
    }
}