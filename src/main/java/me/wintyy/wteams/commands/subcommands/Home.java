package me.wintyy.wteams.commands.subcommands;

import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.objects.SubCommand;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Home extends SubCommand {
    @Override
    public String getName() {
        return "home";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/team home";
    }

    @Override
    public String getDescription() {
        return "Teleport to Team Home";
    }

    @Override
    public void perform(Player player, String[] args) {
        TeamManager teamManager = new TeamManager();
        FileConfiguration lang = LangConfigUtil.getConfig();
        String prefix = ColorUtil.CC(lang.getString("PREFIX"));
        PlayerTeam team = teamManager.getPlayerTeam(player);
        if (team == null) {
            player.sendMessage(prefix + ColorUtil.CC(lang.getString("NO_HOME")));
            return;
        }
        if (team.getHome() == null) {
            player.sendMessage(prefix + ColorUtil.CC(lang.getString("NO_HOME")));
            return;
        }
        player.teleport(team.getHome());
        player.sendMessage(prefix + ColorUtil.CC(lang.getString("HOME_TP")));
    }

}
