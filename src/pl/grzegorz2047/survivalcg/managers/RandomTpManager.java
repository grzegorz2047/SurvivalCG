package pl.grzegorz2047.survivalcg.managers;

import pl.grzegorz2047.survivalcg.teleport.TeleportRequest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import pl.grzegorz2047.survivalcg.SCG;

import java.util.Random;

/**
 * Created by grzegorz2047 on 23.12.2015.
 */
public class RandomTpManager {

    private final SCG plugin;
    private Random r = new Random();

    public RandomTpManager(SCG plugin) {
        this.plugin = plugin;
    }

    public void teleport(Player p, int radius, int baseDistance, boolean force) {
        Material m;
        int x;
        int y;
        int z;
        do{
            boolean negative = r.nextBoolean();
            x = r.nextInt(radius) + baseDistance;
            z = r.nextInt(radius) + baseDistance;
            if (negative) {
                x = -x;
                z = -z;
            }
            y = p.getWorld().getHighestBlockYAt(x, z);
            Block b = p.getWorld().getBlockAt(x,y-2,z);
            m = b.getType();
            //System.out.print("Mat"+m);
        }while(m.equals(Material.STATIONARY_LAVA) || m.equals(Material.STATIONARY_WATER));
        Location dest = new Location(p.getWorld(), x, y, z);
        if(force){
            plugin.getManager().getTeleportManager().getRequests()
                    .add(new TeleportRequest(p.getName(),p.getLocation(),dest,System.currentTimeMillis(),0));
        }else{
            plugin.getManager().getTeleportManager().getRequests()
                    .add(new TeleportRequest(p.getName(),p.getLocation(),dest,System.currentTimeMillis(),5));
        }

        p.sendMessage(plugin.getManager().getMsgManager().getMsg("waitfortp"));
    }

}
