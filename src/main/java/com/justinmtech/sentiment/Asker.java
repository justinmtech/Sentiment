package com.justinmtech.sentiment;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class Asker {
    private final Sentiment plugin;
    private final QuestionManager questionManager;
    private final List<Question> questions;

    public Asker(@NotNull Sentiment plugin) {
        this.plugin = plugin;
        this.questionManager = plugin.getQuestionManager();
        this.questions = plugin.getQuestionManager().getQuestions();
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    //TODO
                }
            }
        }.runTaskTimerAsynchronously(getPlugin(), 0L, 600L);
    }

    private void askQuestion(@NotNull Player player, @NotNull Question question) {
        player.sendMessage("§e[" + plugin.getName() + "] §b" + question.getContent());
    }

    private Sentiment getPlugin() {
        return plugin;
    }

    //TODO
    private Optional<Question> getQuestionForPlayer(Player player) {
        for (Question q : questions) {
            if (!player.hasAnsweredQuestion(q)) return Optional.of(q);
        }
        return Optional.empty();
    }

    private QuestionManager getQuestionManager() {
        return questionManager;
    }

    private List<Question> getQuestions() {
        return questions;
    }
}
