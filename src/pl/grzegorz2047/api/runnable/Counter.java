package pl.grzegorz2047.api.runnable;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.grzegorz2047.api.event.CountdownSecondEvent;
import pl.grzegorz2047.api.event.CounterEndEvent;

import java.util.ArrayList;
import java.util.List;

//import pl.grzegorz2047.levelspvp.manager.LevelsMsgManager;

/**
 * Created by Grzegorz2047. 27.08.2015.
 */
public class Counter extends BukkitRunnable {

    protected static List<Integer> taskids = new ArrayList<Integer>();

    protected int time;
    protected int taskId;
    protected boolean running = false;
    protected Plugin plugin;

    BukkitTask task;

    public Counter(Plugin plugin, int time) {
        this.time = time;
        this.plugin = plugin;
    }


    public void start() {
        if (running) {
            //LevelsMsgManager.debug("Nie mozesz uruchomic countera po raz drugi");
            return;
        }
        this.createTask();
        running = true;

    }

    public void stop() {
        this.cancel();
        this.running = false;
    }

    public void pause() {
        this.running = false;
    }


    private void createTask() {
        task = this.runTaskTimer(plugin, 0, 20l);// async or a sync
        this.taskId = task.getTaskId();
        Counter.taskids.add(taskId);
    }


    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public void run() {
        if (running) {
            this.time--;
            CountdownSecondEvent secondCountEvent = new CountdownSecondEvent(time, this);
            Bukkit.getPluginManager().callEvent(secondCountEvent);//Fires an event and triggers CounterEndListener
            this.time = secondCountEvent.getCurrentTime();//if any modifications
            if (time <= 0) {
                CounterEndEvent event = new CounterEndEvent();
                Bukkit.getPluginManager().callEvent(event);//Fires an event and triggers CounterEndListener
                Counter.taskids.remove((Integer) taskId);
                this.stop();

            }
        }
    }

    public static List<Integer> getTaskids() {
        return taskids;
    }
}
