package me.wintyy.wteams;

import lombok.Getter;
import me.wintyy.wteams.commands.MainCommand;
import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.papi.Papi;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.ConfigUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import me.wintyy.wteams.utils.TeamsConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public final class WTeams extends JavaPlugin {

    @Getter
    public static WTeams instance;


    @Override
    public void onEnable() {
        instance = this;
        ConfigUtil.init();
        LangConfigUtil.init();
        TeamsConfigUtil.init();
        TeamManager teamManager = new TeamManager();
        getCommand("team").setExecutor(new MainCommand());
        CommandSender cs = Bukkit.getConsoleSender();

        new Papi().register();

        Bukkit.getPluginManager().registerEvents(new FriendlyFire(), this);


        for (String teamname : TeamsConfigUtil.getConfig().getKeys(false)){
            ConfigurationSection teamsection = TeamsConfigUtil.getConfig().getConfigurationSection(teamname);
            PlayerTeam team = new PlayerTeam();
            team.setName(teamsection.getName());
            team.setUuid(UUID.fromString(teamsection.getString("uuid")));
            team.setLeader(UUID.fromString(teamsection.getString("leader")));
            team.setMembers(new ArrayList<>());
            team.setAllies(new ArrayList<>());
            for (String string : teamsection.getStringList("members")){
                team.getMembers().add(UUID.fromString(string));
            }
            for (String string : teamsection.getStringList("allies")){
                team.getAllies().add(UUID.fromString(string));
            }
            teamManager.teams.add(team);
        }


        new BukkitRunnable(){
            @Override
            public void run() {
                TeamManager teamManager = new TeamManager();
                for (PlayerTeam team : teamManager.getTeams()){
                    teamManager.saveTeam(team);
                    CommandSender cs = Bukkit.getConsoleSender();
                    cs.sendMessage(ColorUtil.CC("&aSaved ") + team.getName());
                }
            }
        }.runTaskTimerAsynchronously(this, 0, 20 * 20);


        cs.sendMessage(ColorUtil.CC("&7&m----------------------"));
        cs.sendMessage(ColorUtil.CC("&b&lLoading wTeams."));
        cs.sendMessage(ColorUtil.CC(""));
        cs.sendMessage(ColorUtil.CC("&b&lwTeams:"));
        cs.sendMessage(ColorUtil.CC("&fMade by wintyy."));
        cs.sendMessage(ColorUtil.CC("&fthank you for using the plugin!"));
        cs.sendMessage(ColorUtil.CC(""));
        cs.sendMessage(ColorUtil.CC("&b&lLoaded wTeams."));
        cs.sendMessage(ColorUtil.CC("&7&m----------------------"));

    }

    @Override
    public void onDisable() {
        TeamManager teamManager = new TeamManager();
        for (PlayerTeam team : teamManager.getTeams()){
            teamManager.saveTeam(team);
            CommandSender cs = Bukkit.getConsoleSender();
            cs.sendMessage(ColorUtil.CC("&aSaved ") + team.getName());
        }
        instance = null;
    }
}
