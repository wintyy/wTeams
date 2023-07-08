package me.wintyy.wteams.commands.subcommands;

import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.objects.SubCommand;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Info extends SubCommand {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/team info <team>";
    }

    @Override
    public String getDescription() {
        return "get info about a team";
    }

    @Override
    public void perform(Player player, String[] args) {
        TeamManager teamManager = new TeamManager();
        FileConfiguration lang = LangConfigUtil.getConfig();
        String prefix = ColorUtil.CC(lang.getString("PREFIX"));
        if (args.length == 1){
            player.sendMessage(prefix + ColorUtil.CC(lang.getString("INFO_INVALID")));
        }
        if (args.length > 1){
            PlayerTeam team = teamManager.getTeam(args[1]);
            if (team == null){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("INFO_INVALID")));
                return;
            }
            OfflinePlayer leader = Bukkit.getOfflinePlayer(team.getLeader());
            List<String> members = new ArrayList<>();
            List<String> allies = new ArrayList<>();
            if (team.getAllies() == null){
                allies.clear();
                allies.add(ColorUtil.CC("None"));
            }else{
                for (PlayerTeam allyTeam : team.getAllies()){
                    allies.add(allyTeam.getName());
                }
            }
            for (UUID uuid : team.getMembers()){
                OfflinePlayer foundMember = Bukkit.getOfflinePlayer(uuid);
                members.add(foundMember.getName());
            }
            for (String string : lang.getStringList("INFO")){
                player.sendMessage(ColorUtil.CC(string
                        .replace("%team%", team.getName())
                        .replace("%leader%", leader.getName())
                        .replace("%members%", members.toString())
                        .replace("%allies%", allies.toString())

                ));
            }

        }
    }
}
