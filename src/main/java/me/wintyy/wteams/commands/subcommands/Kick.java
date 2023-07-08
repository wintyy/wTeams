package me.wintyy.wteams.commands.subcommands;

import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.objects.SubCommand;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.ConfigUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Kick extends SubCommand {
    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/team kick <player>";
    }

    @Override
    public String getDescription() {
        return "Kick a player from your team (only leaders can do it)";
    }

    @Override
    public void perform(Player player, String[] args) {
        TeamManager teamManager = new TeamManager();
        FileConfiguration lang = LangConfigUtil.getConfig();
        FileConfiguration cfg = ConfigUtil.getConfig();
        String prefix = ColorUtil.CC(lang.getString("PREFIX"));
        if (args.length == 1){
            player.sendMessage(ColorUtil.CC("&c/team kick <player>"));
            return;
        }
        if (args.length > 1){
            PlayerTeam team = teamManager.getPlayerTeam(player);
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_KICK")));
                return;
            }
            if (team == null){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_KICK")));
                return;
            }
            if (!team.getLeader().equals(player.getUniqueId())){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_KICK")));
                return;
            }
            if (!team.getMembers().contains(target.getUniqueId())){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_KICK")));
                return;
            }
            player.sendMessage(prefix + ColorUtil.CC(lang.getString("KICK")
                    .replace("%player%", args[1])));
            team.getMembers().remove(target.getUniqueId());
        }
    }
}
