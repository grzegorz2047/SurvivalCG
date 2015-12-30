package pl.grzegorz2047.survivalcg;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.grzegorz2047.survivalcg.commands.admin.AdminCommand;
import pl.grzegorz2047.survivalcg.commands.drop.DropCommand;
import pl.grzegorz2047.survivalcg.commands.guild.GuildCommand;
import pl.grzegorz2047.survivalcg.commands.help.HelpCommand;
import pl.grzegorz2047.survivalcg.commands.spawn.SpawnCommand;
import pl.grzegorz2047.survivalcg.commands.vip.VIPCommand;
import pl.grzegorz2047.survivalcg.commands.worldspawn.WorldSpawnCommand;
import pl.grzegorz2047.survivalcg.listeners.*;
import pl.grzegorz2047.survivalcg.managers.Manager;


/**
 * Created by grzegorz on 26.12.2015.
 */
public class SCG extends JavaPlugin {

    private Manager manager;

    //TODO Daj gdzies ten random tp         plugin.getRandomTpManager().teleport(p,1500,500, false);


    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.manager = new Manager(this);
        this.manager.initiateManagers();
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        manager.disposeManager();
    }

    public Manager getManager() {
        return manager;
    }

    public void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerChatListener(this), this);
        pm.registerEvents(new PlayerDamagePlayerListener(this), this);
        pm.registerEvents(new PlayerDeathListener(this), this);
        pm.registerEvents(new PlayerJoinListener(this), this);
        pm.registerEvents(new PlayerQuitListener(this), this);
        pm.registerEvents(new PlayerTeleportListener(this), this);
        pm.registerEvents(new PlayerPlaceListener(this), this);
        pm.registerEvents(new PlayerBlockBreakListener(this), this);
        pm.registerEvents(new PlayerRespawnListener(this), this);
        pm.registerEvents(new PlayerLoginListener(this), this);
        pm.registerEvents(new PlayerKickListener(this), this);
        pm.registerEvents(new PlayerBucketListeners(this), this);
    }

    public void registerCommands() {
        this.getCommand("guild").setExecutor(new GuildCommand("guild", new String[]{"g", "gildia", "guild", "druzyna", "team"}, this));
        this.getCommand("vip").setExecutor(new VIPCommand("vip", new String[]{"vip", "extra", "support", "donator"}, this));
        this.getCommand("worldspawn").setExecutor(new WorldSpawnCommand("worldspawn", new String[]{"worldspawn", "spawn"}, this));
        this.getCommand("drop").setExecutor(new DropCommand("drop", this));
        this.getCommand("admin").setExecutor(new AdminCommand("admin", new String[]{"admin"}, this));
        this.getCommand("spawn").setExecutor(new SpawnCommand("spawn", this));
        this.getCommand("pomoc").setExecutor(new HelpCommand("pomoc", new String[]{"pomoc"}, this));
    }

}
