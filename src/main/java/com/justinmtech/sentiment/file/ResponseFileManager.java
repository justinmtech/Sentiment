package com.justinmtech.sentiment.file;

import com.justinmtech.sentiment.*;
import com.justinmtech.sentiment.player.SPlayer;
import com.justinmtech.sentiment.questions.PlayerResponse;
import com.justinmtech.sentiment.questions.Question;
import com.justinmtech.sentiment.questions.QuestionManager;
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

    private File responseFile;
    private FileConfiguration responseFileConfig;

    private static final String FILE_NAME = "responses.yml";

    private static final String RESPONSES_BASE_PATH = "responses.";
    private static final String PLAYER_NAME = "player-name";
    private static final String PLAYER_UUID = "player-uuid";
    private static final String PLAYER_RESPONSE = "response";

    private static final String RESPONSE_FILE_CREATED = "Response file created.";
    private static final String QUESTION_DOES_NOT_EXIST = "Error! That question does not exist.";
    private static final String COULD_NOT_ADD_RESPONSE = "Error! Could not add response to file.";
    private static final String RESPONSE_FILE_DOES_NOT_EXIST = "Error! Response file does not exist.";

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

                map.put(PLAYER_NAME, response.getPlayer().getName());
                map.put(PLAYER_UUID, response.getPlayer().getUuid().toString());
                map.put(PLAYER_RESPONSE, response.getResponse());
                getResponseFileConfig().set(RESPONSES_BASE_PATH + response.getResponseId().toString(), map);
                return saveResponseFile();
            } else {
                getLogger().log(Level.SEVERE, QUESTION_DOES_NOT_EXIST);
            }
        } else {
            getLogger().log(Level.SEVERE, COULD_NOT_ADD_RESPONSE);
        }
        return false;
    }

    public Optional<PlayerResponse> getResponse(@NotNull Question question, @NotNull UUID responseId) {
        String basePath = RESPONSES_BASE_PATH + question.getContent() + "." + responseId + ".";
        if (responseFile.exists()) {
            if (getQuestionManager().questionExists(question.getContent())) {

                String playerName = responseFileConfig.getString(basePath + PLAYER_NAME);
                if (playerName == null) playerName = PlayerFileManager.UNKNOWN_NAME;

                String uuidString = responseFileConfig.getString(basePath + PLAYER_UUID);
                if (uuidString == null) return Optional.empty();
                UUID playerUUID = UUID.fromString(uuidString);

                String response = responseFileConfig.getString(basePath + PLAYER_RESPONSE);
                if (response == null) response = "";

                return Optional.of(new PlayerResponse(new SPlayer(playerName, playerUUID), question, response));
            } else {
                getLogger().log(Level.SEVERE, QUESTION_DOES_NOT_EXIST);
            }
        } else {
            getLogger().log(Level.SEVERE, RESPONSE_FILE_DOES_NOT_EXIST);
        }
        return Optional.empty();
    }

    public boolean removeResponseFromFile(@NotNull Question question, @NotNull UUID responseId) {
        if (responseFile.exists()) {
            if (getQuestionManager().questionExists(question.getContent())) {
                responseFileConfig.set(RESPONSES_BASE_PATH + question.getContent() + "." + responseId, null);
                return saveResponseFile();
            } else {
                getLogger().log(Level.SEVERE, QUESTION_DOES_NOT_EXIST);
            }
        } else {
            getLogger().log(Level.SEVERE, RESPONSE_FILE_DOES_NOT_EXIST);
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
            getLogger().log(Level.INFO, RESPONSE_FILE_CREATED);
        }
        responseFileConfig = YamlConfiguration.loadConfiguration(responseFile);
    }

    private FileConfiguration getResponseFileConfig() {
        return responseFileConfig;
    }

    private boolean saveResponseFile() {
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
