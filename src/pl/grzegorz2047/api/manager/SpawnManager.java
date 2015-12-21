package pl.grzegorz2047.api.manager;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import pl.grzegorz2047.api.exception.IncorrectDataStringException;
import pl.grzegorz2047.api.file.YmlFileHandler;
import pl.grzegorz2047.api.spawn.SpawnPoint;
import pl.grzegorz2047.api.util.LocationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Grzegorz2047. 25.09.2015.
 */
public class SpawnManager {


    private CoreManager coreManager;
    HashMap<String, SpawnPoint> spawns = new HashMap<String, SpawnPoint>();

    public SpawnManager(CoreManager coreManager) {
        this.coreManager = coreManager;
    }


    public HashMap<String, SpawnPoint> getSpawns() {
        return spawns;
    }

    public void saveSpawnsToFile(YmlFileHandler file) {
        file.load();
        FileConfiguration config = file.getConfig();
        List<String> spawnNameList = new ArrayList<String>();
        for (Map.Entry<String, SpawnPoint> sp : this.spawns.entrySet()) {
            spawnNameList.add(sp.getKey());
            String location = LocationUtil.entityLocationToString(sp.getValue().getLocation());
            config.set("spawn." + sp.getKey(), location);
        }
        config.set("spawnnames", spawnNameList);
        file.save();
    }

    public void loadSpawnsFromFile(YmlFileHandler file) {
        file.load();
        FileConfiguration config = file.getConfig();
        List<String> spawnNameList = config.getStringList("spawnnames");
        if (spawnNameList == null) {
            System.out.println("Brak spawnow do wczytania");
        }
        for (String spawn : spawnNameList) {
            String location = config.getString("spawn." + spawn);
            MsgManager.debug("wczytuje spawn list " + spawn);
            try {
                Location loc = LocationUtil.entityStringToLocation(location);
                this.spawns.put(spawn, new SpawnPoint(loc));
            } catch (IncorrectDataStringException e) {
                System.out.print(e.getMessage());
            }
        }
    }


}
