package me.wintyy.wteams.commands.subcommands;

import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.objects.SubCommand;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Invite extends SubCommand {
    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/team invite <player>";
    }

    @Override
    public String getDescription() {
        return "Invite a player to your team. (only works for leaders)";
    }

    @Override
    public void perform(Player player, String[] args) {
        TeamManager teamManager = new TeamManager();
        FileConfiguration lang = LangConfigUtil.getConfig();
        String prefix = ColorUtil.CC(lang.getString("PREFIX"));
        if (args.length == 1){
            player.sendMessage(ColorUtil.CC("&c/team invite <player>"));
            return;
        }
        if (args.length > 1){
            PlayerTeam team = teamManager.getPlayerTeam(player);
            Player found = Bukkit.getPlayer(args[1]);
            if (found == null){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_INVITE")));
                return;
            }
            if (team == null){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_INVITE")));
                return;
            }
            if (!team.getLeader().equals(player.getUniqueId())){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_INVITE")));
                return;
            }
            player.sendMessage(prefix + ColorUtil.CC(lang.getString("INVITED")
                    .replace("%player%", args[1])));
            teamManager.invitePlayer(team, found);
        }
    }
}
