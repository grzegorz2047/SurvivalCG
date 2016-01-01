package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import pl.grzegorz2047.survivalcg.SCG;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzegorz2047 on 31.12.2015.
 */
public class StoneGeneratorManager {

    private final SCG plugin;
    private List<BlockState> blocks = new ArrayList<>();

    public StoneGeneratorManager(SCG plugin){
        this.plugin = plugin;
    }

    public List<BlockState> getBlocks() {
        return blocks;
    }

    public void checkBlocks() {
        for(int i = 0; i < blocks.size();i++){
            //System.out.println("Zamieniam block "+loc.getBlock().getType()+" na  stone");
            BlockState loc = blocks.get(i);
            loc.setType(Material.STONE);
            loc.update(true);
            blocks.remove(loc);
        }
    }
}
