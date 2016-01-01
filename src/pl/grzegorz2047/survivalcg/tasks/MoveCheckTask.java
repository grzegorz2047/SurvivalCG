package pl.grzegorz2047.survivalcg.tasks;

import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzeg on 26.12.2015.
 */
public class MoveCheckTask implements Runnable {

    private final SCG plugin;

    public MoveCheckTask(SCG plugin){
        this.plugin = plugin;

    }

    @Override
    public void run() {
        plugin.getManager().getCuboidManager().checkPlayers();
    }
}
