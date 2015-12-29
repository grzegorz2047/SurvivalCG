package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.grzegorz2047.survivalcg.SCG;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by grzegorz2047 on 29.12.2015.
 */
public class PlayerRespawnListener implements Listener {

    private final SCG plugin;

    public PlayerRespawnListener(SCG plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        final Player player = e.getPlayer();
        if(player.hasPermission("scg.hardcore.bypass")){
            return;
        }

    }

}
