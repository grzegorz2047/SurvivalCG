package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.tasks.GeneralTask;
import pl.grzegorz2047.survivalcg.tasks.MoveCheckTask;

/**
 * Created by grzeg on 26.12.2015.
 */
public class TaskManager {
    private final SCG plugin;
    private BukkitTask generalBukkitTask;
    private BukkitTask moveCheckTask;

    public TaskManager(SCG plugin){
        this.plugin = plugin;
        generalBukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, new GeneralTask(plugin), 0, 20);
        moveCheckTask = Bukkit.getScheduler().runTaskTimer(plugin, new MoveCheckTask(plugin), 0, 10);
    }


    public BukkitTask getGeneralBukkitTask(){
        return this.generalBukkitTask;
    }

    public void dispose() {
        this.generalBukkitTask.cancel();
        this.moveCheckTask.cancel();
    }
}
