package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.world.Cuboid;

/**
 * Created by grzegorz2047 on 29.12.2015.
 */
public class PlayerBlockBreakListener implements Listener {
    private final SCG plugin;

    public PlayerBlockBreakListener(SCG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerBreak(BlockBreakEvent e) {
        if (e.getBlock().getType().equals(Material.SPONGE)) {
            e.setCancelled(true);
            return;
        }
        if(e.getBlock().getType().equals(Material.ENDER_STONE)){
            e.getBlock().setType(Material.AIR);
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
                }else {//Jezeli stoniarka w dobrym miejscu dostepnym
                    if (e.getBlock().getType().equals(Material.STONE)) {
                        Location loc = e.getBlock().getLocation().subtract(0, 1, 0);
                        Block b = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
                        if (b.getType().equals(Material.ENDER_STONE)) {
                            plugin.getManager().getStoneGeneratorManager().getBlocks().add(e.getBlock().getState());
                            return;
                        }
                    }
                }
            } else {
                if(e.getPlayer().isOp()){
                    return;
                }
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("enemyguildblockplace"));
                e.setCancelled(true);
            }
            //Bukkit.broadcastMessage("Gracz "+p.getName()+" robi cos na cuboidzie "+cuboid.getGuild().getGuildName());

        } else {
            if (e.getBlock().getType().equals(Material.STONE)) {
                Location loc = e.getBlock().getLocation().subtract(0, 1, 0);
                Block b = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
                if (b.getType().equals(Material.ENDER_STONE)) {
                    //p.sendMessage("Pod toba jest ender stone");
                    plugin.getManager().getStoneGeneratorManager().getBlocks().add(e.getBlock().getState());
                    return;
                }else {
                   // p.sendMessage("Pod toba jest "+b.getType());
                }
            }
            if(e.getPlayer().isOp()){
                return;
            }
            if (p.getLocation().distance(p.getWorld().getSpawnLocation()) <= plugin.getManager().getSettingsManager().getProtectedSpawnRadius()) {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("spawnplacecantbreak"));
                e.setCancelled(true);
            }
        }
    }
}
