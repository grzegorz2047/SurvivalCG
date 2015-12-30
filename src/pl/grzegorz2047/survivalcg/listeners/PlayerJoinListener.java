package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.mcsg.double0negative.tabapi.TabAPI;
import pl.grzegorz2047.api.permission.Permission;
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
        if (!e.getPlayer().hasPlayedBefore()) {
            for (ItemStack item : plugin.getManager().getSettingsManager().getStartItems()) {
                p.getInventory().addItem(item);
            }
            p.teleport(e.getPlayer().getWorld().getSpawnLocation());//Force to spawn World Spawn
        }
        if (p.hasPermission(Permission.PERMISSIONS_VIP)) {
            p.addAttachment(plugin, "essentials.repair", true);
        }

        TabAPI.setPriority(plugin, p, 0);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                updateTab(p);
            }
        }, 3);


    }

    public void playerJoin(Player p) { //add player to the game, set their priority here
        TabAPI.setPriority(plugin, p, 2);
        TabAPI.updatePlayer(p); //always good to update the player after priority change so that the plugin with priority gets displayed
//other code
    }

    public void updateTab(Player p) { //update the tab for a player
        TabAPI.setTabString(plugin, p, 0, 0, "players");
        TabAPI.setTabString(plugin, p, 0, 1, "alive");
        TabAPI.setTabString(plugin, p, 0, 2, "dead");

        TabAPI.setTabString(plugin, p, 1, 0, "kills");
        TabAPI.setTabString(plugin, p, 1, 1, "aaa");
        TabAPI.setTabString(plugin, p, 2, 0, "deaths");
        TabAPI.setTabString(plugin, p, 1, 1, "daw");

        TabAPI.updatePlayer(p);

    }

    public void removePlayer(Player p) { //player died or left, return priority
        TabAPI.setPriority(plugin, p, -2); //-2 means this plugin isn't using the tab anymore, dont show
        TabAPI.updatePlayer(p); //always good to update the player after priority change so that the plugin with priority gets displayed
    }
//other code


}
