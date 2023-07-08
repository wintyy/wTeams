package me.wintyy.wteams.commands.subcommands;

import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.objects.SubCommand;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Rename extends SubCommand {
    @Override
    public String getName() {
        return "rename";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/team rename <new-name>";
    }

    @Override
    public String getDescription() {
        return "Rename your team to a new name";
    }

    @Override
    public void perform(Player player, String[] args) {
        TeamManager teamManager = new TeamManager();
        FileConfiguration lang = LangConfigUtil.getConfig();
        String prefix = ColorUtil.CC(lang.getString("PREFIX"));
        if (args.length == 1){
            player.sendMessage(ColorUtil.CC("&c/team rename <new-name>"));
            return;
        }
        if (args.length > 1){
            PlayerTeam foundTeam = teamManager.getTeam(args[1]);
            PlayerTeam playerTeam = teamManager.getPlayerTeam(player);
            if (foundTeam != null){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_RENAME")));
                return;
            }
            if (playerTeam == null){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_RENAME")));
                return;
            }
            if (!playerTeam.getLeader().equals(player.getUniqueId())){
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_RENAME")));
                return;
            }
            player.sendMessage(prefix + ColorUtil.CC(lang.getString("RENAME")
                    .replace("%newname%", args[1])));
            teamManager.renameTeam(playerTeam, args[1]);
        }
    }
}
