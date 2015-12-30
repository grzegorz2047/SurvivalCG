package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.grzegorz2047.api.util.ColoringUtil;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.managers.DeathManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by grzegorz2047 on 29.12.2015.
 */
public class PlayerLoginListener implements Listener {

    private final SCG plugin;

    public PlayerLoginListener(SCG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        DeathManager deathManager = plugin.getManager().getDeathManager();
        if(p.hasPermission("scg.hardcore.bypass")) {
            return;
        }
        long bantime = deathManager.getPlayer(p.getName());
        if (bantime > System.currentTimeMillis()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = new Date(bantime);
            String msg =  plugin.getManager().getSettingsManager().getHcKickMsg().replace("{TIME}", dateFormat.format(date));
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ColoringUtil.colorText(msg));
        }else {
            deathManager.unbanPlayer(p.getName());
        }
    }

}
