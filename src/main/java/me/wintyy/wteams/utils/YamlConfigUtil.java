package me.wintyy.wteams.utils;

import me.wintyy.wteams.WTeams;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlConfigUtil extends YamlConfiguration {

    private File jfile;

    public YamlConfigUtil(String file) {
        jfile = new File(WTeams.getInstance().getDataFolder(), file);
        if (!this.jfile.exists()) {
            jfile.getParentFile().mkdirs();
            WTeams.getInstance().saveResource(file, false);
        }

        try {
            this.load(jfile);
        } catch (InvalidConfigurationException | IOException x) {
            Bukkit.getConsoleSender().sendMessage(ColorUtil.CC("&4Error whilst loading " + file + ": \n&c" + x.getMessage() + "\n &cCaused by: \n" + x.getCause()));
        }
    }



    public void save() {
        try {
            this.save(this.jfile);
        } catch (IOException x) {
            Bukkit.getConsoleSender().sendMessage(ColorUtil.CC("&4Error whilst loading " + jfile.getName() + ": \n&c" + x.getMessage() + "\n &cCaused by: \n" + x.getCause()));
        }

    }

    public void delete() {
        jfile.delete();
    }

    public ConfigurationSection getConfiguration() {
        return null;
    }
}
