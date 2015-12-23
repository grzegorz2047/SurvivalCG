package pl.grzegorz2047.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.grzegorz2047.survivalcg.SurvivalCG;
import pl.grzegorz2047.survivalcg.managers.TeleportManager;
import pl.grzegorz2047.survivalcg.teleport.TeleportRequest;

/**
 * Created by grzeg on 23.12.2015.
 */
public class GeneralTask implements Runnable {

    SurvivalCG plugin;

    public GeneralTask(SurvivalCG plugin){
        this.plugin = plugin;
    }


    @Override
    public void run() {
        plugin.getTeleportManager().checkRequests();
    }
}
