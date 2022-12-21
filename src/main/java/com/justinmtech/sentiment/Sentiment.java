package com.justinmtech.sentiment;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public final class Sentiment extends JavaPlugin {
    private QuestionManager questionManager;

    @Override
    public void onEnable() {
        setQuestionManager(new QuestionManager(getConfig()));
        getLogger().log(Level.INFO, "Plugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Plugin disabled.");
    }

    public void setQuestionManager(@NotNull QuestionManager questionManager) {
        this.questionManager = questionManager;
    }

    public QuestionManager getQuestionManager() {
        return questionManager;
    }
}
