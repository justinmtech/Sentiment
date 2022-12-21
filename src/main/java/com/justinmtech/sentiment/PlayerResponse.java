package com.justinmtech.sentiment;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerResponse {
    private SPlayer player;
    private Question question;
    private String response;
    private UUID responseId;

    public PlayerResponse(@NotNull Player player, @NotNull Question question, String response) {
        setPlayer(new SPlayer(player));
        setQuestion(question);
        setResponseId();
        setResponse(response);
    }

    public PlayerResponse(@NotNull SPlayer player, @NotNull Question question, String response) {
        setPlayer(player);
        setQuestion(question);
        setResponseId();
        setResponse(response);
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(@NotNull String response) {
        this.response = response;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(@NotNull Question question) {
        this.question = question;
    }

    public UUID getResponseId() {
        return responseId;
    }

    public void setResponseId() {
        this.responseId = UUID.randomUUID();
    }

    public SPlayer getPlayer() {
        return player;
    }

    public void setPlayer(SPlayer player) {
        this.player = player;
    }
}
