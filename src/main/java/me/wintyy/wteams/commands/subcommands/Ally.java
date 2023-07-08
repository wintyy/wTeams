package me.wintyy.wteams.commands.subcommands;

import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.objects.SubCommand;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.ConfigUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Ally extends SubCommand {
    @Override
    public String getName() {
        return "ally";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/team ally <team>";
    }

    @Override
    public String getDescription() {
        return "Ally Another Team using this command";
    }

    @Override
    public void perform(Player player, String[] args) {
        TeamManager teamManager = new TeamManager();
        FileConfiguration lang = LangConfigUtil.getConfig();
        FileConfiguration cfg = ConfigUtil.getConfig();
        String prefix = ColorUtil.CC(lang.getString("PREFIX"));
        if (args.length == 1){
            player.sendMessage(prefix + ColorUtil.CC("&c/team ally <team>"));
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
            if (teamManager.hasAllyRequest(playerTeam, targetTeam)){
                if (playerTeam.getAllies().size() <= cfg.getInt("ALLIES_LIMIT")) {
                    teamManager.addAlly(playerTeam, targetTeam);
                    return;
                }
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_ALLY_LIMIT")));
                return;
            }
            if (!teamManager.hasAllyRequest(playerTeam, targetTeam)){
                if (playerTeam.getAllies().size() <= cfg.getInt("ALLIES_LIMIT")) {
                    teamManager.allyRequest(playerTeam, targetTeam);
                    return;
                }
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_ALLY_LIMIT")));
            }
        }

    }
}
