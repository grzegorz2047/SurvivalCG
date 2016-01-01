package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.world.Cuboid;

/**
 * Created by grzegorz2047 on 29.12.2015.
 */
public class PlayerPlaceListener implements Listener {

    private final SCG plugin;

    public PlayerPlaceListener(SCG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerPlace(BlockPlaceEvent e) {
       // if (e.getBlock().getType().equals(Material.ENDER_STONE)){
       //     e.getPlayer().sendMessage("Jeszcze nie aktywne!");
       //     e.setCancelled(true);
       //     return;
       // }


        Location loc = e.getBlock().getLocation();
        Block b = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ());
        if (e.getBlock().getType().equals(Material.STONE)) {
            if (b.getType().equals(Material.SPONGE)) {
                e.setCancelled(true);
                return;
            }
        } else if (e.getBlock().getType().equals(Material.ENDER_STONE)) {
            Block bup = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());
            if (b.getType().equals(Material.SPONGE)) {
                e.setCancelled(true);
                return;
            }
            bup.setType(Material.STONE);
        }
        if(e.getPlayer().isOp()){
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
                e.setCancelled(true);
            }
        }
    }

}
