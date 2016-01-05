package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.world.Cuboid;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by grzeg on 26.12.2015.
 */
public class CuboidManager {

    private final SCG plugin;

    public CuboidManager(SCG plugin) {
        this.plugin = plugin;
    }

    private HashMap<String, Cuboid> cuboids = new HashMap<String, Cuboid>();

    public void checkPlayers() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            User user = plugin.getManager().getUserManager().getUsers().get(p.getName());
            int protspawnrad = plugin.getManager().getSettingsManager().getProtectedSpawnRadius();
            if (p.getLocation().distance(p.getWorld().getSpawnLocation()) < protspawnrad) {
                if (!user.isOnSpawn()) {
                    user.setOnSpawn(true);
                    p.sendMessage(plugin.getManager().getMsgManager().getMsg("playerenterspawnnotify"));
                    user.setCurrentCuboid(null);
                    continue;
                }
            } else {
                if (user.isOnSpawn()) {
                    user.setOnSpawn(false);
                    p.sendMessage(plugin.getManager().getMsgManager().getMsg("playerleavespawnnotify"));
                    user.setCurrentCuboid(null);
                    continue;
                }
            }
            boolean inany = false;
            for (Map.Entry<String, Cuboid> entry : cuboids.entrySet()) {
                boolean entered = entry.getValue().isinCuboid(p.getLocation());
                if (entered) {
                    if (user.getCurrentCuboid() == null) {
                        if (plugin.getManager().getSettingsManager().isCuboidEntryNotify()) {
                            if (plugin.getManager().getSettingsManager().isCuboidEntrySound()) {
                                p.playSound(p.getLocation(), plugin.getManager().getSettingsManager().getCuboidEntrySoundType(), 1, 1);
                                for (Player member : Bukkit.getOnlinePlayers()) {
                                    if (entry.getValue().getGuild().getMembers().contains(member)) {
                                        if (user.getGuild() != null) {
                                            member.sendMessage(plugin.getManager().getMsgManager().getMsg("entercubmems").replace("{GUILD}", user.getGuild().getGuildTag()).replace("{PLAYER}", user.getUsername()));

                                        } else {
                                            member.sendMessage(plugin.getManager().getMsgManager().getMsg("entercubmemsnoguild").replace("{PLAYER}", user.getUsername()));
                                        }
                                        member.playSound(p.getLocation(), plugin.getManager().getSettingsManager().getCuboidEntrySoundType(), 1, 1);
                                    }
                                }
                            }

                        }
                        if (plugin.getManager().getSettingsManager().isCuboidEntrySound()) {
                            p.playSound(p.getLocation(), plugin.getManager().getSettingsManager().getCuboidEntrySoundType(), 1, 1);
                            p.sendMessage(plugin.getManager().getMsgManager().getMsg("entercubpl").replace("{GUILD}", entry.getKey()));
                        }
                    }
                    user.setCurrentCuboid(entry.getValue());
                    inany = true;
                }
            }
            if (!inany) {
                if (user.getCurrentCuboid() != null) {
                    p.sendMessage(plugin.getManager().getMsgManager().getMsg("leavecubpl").replace("{GUILD}", user.getCurrentCuboid().getGuild().getGuildTag()));
                }
                user.setCurrentCuboid(null);
            }
        }

    }

    /**
     * Parameter @param location
     *
     * @return cuboid where loc is in otherwise null if not found
     */
    public Cuboid getRegion(Location loc) {
        Cuboid cuboid = null;
        for (Map.Entry<String, Cuboid> entry : cuboids.entrySet()) {
            if (entry.getValue().isinCuboid(loc)) {
                cuboid = entry.getValue();
            }
        }
        return cuboid;
    }

    public HashMap<String, Cuboid> getCuboids() {
        return cuboids;
    }
}
