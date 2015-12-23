package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.grzegorz2047.survivalcg.SurvivalCG;
import pl.grzegorz2047.survivalcg.teleport.TeleportRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzeg on 23.12.2015.
 */
public class TeleportManager {

    private final SurvivalCG plugin;
    private List<TeleportRequest> requests = new ArrayList<TeleportRequest>();

    public TeleportManager(SurvivalCG plugin) {
        this.plugin = plugin;
    }


    public void checkRequests(){
        for(int i = 0; i < requests.size(); i++){
            TeleportRequest r = requests.get(i);
            if(r.getTeleportTime()<= System.currentTimeMillis()){
                requests.remove(r);
                Player requester = Bukkit.getPlayer(r.getRequester());
                if(requester != null){
                    //System.out.print("Odleglosc: "+requester.getLocation().distance(r.getSource()));
                    if(requester.getLocation().distance(r.getSource())<1){
                        requester.teleport(r.getDestination());
                        requester.sendMessage(plugin.getPrefix()+"Zostales pomyslnie przeteleportowany!");
                    }else{
                        requester.sendMessage(plugin.getPrefix()+"Teleportacja zostala odrzucona z powodu poruszenia sie!");
                    }

                }
            }
        }
    }

    public List<TeleportRequest> getRequests() {
        return requests;
    }
}
