package me.wintyy.wteams.managers;

import lombok.Getter;
import me.wintyy.wteams.WTeams;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.TeamsConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TeamManager {


    @Getter
    public static TeamManager instance = new TeamManager();

    @Getter
    public static ArrayList<PlayerTeam> teams = new ArrayList<>();

    FileConfiguration teamsyml = TeamsConfigUtil.getConfig();

    File file = new File(WTeams.getInstance().getDataFolder(), "teams.yml");


    public void createTeam(String name, Player player){
        PlayerTeam team = new PlayerTeam(name, UUID.randomUUID(), player.getUniqueId());
        team.setAllies(new ArrayList<>());
        team.setMembers(new ArrayList<>());
        team.getMembers().add(player.getUniqueId());
        List<String> tempUuidList = new ArrayList<>();
        for (UUID uuid : team.getMembers()){
            tempUuidList.add(uuid.toString());
        }


        teams.add(team);

        teamsyml.createSection(name);
        teamsyml.set(name + ".uuid", team.getUuid().toString());
        teamsyml.set(name + ".allies", team.getAllies());
        teamsyml.set(name + ".leader", team.getLeader().toString());
        teamsyml.set(name + ".members", tempUuidList);


        try {
            teamsyml.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to save data for " + team.getName());
        }

    }

    public void deleteTeam(PlayerTeam team){
        if (team != null){
            teamsyml.set(team.getName(), null);
            teams.remove(team);

            try {
                teamsyml.save(file);
            } catch (IOException e) {
                Bukkit.getLogger().severe("Failed to save data for " + team.getName());
            }
        }
    }



    public PlayerTeam getTeam(String string) {
        for (PlayerTeam team : getTeams()) {
            if (team.getName().equalsIgnoreCase(string)) {
                return team;
            }
        }
        return null;
    }

    public PlayerTeam getPlayerTeam(Player player){
        for (PlayerTeam team : teams){
            if (team.getMembers().contains(player.getUniqueId())){
                return team;
            }
        }
        return null;
    }



    public void saveTeam(PlayerTeam team){
        if (team != null){
            List<String> tempUuidList = new ArrayList<>();
            for (UUID uuid : team.getMembers()){
                tempUuidList.add(uuid.toString());
            }
            teamsyml.set(team.getName() + ".uuid", team.getUuid().toString());
            teamsyml.set(team.getName() + ".allies", team.getAllies());
            teamsyml.set(team.getName() + ".leader", team.getLeader().toString());
            teamsyml.set(team.getName() + ".members", tempUuidList);
            if (team.getHome() != null) {
                teamsyml.set(team.getName() + ".home", team.getHome());
            }
            try {
                teamsyml.save(file);
            } catch (IOException e) {
                Bukkit.getLogger().severe("Failed to save data for " + team.getName());
            }

        }
    }
}
