package com.justinmtech.sentiment;

import com.justinmtech.sentiment.file.PlayerFileManager;
import com.justinmtech.sentiment.file.ResponseFileManager;
import com.justinmtech.sentiment.player.PlayerCache;
import com.justinmtech.sentiment.questions.Asker;
import com.justinmtech.sentiment.questions.QuestionManager;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.logging.Level;

public final class Sentiment extends JavaPlugin {
    private QuestionManager questionManager;
    private PlayerFileManager playerFileManager;
    private ResponseFileManager responseFileManager;
    private Asker asker;
    private PlayerCache cache;

    public Sentiment() {
        super();
    }

    protected Sentiment(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        this.questionManager = new QuestionManager(getConfig());
        this.playerFileManager = new PlayerFileManager(this);
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
