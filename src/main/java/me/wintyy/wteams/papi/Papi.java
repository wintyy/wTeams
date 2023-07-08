package me.wintyy.wteams.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.objects.PlayerTeam;
import me.wintyy.wteams.utils.ColorUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Papi extends PlaceholderExpansion implements Relational {
    TeamManager teamManager = new TeamManager();

    @Override
    public @NotNull String getIdentifier() {
        return "wteams";
    }

    @Override
    public @NotNull String getAuthor() {
        return "wintyy";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("team")){
            PlayerTeam playerTeam = teamManager.getPlayerTeam(player.getPlayer());
            if (playerTeam == null){
                return ColorUtil.CC("&4No-Team");
            }else{
                return playerTeam.getName();
            }
        }

        return null;
    }

    @Override
    public String onPlaceholderRequest(Player one, Player two, String params) {
        if(one == null || two == null)
            return null;

        if(params.equalsIgnoreCase("color")) {
            TeamManager teamManager = new TeamManager();
            PlayerTeam team1 = teamManager.getPlayerTeam(one);
            PlayerTeam team2 = teamManager.getPlayerTeam(two);
            if (team1 == null || team2 == null){
                return ColorUtil.CC("&f");
            }
            if(team1.getMembers().contains(two.getUniqueId())) {
                return ColorUtil.CC("&a");
            } else if (team1.getAllies().contains(team2.getUuid())) {
                return ColorUtil.CC("&d");
            }else{
                return ColorUtil.CC("&4");
            }
        }

        return ColorUtil.CC("&f");
    }
}
