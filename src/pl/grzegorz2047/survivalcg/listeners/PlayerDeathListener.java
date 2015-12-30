package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.api.util.ColoringUtil;
import pl.grzegorz2047.survivalcg.SCG;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by grzeg on 26.12.2015.
 */
public class PlayerDeathListener implements Listener {

    private final SCG plugin;

    public PlayerDeathListener(SCG plugin){
        this.plugin = plugin;
    }


    @EventHandler
    void onPlayerDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
        Player victim = e.getEntity();
        String victimname = victim.getName();
        User victimuser = plugin.getManager().getUserManager().getUsers().get(victimname);
        int points = plugin.getManager().getSettingsManager().getPoints();
        victimuser.setDeaths(victimuser.getDeaths() + 1);
        boolean canGivePoints = false;
        if (victim.getKiller() == null) {
            plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(victimuser);
            return;
        }else{
            User killeruser = plugin.getManager().getUserManager().getUsers().get(victim.getKiller().getName());
            killeruser.setDeaths(killeruser.getDeaths()+1);
            if(!killeruser.getLastKilledPlayer().equals(victimname)){
                killeruser.setLastKilledPlayer(victimname);
                if(victimuser.getPoints()> points){
                    canGivePoints = true;
                }
            }else {
                canGivePoints = false;
            }
            if(canGivePoints){
                victimuser.setPoints(victimuser.getPoints()-points);
                killeruser.setPoints(killeruser.getPoints()+points);
            }
            plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(victimuser);
            plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(killeruser);

        }
        if(victim.hasPermission("scg.hardcore.bypass")) {
            return;
        }else{
            victimuser.setToBan(true);
        }


    }


}
