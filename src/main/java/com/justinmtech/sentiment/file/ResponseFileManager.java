package com.justinmtech.sentiment.file;

import com.justinmtech.sentiment.*;
import com.justinmtech.sentiment.player.SPlayer;
import com.justinmtech.sentiment.questions.PlayerResponse;
import com.justinmtech.sentiment.questions.Question;
import com.justinmtech.sentiment.questions.QuestionManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResponseFileManager {
    private final Sentiment plugin;
    private final QuestionManager questionManager;
    private final Logger logger;
    private static final String FILE_NAME = "responses.yml";

    public ResponseFileManager(Sentiment plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.questionManager = plugin.getQuestionManager();
        createFileIfNotExist();
    }

    public boolean addResponseToFile(@NotNull PlayerResponse response, @NotNull Question question) {
        FileConfiguration responseFile = getResponseFile();
        if (responseFile != null) {
            if (getQuestionManager().questionExists(question.getContent())) {
                ConfigurationSection section = responseFile.getConfigurationSection(question.getContent());
                Map<String, String> map = new HashMap<>();

                map.put("player-name", response.getPlayer().getName());
                map.put("player-uuid", response.getPlayer().getUuid().toString());
                map.put("response", response.getResponse());
                section.set(response.getResponseId().toString(), map);
                return true;
            } else {
                getLogger().log(Level.SEVERE, "Error! That question does not exist.");
            }
        } else {
            getLogger().log(Level.SEVERE, "Error! Could not add response to file.");
        }
        return false;
    }

    public Optional<PlayerResponse> getResponse(@NotNull Question question, @NotNull UUID responseId) {
        FileConfiguration responseFile = getResponseFile();
        String basePath = question.getContent() + "." + responseId + ".";
        if (responseFile != null) {
            if (getQuestionManager().questionExists(question.getContent())) {
                String playerName = responseFile.getString(basePath + "player-name");
                UUID playerUUID = UUID.fromString(responseFile.getString(basePath + "player-uuid"));
                String response = responseFile.getString(basePath + "response");
                return Optional.of(new PlayerResponse(new SPlayer(playerName, playerUUID), question, response));
            } else {
                getLogger().log(Level.SEVERE, "Error! That question does not exist.");
            }
        } else {
            getLogger().log(Level.SEVERE, "Error! Could not find response file.");
        }
        return Optional.empty();
    }

    public boolean removeResponseFromFile(@NotNull Question question, @NotNull UUID responseId) {
        FileConfiguration responseFile = getResponseFile();
        if (responseFile != null) {
            if (getQuestionManager().questionExists(question.getContent())) {
                responseFile.set(question.getContent() + "." + responseId, null);
                return true;
            } else {
                getLogger().log(Level.SEVERE, "That question does not exist.");
            }
        } else {
            getLogger().log(Level.SEVERE, "The response file does not exist.");
        }
        return false;
    }

    private void createFileIfNotExist() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Could not create responses.yml file. Plugin disabling.");
            }
        }
    }

    private File getFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    private FileConfiguration getResponseFile() {
        File file = getFile();
        if (file == null) return null;
        try {
            return YamlConfiguration.loadConfiguration(file);
        } catch (IllegalArgumentException e) {
            getLogger().log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    private Logger getLogger() {
        return logger;
    }

    private Sentiment getPlugin() {
        return plugin;
    }

    private QuestionManager getQuestionManager() {
        return questionManager;
    }
}
