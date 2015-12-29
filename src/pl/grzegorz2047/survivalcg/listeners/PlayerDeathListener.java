package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.grzegorz2047.api.user.User;
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
        Player victim = e.getEntity();
        String victimname = victim.getName();
        User victimuser = plugin.getManager().getUserManager().getUsers().get(victimname);
        int points = 10;
        victimuser.setDeaths(victimuser.getDeaths() + 1);
        boolean canGivePoints = false;
        if (victim.getKiller() == null) {
            plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(victimuser);
            return;
        }else{
            User killeruser = plugin.getManager().getUserManager().getUsers().get(victim.getKiller().getName());
            if(!killeruser.getLastKilledPlayer().equals(victimname)){
                killeruser.setLastKilledPlayer(victimname);
            }else {

            }

        }
        plugin.getManager().getDeathManager().banPlayer(victim.getName(), plugin.getManager().getSettingsManager().getHcBanTime());
        long bantime = plugin.getManager().getSettingsManager().getHcBanTime() + System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = new Date(bantime);
        victim.kickPlayer(plugin.getManager().getSettingsManager().getHcKickMsg().replace("{TIME}", dateFormat.format(date)));
    }


}
