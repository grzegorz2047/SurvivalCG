package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;

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
        }

    }


}
