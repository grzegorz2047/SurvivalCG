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

    float halfSeconds = 0;
    @Override
    public void run() {
        plugin.getManager().getCuboidManager().checkPlayers();
        halfSeconds+= 0.5;
        if(halfSeconds % plugin.getManager().getSettingsManager().getStoneGeneratorDelayTime() == 0){
            plugin.getManager().getStoneGeneratorManager().checkBlocks();
        }
        if(halfSeconds >=5){
            halfSeconds = 0;
        }
    }
}
