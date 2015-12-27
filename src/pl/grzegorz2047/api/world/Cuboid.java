package pl.grzegorz2047.api.world;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Created by Grzegorz2047. 29.09.2015.
 */
public class Cuboid {

    private Location min;
    private Location max;

    private int minX;
    private int minY;
    private int minZ;

    private int maxX;
    private int maxY;
    private int maxZ;

    private World world;

    public Cuboid(Location min, Location max) {
        this.min = min;
        this.max = max;

        this.world = min.getWorld();
        this.minX = Math.min(min.getBlockX(), max.getBlockX());
        this.minY = Math.min(min.getBlockY(), max.getBlockY());
        this.minZ = Math.min(min.getBlockZ(), max.getBlockZ());

        this.maxX = Math.max(min.getBlockX(), max.getBlockX());
        this.maxY = Math.max(min.getBlockY(), max.getBlockY());
        this.maxZ = Math.max(min.getBlockZ(), max.getBlockZ());

        System.out.println("Min: x: " + minX + ", min y: " + minY + ", min z: " + minZ);

        System.out.println("Max: x: " + maxX + ", maax y: " + maxY + ", max z: " + maxZ);

    }

    public Location getMin() {
        return min;
    }

    public void setMin(Location min) {
        this.min = min;
    }

    public Location getMax() {
        return max;
    }

    public void setMax(Location max) {
        this.max = max;
    }

    public void removeBlockInCuboid() {
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block b = world.getBlockAt(x, y, z);
                    b.setType(Material.AIR);//Test?
                }
            }
        }
    }

}
