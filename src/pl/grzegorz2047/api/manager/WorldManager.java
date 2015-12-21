package pl.grzegorz2047.api.manager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.entity.Player;
import pl.grzegorz2047.api.file.YmlFileHandler;
import pl.grzegorz2047.api.util.RandomUtil;
import pl.grzegorz2047.api.world.AirGenerator;

import java.io.File;
import java.io.IOException;

/**
 * Created by Grzegorz2047. 29.08.2015.
 */
public class WorldManager {

    private int numberOfMaps;
    private String defaultToMapName = "Mapa";
    private String prefixMap;
    private String mapPath;
    private String loadedMap;
    private YmlFileHandler config;
    private CoreManager core;

    public WorldManager(CoreManager core, String prefixMap, String mapPath, int numberOfMaps) {
        this.prefixMap = prefixMap;
        this.mapPath = mapPath;//Path where maps are stored
        this.numberOfMaps = numberOfMaps;
        this.core = core;
        loadMinigameWorld();
    }


    public void loadWorld(String worldName) throws IOException {
        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.kickPlayer("Blad serwera. Zglos sie do administracji serwera!");
            }
        }
        File from = new File(mapPath + File.separator + worldName);
        MsgManager.debug("From " + from.getAbsolutePath());

        File to = new File(defaultToMapName);

        if (to.exists()) {
            to.delete();
        }

        FileUtils.copyDirectory(from, to);
        new File(to, "uid.dat").delete();
        new File(to, "session.lock").delete();
        File defaultWorldFile = new File(Bukkit.getWorlds().get(0).getName() + File.separator + "playerdata" + File.separator);
        MsgManager.debug("From " + defaultWorldFile.getAbsolutePath());
        FileUtils.deleteDirectory(defaultWorldFile);
        defaultWorldFile.mkdir();

        WorldCreator creator = new WorldCreator(defaultToMapName);
        creator.environment(World.Environment.NORMAL);
        creator.generateStructures(false);

        creator.generator(new AirGenerator());
        creator.type(WorldType.FLAT);

//World being created again
        World world = Bukkit.getServer().createWorld(creator);
        world.setAutoSave(false);
        world.setDifficulty(Bukkit.getWorlds().get(0).getDifficulty());
        world.setPVP(true);
        world.setMonsterSpawnLimit(1);//Wylacza potwory?
        world.setStorm(false);
        world.setTime(0);
        //world.setGameRuleValue("doDaylightCycle", "false");
        world.setWeatherDuration(180 * 60 * 20);

        //World 0 with some changes
        World w0 = Bukkit.getWorlds().get(0);
        w0.setAutoSave(false);

        for (Chunk chunk : w0.getLoadedChunks()) {
            chunk.unload();
        }
    }

    public void unloadWorld(String worldName) {
        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.kickPlayer(MsgManager.msg("Mapa restartuje sie!"));
            }
        }
        Bukkit.unloadWorld(Bukkit.getWorld(worldName), false);

    }

    public void unloadWorld() {

        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.kickPlayer(MsgManager.msg("Mapa restartuje sie!"));
            }
        }
        if (Bukkit.getWorlds().size() > 1) {
            Bukkit.unloadWorld(Bukkit.getWorlds().get(1), false);//unload second map if loaded
        }


    }

    /* public String getAuthorsString(String authorColor, String color) {
         Validate.notNull(authorColor);
         Validate.notNull(color);
         if (this.getAuthors() == null) {
             return authorColor + Color.ITALIC + "(nieznani)" + Color.RESET;
         } else {
             StringBuilder builder = new StringBuilder();
             for (int i = 0; i < this.getAuthors().length; i++) {
                 if (i != 0) {
                     builder.append(color);
                     if (this.getAuthors().length == (i + 1)) {
                         builder.append(" oraz ");
                     } else {
                         builder.append(", ");
                     }
                 }
                 builder.append(authorColor).append(this.authors[i]);
             }
             return Color.RESET + builder.toString();
         }
     }*/
    public void loadMinigameWorld() {
        Validate.isTrue(numberOfMaps > 0, "Brak Map!!");
        int randomNumber = RandomUtil.get().nextInt(numberOfMaps);
        if (randomNumber == 0) {
            randomNumber++;
        }
        this.unloadWorld();
        this.loadedMap = this.prefixMap + randomNumber;
        try {
            this.loadWorld(this.loadedMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.config = new YmlFileHandler(core.getPlugin(), this.mapPath + File.separator + this.loadedMap, "spawns");//Get map path and load file config
        core.getSpawnManager().loadSpawnsFromFile(config);
    }

    public String getLoadedMap() {
        return loadedMap;
    }

    public String getMapPath() {
        return mapPath;
    }

    public YmlFileHandler getConfig() {
        return config;
    }
}
