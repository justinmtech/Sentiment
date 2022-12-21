package com.justinmtech.sentiment;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SPlayer {
    private final String name;
    private final UUID uuid;
    private Set<String> questionsAnswered;

    public SPlayer(Player player) {
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.questionsAnswered = new HashSet<>();
    }

    public SPlayer(@NotNull String name, @NotNull UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        this.questionsAnswered = new HashSet<>();
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

    public void setQuestionsAnswered(Set<String> questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }

    public void addQuestionAnswered(Question question) {
        this.questionsAnswered.add(question.getContent());
    }
}
