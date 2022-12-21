package com.justinmtech.sentiment.file;

import com.justinmtech.sentiment.player.SPlayer;
import junit.framework.TestCase;

import java.util.UUID;

public class PlayerFileManagerTest extends TestCase {
    private static PlayerFileManager playerFileManager;

    public void testSetPlayer() {
        playerFileManager = new PlayerFileManager();
        SPlayer sPlayer = new SPlayer("Name", UUID.randomUUID());
        sPlayer.setOptOut(false);
        playerFileManager.setPlayer(sPlayer);
    }

    public void testGetPlayer() {
    }

    public void testRemovePlayer() {
    }
}