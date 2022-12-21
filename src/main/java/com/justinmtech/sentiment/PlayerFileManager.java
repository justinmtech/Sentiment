package com.justinmtech.sentiment;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Load and save SPlayers from the FILE_NAME.yml
 */
public class PlayerFileManager {
    private final static String FILE_NAME = "players.yml";

    public PlayerFileManager() {
        createFile();
    }

    /**
     * Set a player field in the file
     * @param player SPlayer object
     */
    public void setPlayer(@NotNull SPlayer player) {
        setDataInFile(player.getUuid(), player.getName());
        setDataInFile(player.getUuid(), player.getQuestionsAnswered());
    }

    /**
     * Get and build an SPlayer from the player data file
     * @param uuid The UUID of the player
     * @return Optional of SPlayer
     */
    public Optional<SPlayer> getPlayer(@NotNull UUID uuid) {
        ConfigurationSection section = getFileConfiguration().getConfigurationSection(getPlayerPath(uuid));
        if (section == null) return Optional.empty();

        String name = section.getString("player-name");
        if (name == null) name = "Unknown";
        SPlayer sPlayer = new SPlayer(name, uuid);
        sPlayer.setQuestionsAnswered(getQuestionsAnswered(section));
        return Optional.of(sPlayer);
    }

    private Set<String> getQuestionsAnswered(@NotNull ConfigurationSection section) {
        List<?> questionsAnswered = section.getList("questionsAnswered");
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
        setDataInFile(uuid, null);
    }

    public void loadPlayers() {

    }

    private void setDataInFile(UUID uuid, Object data) {
        getFileConfiguration().set(getPlayerPath(uuid), data);
    }

    private String getPlayerPath(@NotNull UUID uuid) {
        return "players." + uuid;
    }

    private String getPlayerNamePath(@NotNull UUID uuid) {
        return getPlayerPath(uuid) + ".player-name";
    }

    private String getQuestionsAnsweredPath(@NotNull UUID uuid) {
        return getPlayerPath(uuid) + ".questionsAnswered";
    }

    private FileConfiguration getFileConfiguration() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    private File getFile() {
        return new File(FILE_NAME);
    }

    private boolean createFile() {
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
