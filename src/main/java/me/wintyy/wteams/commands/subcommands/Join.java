package me.wintyy.wteams.commands.subcommands;

import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.objects.SubCommand;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Join extends SubCommand {
    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/team join <team>";
    }

    @Override
    public String getDescription() {
        return "Join a team that you got invited to";
    }

    @Override
    public void perform(Player player, String[] args) {
        TeamManager teamManager = new TeamManager();
        FileConfiguration lang = LangConfigUtil.getConfig();
        String prefix = ColorUtil.CC(lang.getString("PREFIX"));
        if (args.length == 1){
            player.sendMessage(ColorUtil.CC("&c/team join <team>"));
            return;
        }
        if (args.length > 1){
            PlayerTeam foundTeam = teamManager.getTeam(args[1]);
            PlayerTeam playerTeam = teamManager.getPlayerTeam(player);
            if (playerTeam != null){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_JOIN")));
                return;
            }
            if (foundTeam == null){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_JOIN")));
                return;
            }
            if (!teamManager.isInvited(foundTeam, player)){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_JOIN")));
                return;
            }
            player.sendMessage(prefix + ColorUtil.CC(lang.getString("JOINED")));
            foundTeam.getMembers().add(player.getUniqueId());
        }
    }
}
