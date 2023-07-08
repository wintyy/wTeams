package me.wintyy.wteams.utils;

import org.bukkit.configuration.file.FileConfiguration;

public class TeamsConfigUtil {

    private static FileConfiguration config;

    public static FileConfiguration getConfig() {
        return config;
    }

    public static void init() {
        config = new YamlConfigUtil("teams.yml");
    }


}
