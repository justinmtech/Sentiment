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
    private File responseFile;
    private FileConfiguration responseFileConfig;

    public ResponseFileManager(Sentiment plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.questionManager = plugin.getQuestionManager();
        createOrLoadFile();
    }

    public boolean addResponseToFile(@NotNull PlayerResponse response, @NotNull Question question) {
        if (responseFile.exists()) {
            if (getQuestionManager().questionExists(question.getContent())) {
                Map<String, String> map = new HashMap<>();

                map.put("player-name", response.getPlayer().getName());
                map.put("player-uuid", response.getPlayer().getUuid().toString());
                map.put("response", response.getResponse());
                getResponseFileConfig().set("responses." + response.getResponseId().toString(), map);
                return saveFile();
            } else {
                getLogger().log(Level.SEVERE, "Error! That question does not exist.");
            }
        } else {
            getLogger().log(Level.SEVERE, "Error! Could not add response to file.");
        }
        return false;
    }

    public Optional<PlayerResponse> getResponse(@NotNull Question question, @NotNull UUID responseId) {
        String basePath = "responses." + question.getContent() + "." + responseId + ".";
        if (responseFile.exists()) {
            if (getQuestionManager().questionExists(question.getContent())) {

                String playerName = responseFileConfig.getString(basePath + "player-name");
                if (playerName == null) playerName = PlayerFileManager.UNKNOWN_NAME;

                String uuidString = responseFileConfig.getString(basePath + "player-uuid");
                if (uuidString == null) return Optional.empty();
                UUID playerUUID = UUID.fromString(uuidString);

                String response = responseFileConfig.getString(basePath + "response");
                if (response == null) response = "";

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
        if (responseFile.exists()) {
            if (getQuestionManager().questionExists(question.getContent())) {
                responseFileConfig.set("responses." + question.getContent() + "." + responseId, null);
                return saveFile();
            } else {
                getLogger().log(Level.SEVERE, "That question does not exist.");
            }
        } else {
            getLogger().log(Level.SEVERE, "The response file does not exist.");
        }
        return false;
    }

    private File getResponseFile() {
        return responseFile;
    }

    private void createOrLoadFile() {
        responseFile = new File(getPlugin().getDataFolder(), FILE_NAME);
        if (!responseFile.exists()) {
            responseFile.getParentFile().mkdirs();
            getPlugin().saveResource(FILE_NAME, false);
            getLogger().log(Level.INFO, "Player data file created.");
        }
        responseFileConfig = YamlConfiguration.loadConfiguration(responseFile);
    }

    private FileConfiguration getResponseFileConfig() {
        return responseFileConfig;
    }

    private boolean saveFile() {
        try {
            getResponseFileConfig().save(getResponseFile());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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
