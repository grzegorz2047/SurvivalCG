package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.world.Cuboid;

/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class PlayerBucketListeners implements Listener {

    private final SCG plugin;

    public PlayerBucketListeners(SCG plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void onBucketTake(PlayerBucketFillEvent e) {
        if(e.getPlayer().hasPermission("scg.cuboid.bypassplace")) {
            return;
        }
        Player p = e.getPlayer();
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());
        Guild guild = user.getGuild();
        Cuboid cuboid = user.getCurrentCuboid();
        if (cuboid != null) {
            if (guild != null) {
                if (!user.getGuild().equals(cuboid.getGuild())) {
                    p.sendMessage(plugin.getManager().getMsgManager().getMsg("enemyguildblockplace"));
                    e.setCancelled(true);
                }
            } else {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("enemyguildblockplace"));
                e.setCancelled(true);
            }
            //Bukkit.broadcastMessage("Gracz "+p.getName()+" robi cos na cuboidzie "+cuboid.getGuild().getGuildName());

        } else {
            if (p.getLocation().distance(p.getWorld().getSpawnLocation()) <= plugin.getManager().getSettingsManager().getProtectedSpawnRadius()) {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("spawnplacecantbreak"));
            }
        }
    }
    @EventHandler
    public void onBucketFlow(PlayerBucketEmptyEvent e) {
        if(e.getPlayer().hasPermission("openguild.cuboid.bypassplace")) {
            return;
        }
        Player p = e.getPlayer();
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());
        Guild guild = user.getGuild();
        Cuboid cuboid = user.getCurrentCuboid();
        if (cuboid != null) {
            if (guild != null) {
                if (!user.getGuild().equals(cuboid.getGuild())) {
                    p.sendMessage(plugin.getManager().getMsgManager().getMsg("enemyguildblockplace"));
                    e.setCancelled(true);
                }
            } else {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("enemyguildblockplace"));
                e.setCancelled(true);
            }
            //Bukkit.broadcastMessage("Gracz "+p.getName()+" robi cos na cuboidzie "+cuboid.getGuild().getGuildName());

        } else {
            if (p.getLocation().distance(p.getWorld().getSpawnLocation()) <= plugin.getManager().getSettingsManager().getProtectedSpawnRadius()) {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("spawnplacecantbreak"));
            }
        }
    }

}
