package me.wintyy.wteams.commands.subcommands;

import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.objects.SubCommand;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Leave extends SubCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/team leave";
    }

    @Override
    public String getDescription() {
        return "Leave your current team";
    }

    @Override
    public void perform(Player player, String[] args) {
        TeamManager teamManager = new TeamManager();
        FileConfiguration lang = LangConfigUtil.getConfig();
        String prefix = ColorUtil.CC(lang.getString("PREFIX"));
        if (args.length == 1){
            PlayerTeam playerTeam = teamManager.getPlayerTeam(player);
            if (playerTeam == null){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_LEAVE")));
                return;
            }
            if (playerTeam.getLeader().equals(player.getUniqueId())){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_LEAVE")));
                return;
            }
            player.sendMessage(prefix + ColorUtil.CC(lang.getString("LEAVE")));
            playerTeam.getMembers().remove(player.getUniqueId());
        }
    }
}
