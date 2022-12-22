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
                ConfigurationSection section = responseFileConfig.getConfigurationSection(question.getContent());
                Map<String, String> map = new HashMap<>();

                map.put("player-name", response.getPlayer().getName());
                map.put("player-uuid", response.getPlayer().getUuid().toString());
                map.put("response", response.getResponse());
                section.set(response.getResponseId().toString(), map);
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
        String basePath = question.getContent() + "." + responseId + ".";
        if (responseFile.exists()) {
            if (getQuestionManager().questionExists(question.getContent())) {
                String playerName = responseFileConfig.getString(basePath + "player-name");
                UUID playerUUID = UUID.fromString(responseFileConfig.getString(basePath + "player-uuid"));
                String response = responseFileConfig.getString(basePath + "response");
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
                responseFileConfig.set(question.getContent() + "." + responseId, null);
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

    public FileConfiguration getResponseFileConfig() {
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
