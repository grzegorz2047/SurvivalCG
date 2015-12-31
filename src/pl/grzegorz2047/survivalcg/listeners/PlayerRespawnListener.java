package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.api.util.ColoringUtil;
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
        User user  = plugin.getManager().getUserManager().getUsers().get(player.getName());
        if(user.isToBan()){
            user.setToBan(false);
            plugin.getManager().getDeathManager().banPlayer(player.getName(), plugin.getManager().getSettingsManager().getHcBanTime());
            long bantime = plugin.getManager().getSettingsManager().getHcBanTime() + System.currentTimeMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = new Date(bantime);
            String msg =  plugin.getManager().getSettingsManager().getHcKickMsg().replace("{TIME}", dateFormat.format(date));
            player.kickPlayer(ColoringUtil.colorText(msg));
        }
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
    }

}
