package com.justinmtech.sentiment.file;

import com.justinmtech.sentiment.Sentiment;
import com.justinmtech.sentiment.player.SPlayer;
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
    private final Sentiment plugin;

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

    private File playerFile;
    private FileConfiguration playerConfigFile;

    public PlayerFileManager(Sentiment plugin) {
        this.plugin = plugin;
        createOrLoadFile();
    }

    /**
     * Set a player field in the file
     * @param player SPlayer object
     */
    public void setPlayer(@NotNull SPlayer player) {
        UUID uuid = player.getUuid();
        playerConfigFile.set("test", "test");
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
        File file = getPlayerFile();
        ConfigurationSection section = getFileConfiguration(file).getConfigurationSection(getPlayerPath(uuid));
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
        playerConfigFile.set(path, data);
        try {
            playerConfigFile.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPlayerPath(@NotNull UUID uuid) {
        return PLAYER_ROOT + "." + uuid;
    }

    private FileConfiguration getFileConfiguration(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }


    private void createOrLoadFile() {
        playerFile = new File(getPlugin().getDataFolder(), FILE_NAME);
        if (!playerFile.exists()) {
            playerFile.getParentFile().mkdirs();
            getPlugin().saveResource(FILE_NAME, false);
            getLogger().log(Level.INFO, "Player data file created.");
        }
        playerConfigFile = YamlConfiguration.loadConfiguration(playerFile);
    }

    private Sentiment getPlugin() {
        return plugin;
    }

    private File getPlayerFile() {
        return playerFile;
    }

    public FileConfiguration getPlayerConfigFile() {
        return playerConfigFile;
    }

    private Logger getLogger() {
        return getPlugin().getLogger();
    }


}
