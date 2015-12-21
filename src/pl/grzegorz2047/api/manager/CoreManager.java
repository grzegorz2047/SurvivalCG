package pl.grzegorz2047.api.manager;

import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.chests.ChestManager;
//import pl.grzegorz2047.levelspvp.LevelsPVP;

/**
 * Created by Grzegorz2047. 24.09.2015.
 */
public class CoreManager {

    protected Plugin plugin;
    protected String pluginPrefix;
    protected String mapPath;

    protected MinigameManager minigameManager;
    protected WorldManager worldManager;
    protected ChestManager chestManager;
    protected MsgManager msgManager;
    protected SpawnManager spawnManager;
    protected int numberOfMaps;

    private CoreManager() {
    }

    public CoreManager(Plugin plugin, String pluginPrefix, String mapPath, int numberOfMaps) {
        this.plugin = plugin;
        this.pluginPrefix = pluginPrefix;
        this.mapPath = mapPath;
        this.numberOfMaps = numberOfMaps;

        this.minigameManager = new MinigameManager(this);
        this.spawnManager = new SpawnManager(this);
        this.worldManager = new WorldManager(this, pluginPrefix, mapPath, numberOfMaps);
        this.chestManager = new ChestManager(this);
        this.msgManager = new MsgManager(this);
    }

    public MinigameManager getMinigameManager() {
        return minigameManager;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public ChestManager getChestManager() {
        return chestManager;
    }

    public MsgManager getMsgManager() {
        return msgManager;
    }

    public SpawnManager getSpawnManager() {
        return spawnManager;
    }


    public void resetManagers(/*LevelsPVP levels*/) {
        //First do cleanup methods?
        this.minigameManager = new MinigameManager(this);
        this.spawnManager = new SpawnManager(this);
        this.worldManager = new WorldManager(this, pluginPrefix, mapPath, numberOfMaps);
        this.chestManager = new ChestManager(this);
        this.msgManager = new MsgManager(this);

    }

    public Plugin getPlugin() {
        return this.plugin;
    }


}
