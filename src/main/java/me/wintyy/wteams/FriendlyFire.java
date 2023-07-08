package me.wintyy.wteams;

import me.wintyy.wteams.managers.TeamManager;
import me.wintyy.wteams.utils.ColorUtil;
import me.wintyy.wteams.utils.ConfigUtil;
import me.wintyy.wteams.utils.LangConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FriendlyFire implements Listener {

    TeamManager teamManager = TeamManager.getInstance();

    FileConfiguration lang = LangConfigUtil.getConfig();
    FileConfiguration cfg = ConfigUtil.getConfig();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player
                && event.getEntity() instanceof Player){
            Player player = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();
            if (!cfg.getBoolean("FRIENDLY_FIRE")) {
                if (teamManager.getPlayerTeam(player).getUuid() == null){
                    return;
                }
                if (teamManager.getPlayerTeam(victim).getUuid() == null){
                    return;
                }
                if (teamManager.getPlayerTeam(player).getUuid() == teamManager.getPlayerTeam(victim).getUuid()) {
                    player.sendMessage(ColorUtil.CC(lang.getString("FRIENDLY_FIRE")));
                    event.setCancelled(true);
                }else if (teamManager.getPlayerTeam(player).getAllies().contains(teamManager.getPlayerTeam(victim).getUuid())){
                    player.sendMessage(ColorUtil.CC(lang.getString("FRIENDLY_FIRE")));
                    event.setCancelled(true);
                }
            }
        }
    }
}
