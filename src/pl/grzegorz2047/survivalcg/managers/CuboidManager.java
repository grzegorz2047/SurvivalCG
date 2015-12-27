package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.grzegorz2047.survivalcg.world.Cuboid;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by grzeg on 26.12.2015.
 */
public class CuboidManager {

    private HashMap<String, Cuboid> cuboids = new HashMap<String, Cuboid>();

    public void checkPlayers() {
        for(Player p : Bukkit.getOnlinePlayers()){
            for(Map.Entry<String, Cuboid> entry : cuboids.entrySet()){
                entry.getValue().isinCuboid(p.getLocation());
            }
        }

    }

    /**
     * Parameter @param location
     * @return cuboid where loc is in otherwise null if not found
     */
    public Cuboid getRegion(Location loc){
        Cuboid cuboid = null;
        for(Map.Entry<String, Cuboid> entry : cuboids.entrySet()){
            if(entry.getValue().isinCuboid(loc)){
                cuboid = entry.getValue();
            }
        }
        return cuboid;
    }

    public HashMap<String, Cuboid> getCuboids() {
        return cuboids;
    }
}
