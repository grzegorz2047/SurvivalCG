package pl.grzegorz2047.api.world;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

/**
 * Created by Grzegorz2047. 24.09.2015.
 */
public class BlockManipulate {


    public static void removeBlocks(Location first, Location last) {
        Location min = Vector.getMinimum(first.toVector(), last.toVector()).toLocation(first.getWorld());
        Location max = Vector.getMaximum(first.toVector(), last.toVector()).toLocation(last.getWorld());
        for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y < max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
                    Block b = first.getWorld().getBlockAt(x, y, z);
                    System.out.println("Block " + b.getType().toString());
                    b.setType(Material.AIR);//Test?
                }
            }
        }

    }

}
