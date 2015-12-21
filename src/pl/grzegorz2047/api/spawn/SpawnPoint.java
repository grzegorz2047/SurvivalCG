package pl.grzegorz2047.api.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Created by Grzegorz2047. 28.08.2015.
 */
public class SpawnPoint {

    private double pitch;
    private double yaw;
    private double x, y, z;
    private String worldName;
    private boolean free = true;
    private String occupiedBy;

    public SpawnPoint(double x, double y, double z, double pitch, double yaw, String worldName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.worldName = worldName;
    }

    public SpawnPoint(Location loc) {
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
        this.pitch = loc.getPitch();
        this.yaw = loc.getYaw();
        this.worldName = loc.getWorld().getName();
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free, String occupiedBy) {
        this.free = free;
        this.occupiedBy = occupiedBy;
    }

    public void setFree(boolean free) {
        this.free = free;
    }


    public String getOccupiedBy() {
        return occupiedBy;
    }

    public void setOccupiedBy(String occupiedBy) {
        this.occupiedBy = occupiedBy;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(this.worldName), x, y, z);
    }

    public double getPitch() {
        return pitch;
    }

    public double getYaw() {
        return yaw;
    }
}
