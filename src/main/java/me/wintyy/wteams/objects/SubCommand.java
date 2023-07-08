package me.wintyy.wteams.objects;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public abstract class SubCommand {

    public abstract String getName();
    public abstract String getPermission();
    public abstract String getUsage();
    public abstract String getDescription();

    public abstract void perform(Player player, String[] args);
}
