package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.grzegorz2047.api.util.ColoringUtil;
import pl.grzegorz2047.survivalcg.SCG;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Grzegorz2047. 30.08.2015.
 */
public class MsgManager {

    private final SCG plugin;

    public MsgManager(SCG plugin){
        this.plugin = plugin;
        String lang = plugin.getManager().getSettingsManager().getLang();
        FileConfiguration msgConfig = this.loadFile("messages_"+lang.toLowerCase());
        if(msgConfig != null){
            loadMessages(msgConfig);
            prefix = plugin.getManager().getSettingsManager().getPrefix();
        }else{
            Bukkit.getLogger().warning("Plugin returned null when loading "+"messages_"+lang.toLowerCase()+".yml !");
        }


    }

    protected Map<String, String> messages = new HashMap<String, String>();

    private static boolean debugging = false;



    public static boolean isDebugging() {
        return debugging;
    }

    public static void setDebugging(boolean debugging) {
        MsgManager.debugging = debugging;
    }

    protected static String prefix;

    public static String msg(String msg) {
        String message = ColoringUtil.colorText(prefix + msg);

        return message;
    }
    public static String noprefmsg(String msg) {
        String message = ColoringUtil.colorText(msg);

        return message;
    }
    public static void debug(String msg) {
        if (MsgManager.debugging) {
            Bukkit.broadcastMessage(ColoringUtil.colorText(msg));
            //Bukkit.getLogger().log(Level.WARNING,msg);
        }
    }

    public static String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        MsgManager.prefix = prefix;
    }

    public String getMsg(String msg) {
        String r = this.messages.get(msg);
        if (r != null) {
            return MsgManager.msg(r);
        }
        System.out.println("Brak wiadomosci w "+msg);
        return "Brak takiej wiadomosci!";

    }
    public String getNoPrefMsg(String msg) {
        String r = this.messages.get(msg);
        if (r != null) {
            return MsgManager.noprefmsg(r);
        }
        System.out.println("Brak wiadomosci w "+msg);
        return "Brak takiej wiadomosci!";

    }
    public FileConfiguration loadFile(String name) {
        Bukkit.getLogger().info("Validating file '" + name + ".yml ...");

        YamlConfiguration c = new YamlConfiguration();
        try {
            File file = new File("plugins/"+plugin.getName()+"/" + name + ".yml");
            if(!file.exists()) {
                Bukkit.getLogger().info("File plugins/"+plugin.getName()+"/" + name + ".yml does not exists - creating ...");
                file.createNewFile();
            }

            c.load(file);

            YamlConfiguration configInside = new YamlConfiguration();

            if(plugin.getResource(name + ".yml") == null) {
                Bukkit.getLogger().info("File " + name + ".yml does not exists - skipping ...");
                file.delete();
                return null;
            }

            configInside.load(plugin.getResource(name + ".yml"));

            for(String k : configInside.getKeys(true)) {
                if(!c.contains(k)) {
                    c.set(k, configInside.get(k));
                }
            }

            c.save(file);
            return  configInside;
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void loadMessages(FileConfiguration config) {
        messages = new HashMap<String, String>();
        for(String path : config.getConfigurationSection("").getKeys(false)) {
            messages.put(path, config.getString(path).replace("&", "§"));
        }
    }
}
