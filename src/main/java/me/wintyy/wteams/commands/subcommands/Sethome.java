package me.wintyy.wteams.commands.subcommands;

import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.objects.SubCommand;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Sethome extends SubCommand {
    @Override
    public String getName() {
        return "sethome";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/team sethome";
    }

    @Override
    public String getDescription() {
        return "set your team home (only for Leaders)";
    }

    @Override
    public void perform(Player player, String[] args) {
        TeamManager teamManager = new TeamManager();
        FileConfiguration lang = LangConfigUtil.getConfig();
        String prefix = ColorUtil.CC(lang.getString("PREFIX"));
        PlayerTeam team = teamManager.getPlayerTeam(player);
        if (team == null) {
            player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_SETHOME")));
            return;
        }
        if (!team.getLeader().equals(player.getUniqueId())) {
            player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_SETHOME")));
            return;
        }
        team.setHome(player.getLocation());
        player.sendMessage(prefix + ColorUtil.CC(lang.getString("SETHOME")));
    }

}
