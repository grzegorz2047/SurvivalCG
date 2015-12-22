package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.grzegorz2047.survivalcg.SurvivalCG;
import pl.grzegorz2047.survivalcg.user.SurvUser;

/**
 * Created by grzeg on 22.12.2015.
 */
public class PlayerChatListeners implements Listener {

    private final SurvivalCG plugin;

    public PlayerChatListeners(SurvivalCG plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void handleEvent(AsyncPlayerChatEvent event) {
        if(event.isCancelled()) {
            return;
        }
        Player p = event.getPlayer();
        SurvUser user = plugin.getPlayers().getUsers().get(p.getName());
        String message = event.getMessage();

        if(event.getFormat().contains("%TAG%")) {
            String tag = user.getGroup();
            event.setFormat(event.getFormat().replace("%TAG%", tag));
        }
    }







}
