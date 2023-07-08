package me.wintyy.wteams.commands.subcommands;

import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.objects.SubCommand;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Disband extends SubCommand {
    @Override
    public String getName() {
        return "disband";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/team disband";
    }

    @Override
    public String getDescription() {
        return "Disband the team you are currently in (only for Leaders)";
    }

    @Override
    public void perform(Player player, String[] args) {
        TeamManager teamManager = new TeamManager();
        FileConfiguration lang = LangConfigUtil.getConfig();
        String prefix = ColorUtil.CC(lang.getString("PREFIX"));
        PlayerTeam team = teamManager.getPlayerTeam(player);
        if (team == null) {
            player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_DISBAND")));
            return;
        }
        if (!team.getLeader().equals(player.getUniqueId())) {
            player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_DISBAND")));
            return;
        }
        teamManager.deleteTeam(team);
        player.sendMessage(prefix + ColorUtil.CC(lang.getString("DISBAND")));
    }

}
