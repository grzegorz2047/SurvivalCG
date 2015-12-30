package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzeg on 26.12.2015.
 */
public class PlayerJoinListener implements Listener {

    private final SCG plugin;

    public PlayerJoinListener(SCG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");
        Player p = e.getPlayer();
        String username = p.getName();
        User user = plugin.getManager().getUserManager().addUser(p.getName());
        if(!e.getPlayer().hasPlayedBefore()){
            for(ItemStack item: plugin.getManager().getSettingsManager().getStartItems()){
                p.getInventory().addItem(item);
            }
            p.teleport(e.getPlayer().getWorld().getSpawnLocation());//Force to spawn World Spawn
        }
    }

}
