package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 29.12.2015.
 */
public class PlayerKickListener implements Listener {

    private final SCG plugin;

    public PlayerKickListener(SCG plugin){
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerKick(PlayerKickEvent e){
        plugin.getManager().getAntiLogoutManager().getFightList().remove(e.getPlayer().getName());
    }

}
