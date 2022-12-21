package com.justinmtech.sentiment.player;

import com.justinmtech.sentiment.questions.Question;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SPlayer {
    private final String name;
    private final UUID uuid;
    private Set<String> questionsAnswered;
    private boolean optOut;

    public SPlayer(Player player) {
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.questionsAnswered = new HashSet<>();
        this.optOut = false;
    }

    public SPlayer(@NotNull String name, @NotNull UUID uuid, Set<String> questionsAnswered, boolean optOut) {
        this.name = name;
        this.uuid = uuid;
        this.questionsAnswered = questionsAnswered;
        this.optOut = optOut;
    }

    public SPlayer(@NotNull String name, @NotNull UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        this.questionsAnswered = new HashSet<>();
        this.optOut = false;
    }

    public SPlayer(@NotNull String name, @NotNull UUID uuid, boolean optOut) {
        this.name = name;
        this.uuid = uuid;
        this.questionsAnswered = new HashSet<>();
        this.optOut = false;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Set<String> getQuestionsAnswered() {
        return questionsAnswered;
    }

    public void setQuestionsAnswered(@NotNull Set<String> questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }

    public void addQuestionAnswered(@NotNull Question question) {
        this.questionsAnswered.add(question.getContent());
    }

    public boolean hasAnsweredQuestion(@NotNull Question question) {
        return getQuestionsAnswered().contains(question.getContent());
    }

    public boolean isOptOut() {
        return optOut;
    }

    public void setOptOut(boolean optOut) {
        this.optOut = optOut;
    }
}
