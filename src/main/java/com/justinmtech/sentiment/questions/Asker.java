package com.justinmtech.sentiment.questions;

import com.justinmtech.sentiment.Sentiment;
import com.justinmtech.sentiment.player.PlayerCache;
import com.justinmtech.sentiment.player.SPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Asker {
    private final Sentiment plugin;
    private final PlayerCache cache;
    private final QuestionManager questionManager;
    private final List<Question> questions;

    public Asker(@NotNull Sentiment plugin) {
        this.plugin = plugin;
        this.questionManager = plugin.getQuestionManager();
        this.questions = plugin.getQuestionManager().getQuestions();
        this.cache = plugin.getCache();
        run();
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Optional<Question> question = getQuestionForPlayer(p.getUniqueId());
                    question.ifPresent(value -> askQuestion(p, value));
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

    private Optional<Question> getQuestionForPlayer(@NotNull UUID uuid) {
        Optional<SPlayer> sPlayer = getCache().getPlayer(uuid);
        if (sPlayer.isPresent()) {
            for (Question q : questions) {
                if (!sPlayer.get().hasAnsweredQuestion(q)) return Optional.of(q);
            }
        }
        return Optional.empty();
    }

    private QuestionManager getQuestionManager() {
        return questionManager;
    }

    private List<Question> getQuestions() {
        return questions;
    }

    public PlayerCache getCache() {
        return cache;
    }
}
