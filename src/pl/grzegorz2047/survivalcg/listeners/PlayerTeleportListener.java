package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzeg on 26.12.2015.
 */
public class PlayerTeleportListener implements Listener {

    private final SCG plugin;

    public PlayerTeleportListener(SCG plugin){
        this.plugin = plugin;
    }

    @EventHandler
    void onTeleport(PlayerTeleportEvent e) {
       // if(e.getCause().equals(PlayerTeleportEvent.TeleportCause.PLUGIN)){
       //     Player p = e.getPlayer();
       //
       // }
    }

}
