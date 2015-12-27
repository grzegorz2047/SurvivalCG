package pl.grzegorz2047.survivalcg.tasks;

import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzeg on 23.12.2015.
 */
public class GeneralTask implements Runnable {

    private SCG plugin;

    public GeneralTask(SCG plugin){
        this.plugin = plugin;
    }


    @Override
    public void run() {
        plugin.getManager().getTeleportManager().checkRequests();
        plugin.getManager().getAntiLogoutManager().checkFights();
    }
}
