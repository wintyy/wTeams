package me.wintyy.wteams.managers;

import lombok.Getter;
import me.wintyy.wteams.WTeams;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import me.wintyy.wteams.utils.TeamsConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TeamManager {


    @Getter
    public static TeamManager instance = new TeamManager();

    @Getter
    public static ArrayList<PlayerTeam> teams = new ArrayList<>();

    public static Map<Player, Map<PlayerTeam, Boolean>> inviteMap = new HashMap<>();
    public static Map<PlayerTeam, Map<PlayerTeam, Boolean>> allyMap = new HashMap<>();

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
            for (PlayerTeam teams : teams){
                if (teams.getAllies().contains(team)){
                    teams.getAllies().remove(team);
                }
            }
            teamsyml.set(team.getName(), null);
            teams.remove(team);

            try {
                teamsyml.save(file);
            } catch (IOException e) {
                Bukkit.getLogger().severe("Failed to save data for " + team.getName());
            }
        }
    }


    public void invitePlayer(PlayerTeam team, Player player){
        if (inviteMap.containsKey(player)){
            Map<PlayerTeam, Boolean> tempInviteMap = new HashMap<>(inviteMap.get(player));
            tempInviteMap.put(team, true);
            inviteMap.put(player, tempInviteMap);
            return;
        }
        Map<PlayerTeam, Boolean> tempInviteMap = new HashMap<>();
        tempInviteMap.put(team, true);
        inviteMap.put(player, tempInviteMap);
    }

    public void allyRequest(PlayerTeam team, PlayerTeam team2){
        if (team != null) {
            if (team2 != null) {
                Map<PlayerTeam, Boolean> allyRequest;
                if (allyMap.containsKey(team)){
                    allyRequest = allyMap.get(team);
                } else {
                    allyRequest = new HashMap<>();
                }
                allyRequest.put(team2, true);
                allyMap.put(team, allyRequest);

                Player team2leader = Bukkit.getPlayer(team2.getLeader());
                Player team1leader = Bukkit.getPlayer(team.getLeader());
                if (team2leader != null && team1leader != null){
                    team2leader.sendMessage(ColorUtil.CC(LangConfigUtil.getConfig().getString("ALLY_REQUEST")
                            .replace("%team%", team.getName())));

                }
            }
        }
    }

    public void addAlly(PlayerTeam team, PlayerTeam team2) {
        if (team != null) {
            if (team2 != null) {
                team.getAllies().add(team2.getUuid());
                team2.getAllies().add(team.getUuid());
                Map<PlayerTeam, Boolean> allyRequest;
                if (allyMap.containsKey(team)) {
                    allyRequest = allyMap.get(team);
                    allyRequest.remove(team2);
                }
                if (allyMap.containsKey(team2)) {
                    allyRequest = allyMap.get(team2);
                    allyRequest.remove(team);
                }
                Player team2leader = Bukkit.getPlayer(team2.getLeader());
                Player team1leader = Bukkit.getPlayer(team.getLeader());
                if (team2leader != null && team1leader != null) {
                    team2leader.sendMessage(ColorUtil.CC(LangConfigUtil.getConfig().getString("ALLY_ACCEPT")
                            .replace("%team%", team.getName())));
                    team1leader.sendMessage(ColorUtil.CC(LangConfigUtil.getConfig().getString("ALLY_ACCEPT")
                            .replace("%team%", team2.getName())));
                }
            }
        }
    }

    public void unAlly(PlayerTeam team, PlayerTeam team2) {
        if (team != null) {
            if (team2 != null) {
                team.getAllies().remove(team2);
                team2.getAllies().remove(team);
            }
        }
    }

    public Boolean hasAllyRequest(PlayerTeam team, PlayerTeam team2){
        if (team != null){
            if (allyMap.containsKey(team2)) {
                Map<PlayerTeam, Boolean> allyRequest = allyMap.get(team2);
                if (allyRequest.get(team) != null) {
                    return allyRequest.get(team);
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public void uninvitePlayer(PlayerTeam team, Player player){
        if (inviteMap.containsKey(player)){
            Map<PlayerTeam, Boolean> tempInviteMap = new HashMap<>(inviteMap.get(player));
            tempInviteMap.put(team, false);
            inviteMap.put(player, tempInviteMap);
            return;
        }
        Map<PlayerTeam, Boolean> tempInviteMap = new HashMap<>();
        tempInviteMap.put(team, false);
        inviteMap.put(player, tempInviteMap);
    }

    public boolean isInvited(PlayerTeam team, Player player){
        if (team != null){
            if (inviteMap.containsKey(player)){
                Map<PlayerTeam, Boolean> tempInviteMap = new HashMap<>(inviteMap.get(player));
                return tempInviteMap.get(team);
            }
        }
        return false;
    }



    public void renameTeam(PlayerTeam team, String name){
        if (team != null){
            teamsyml.set(team.getName(), null);
            team.setName(name);
            teamsyml.createSection(name);
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



    public PlayerTeam getTeam(String string) {
        for (PlayerTeam team : getTeams()) {
            if (team.getName().equalsIgnoreCase(string)) {
                return team;
            }
        }
        return null;
    }

    public PlayerTeam getTeam(UUID uuid) {
        for (PlayerTeam team : getTeams()) {
            if (team.getUuid().equals(uuid)) {
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
            List<String> tempAllyUuidList = new ArrayList<>();
            for (UUID uuid : team.getAllies()){
                tempAllyUuidList.add(uuid.toString());
            }
            teamsyml.set(team.getName() + ".uuid", team.getUuid().toString());
            teamsyml.set(team.getName() + ".allies", tempAllyUuidList);
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
