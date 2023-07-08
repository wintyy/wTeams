package me.wintyy.wteams.commands.subcommands;

import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.SubCommand;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Create extends SubCommand {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getUsage() {
        return "/team create <name>";
    }

    @Override
    public String getDescription() {
        return "Create a team using this command!";
    }

    @Override
    public void perform(Player player, String[] args) {
        TeamManager teamManager = new TeamManager();
        FileConfiguration lang = LangConfigUtil.getConfig();
        String prefix = ColorUtil.CC(lang.getString("PREFIX"));
        if (args.length == 1){
            player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_CREATE_EMPTY")));
            return;
        }
        if (args.length == 2){
            PlayerTeam playerteam = TeamManager.getInstance().getTeam(args[1]);
            if (playerteam == null){
                teamManager.createTeam(args[1], player);
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CREATED")));
            }else{
                player.sendMessage(prefix + ColorUtil.CC(lang.getString("CANT_CREATE_ALREADY_EXISTS")));
            }

        }
    }
}
