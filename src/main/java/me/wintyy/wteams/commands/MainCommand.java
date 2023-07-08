package me.wintyy.wteams.commands;

import me.wintyy.wteams.commands.subcommands.*;
import me.wintyy.wteams.objects.SubCommand;
import me.wintyy.wteams.utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainCommand implements CommandExecutor {

    private List<SubCommand> subCommands = new ArrayList<>();

    public MainCommand(){
        subCommands.add(new Create());
        subCommands.add(new Info());
        subCommands.add(new Disband());
        subCommands.add(new Rename());
        subCommands.add(new Invite());
        subCommands.add(new Uninvite());
        subCommands.add(new Join());
        subCommands.add(new Leave());
        subCommands.add(new Kick());
        subCommands.add(new Ally());
        subCommands.add(new Unally());
        subCommands.add(new Sethome());
        subCommands.add(new Home());
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)){
            return false;
        }
        Player player = (Player) commandSender;
        if (strings.length == 0){
            player.sendMessage(ColorUtil.CC("&8&m                                                                          "));
            player.sendMessage(ColorUtil.CC(""));
            for (SubCommand subCommand : subCommands){
                player.sendMessage(ColorUtil.CC("&b") + subCommand.getUsage() + ColorUtil.CC(" &8&l| &f") + subCommand.getDescription());
            }
            player.sendMessage(ColorUtil.CC(""));
            player.sendMessage(ColorUtil.CC("&8&m                                                                          "));
            return true;
        }
        for (SubCommand subCommand : subCommands){
            if (strings[0].equalsIgnoreCase(subCommand.getName())){
                if (subCommand.getPermission() != null){
                    if (!player.hasPermission(subCommand.getPermission())){
                        return true;
                    }
                }
                subCommand.perform(player, strings);
                return true;
            }
        }
        return true;
    }
}
