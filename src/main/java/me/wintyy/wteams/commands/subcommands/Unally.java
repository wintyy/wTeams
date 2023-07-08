package me.wintyy.wteams.commands.subcommands;

import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.objects.SubCommand;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.ConfigUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Unally extends SubCommand {
    @Override
    public String getName() {
        return "unally";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/team unally <team>";
    }

    @Override
    public String getDescription() {
        return "Unally Another Team";
    }

    @Override
    public void perform(Player player, String[] args) {
        TeamManager teamManager = new TeamManager();
        FileConfiguration lang = LangConfigUtil.getConfig();
        FileConfiguration cfg = ConfigUtil.getConfig();
        String prefix = ColorUtil.CC(lang.getString("PREFIX"));
        if (args.length == 1){
            player.sendMessage(prefix + ColorUtil.CC("&c/team unally <team>"));
        }
        if (args.length > 1){
            PlayerTeam playerTeam = teamManager.getPlayerTeam(player);
            PlayerTeam targetTeam = teamManager.getTeam(args[1]);
            if (playerTeam == null){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_ALLY")));
                return;
            }
            if (targetTeam == null){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_ALLY")));
                return;
            }
            if (!playerTeam.getLeader().equals(player.getUniqueId())){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_ALLY")));
                return;
            }
            if (playerTeam.getAllies().contains(targetTeam)){
                teamManager.unAlly(playerTeam, targetTeam);
            }
        }

    }
}
