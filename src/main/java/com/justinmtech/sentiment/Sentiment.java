package com.justinmtech.sentiment;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Sentiment extends JavaPlugin {
    private QuestionManager questionManager;
    private PlayerFileManager playerFileManager;
    private ResponseFileManager responseFileManager;
    private Asker asker;
    private PlayerCache cache;

    @Override
    public void onEnable() {
        this.questionManager = new QuestionManager(getConfig());
        this.playerFileManager = new PlayerFileManager(getLogger());
        this.responseFileManager = new ResponseFileManager(this);
        this.asker = new Asker(this);
        this.cache = new PlayerCache();
        getLogger().log(Level.INFO, "Plugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Plugin disabled.");
    }

    public QuestionManager getQuestionManager() {
        return questionManager;
    }

    public PlayerFileManager getPlayerFileManager() {
        return playerFileManager;
    }

    public ResponseFileManager getResponseFileManager() {
        return responseFileManager;
    }

    public Asker getAsker() {
        return asker;
    }

    public PlayerCache getCache() {
        return cache;
    }
}
