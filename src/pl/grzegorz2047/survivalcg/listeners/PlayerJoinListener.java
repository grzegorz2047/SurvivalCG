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
        User user = plugin.getManager().getUserManager().addUser(username);
        if (!e.getPlayer().hasPlayedBefore()) {
            for (ItemStack item : plugin.getManager().getSettingsManager().getStartItems()) {
                p.getInventory().addItem(item);
            }
            p.teleport(e.getPlayer().getWorld().getSpawnLocation());//Force to spawn World Spawn
        }
        p.addAttachment(plugin, "essentials.essentials.kit", true);
        p.addAttachment(plugin, "essentials.essentials.kits.gracz", true);
        p.addAttachment(plugin, "essentials.essentials.tpa", true);
        p.addAttachment(plugin, "essentials.essentials.tpa.accept", true);
        p.addAttachment(plugin, "essentials.essentials.tpa.deny", true);
        if (p.hasPermission(Permission.PERMISSIONS_VIP)) {
            p.addAttachment(plugin, "essentials.repair", true);
            p.addAttachment(plugin, "essentials.essentials.kits.vip", true);
        }
        /*Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
            public void run(){
            }
        }, 3);*/
        plugin.getManager().getTabManager().addPlayer(p);
        if(user.getGuild() != null){
            p.setScoreboard(user.getGuild().getGuildScoreboard());
        }else {
            p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }


//other code


}
