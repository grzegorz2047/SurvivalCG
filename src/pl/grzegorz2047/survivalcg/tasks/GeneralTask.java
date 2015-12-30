package pl.grzegorz2047.survivalcg.tasks;

import org.bukkit.Bukkit;
import pl.grzegorz2047.api.util.ColoringUtil;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.managers.MsgManager;

/**
 * Created by grzeg on 23.12.2015.
 */
public class GeneralTask implements Runnable {

    private SCG plugin;

    public GeneralTask(SCG plugin){
        this.plugin = plugin;
    }

    int seconds = 0;
    @Override
    public void run() {
        plugin.getManager().getTeleportManager().checkRequests();
        plugin.getManager().getAntiLogoutManager().checkFights();
        plugin.getManager().getGuildManager().checkPendingRelationList();

        if(seconds % 30 == 0){
            seconds = 1;
            Bukkit.broadcastMessage(ColoringUtil.colorText(MsgManager.getPrefix()+"Serwer w fazie &ctestowej&7! Oczekuj restartow serwera! Mapa bedzie usunieta po testach!!"));
        }else{
            seconds++;
        }
    }
}
