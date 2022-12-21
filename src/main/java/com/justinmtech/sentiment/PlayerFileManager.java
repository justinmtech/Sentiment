package com.justinmtech.sentiment;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Add, remove and update SPlayers in the FILE_NAME.yml
 */
public class PlayerFileManager {
    //The name to be used for the player storage file
    private final static String FILE_NAME = "players.yml";

    //Collection of players
    private final static String PLAYER_ROOT = "players";

    //The player's username (if stored)
    private final static String PLAYER_NAME = "player-name";

    //Whether the player has declined to participate
    private final static String OPT_OUT = "opt-out";

    //Collection of questions the player has answered
    private final static String QUESTIONS_ANSWERED = "questionsAnswered";

    //Placeholder for empty player names
    private final static String UNKNOWN_NAME = "Unknown";

    public PlayerFileManager(Logger logger) {
        boolean created = createFileIfNotExists();
        if (created) logger.log(Level.INFO, "Player file created: " + FILE_NAME + ".yml");
    }

    /**
     * Set a player field in the file
     * @param player SPlayer object
     */
    public void setPlayer(@NotNull SPlayer player) {
        UUID uuid = player.getUuid();
        setDataInFile(getPlayerPath(uuid) + "." + PLAYER_NAME, player.getName());
        setDataInFile(getPlayerPath(uuid) + "." + OPT_OUT, player.isOptOut());
        setDataInFile(getPlayerPath(uuid) + "." + QUESTIONS_ANSWERED, player.getQuestionsAnswered());
    }

    /**
     * Get and build an SPlayer from the player data file
     * @param uuid The UUID of the player
     * @return Optional of SPlayer
     */
    public Optional<SPlayer> getPlayer(@NotNull UUID uuid) {
        ConfigurationSection section = getFileConfiguration().getConfigurationSection(getPlayerPath(uuid));
        if (section == null) return Optional.empty();

        String name = section.getString(PLAYER_NAME);
        if (name == null) name = UNKNOWN_NAME;
        boolean optOut = section.getBoolean(OPT_OUT, false);
        SPlayer sPlayer = new SPlayer(name, uuid, optOut);
        sPlayer.setQuestionsAnswered(getQuestionsAnswered(section));
        return Optional.of(sPlayer);
    }

    private Set<String> getQuestionsAnswered(@NotNull ConfigurationSection section) {
        List<?> questionsAnswered = section.getList(QUESTIONS_ANSWERED);
        if (questionsAnswered == null) return new HashSet<>();
        Set<String> questionsAnsweredList = new HashSet<>();
        for (Object o : questionsAnswered) {
            if (o instanceof String) {
                questionsAnsweredList.add(String.valueOf(o));
            }
        }
        return questionsAnsweredList;
    }

    /**
     * @param uuid UUID of the player to remove
     */
    public void removePlayer(@NotNull UUID uuid) {
        setDataInFile(getPlayerPath(uuid), null);
    }

    private void setDataInFile(String path, Object data) {
        getFileConfiguration().set(path, data);
    }

    private String getPlayerPath(@NotNull UUID uuid) {
        return PLAYER_ROOT + "." + uuid;
    }

    private FileConfiguration getFileConfiguration() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    private File getFile() {
        return new File(FILE_NAME);
    }

    private boolean createFileIfNotExists() {
        if (!getFile().exists()) {
            try {
                return getFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
