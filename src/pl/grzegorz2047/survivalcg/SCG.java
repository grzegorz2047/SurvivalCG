package pl.grzegorz2047.survivalcg;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.grzegorz2047.survivalcg.commands.guild.GuildCommand;
import pl.grzegorz2047.survivalcg.commands.vip.VIPCommand;
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
    }

    public void registerCommands() {
        this.getCommand("guild").setExecutor(new GuildCommand("guild", new String[]{"g", "gildia", "guild", "druzyna", "team"}, this));
        this.getCommand("vip").setExecutor(new VIPCommand("vip", new String[]{"vip", "extra", "support", "donator"}, this));
    }

}
