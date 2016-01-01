package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.managers.MsgManager;
import pl.grzegorz2047.survivalcg.world.Cuboid;

/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class PlayerInteractListener implements Listener {

    private SCG plugin;

    public PlayerInteractListener(SCG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {




        Player p = e.getPlayer();
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());
        if (e.getClickedBlock() != null) {
            if (e.getClickedBlock().getState() instanceof Sign) {
                Sign s = (Sign) e.getClickedBlock().getState();
                if (s.getLine(0).equalsIgnoreCase("[tp]")) {
                    plugin.getManager().getRandomTpManager().teleport(e.getPlayer(), 1000, 500, true);
                }
            }
            Block iblock = e.getClickedBlock();
            if (iblock.getType().equals(Material.ENDER_CHEST) || iblock.getType().equals(Material.COOKED_BEEF ) || iblock.getType().equals(Material.WORKBENCH)) {
                return;
            }



            Guild guild = user.getGuild();
            Cuboid cuboid = user.getCurrentCuboid();
            if (cuboid != null) {
                if (guild != null) {
                    if (!user.getGuild().equals(cuboid.getGuild())) {
                        if (e.getPlayer().hasPermission("scg.cuboid.bypassinteract")) {
                            Location block = e.getPlayer().getLocation();
                            Bukkit.getLogger().info("Player " + e.getPlayer().getName() + " interacted in guilds cuboid at X:" + block.getBlockX() + ", Y:" + block.getBlockY() +
                                    ", Z:" + block.getBlockZ() + " in world " + block.getWorld().getName() + " (" +
                                    block.getBlock().getType().name() + ")");
                            //e.getPlayer().sendMessage(plugin.getManager().getMsgManager().getMsg("interinguild")
                            // .replace("{WORLD}", block.getWorld().getName())
                            // .replace("{X}", String.valueOf(block.getBlockX()))
                            //  .replace("{Y}", String.valueOf(block.getBlockY()))
                            //  .replace("{Z}", String.valueOf(block.getBlockZ())));
                            return;
                        } else {
                            e.getPlayer().sendMessage(plugin.getManager().getMsgManager().getMsg("cantdoitonsomeonearea"));
                            e.setCancelled(true);
                            return;
                        }
                    }
                } else {
                    p.sendMessage(plugin.getManager().getMsgManager().getMsg("enemyguildblockplace"));
                    e.setCancelled(true);
                }
                //Bukkit.broadcastMessage("Gracz "+p.getName()+" robi cos na cuboidzie "+cuboid.getGuild().getGuildName());

            } else {
                if(e.getPlayer().isOp()){
                    return;
                }
                if (p.getLocation().distance(p.getWorld().getSpawnLocation()) <= plugin.getManager().getSettingsManager().getProtectedSpawnRadius()) {
                    p.sendMessage(plugin.getManager().getMsgManager().getMsg("spawninteractblock"));
                    e.setCancelled(true);
                }
            }

        }
    }


}
