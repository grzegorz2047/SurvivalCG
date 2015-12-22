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

    public PlayerChatListeners(SurvivalCG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player p = event.getPlayer();
        SurvUser user = plugin.getPlayers().getUsers().get(p.getName());
        String message = event.getMessage();

        if (event.getFormat().contains("&GUILD_TAG&")) {
            String tag = user.getGroup();
            String points = String.valueOf(user.getPoints());

            event.setFormat(event.getFormat().replace("&GUILD_TAG&", tag));
            if(event.getFormat().contains("&points&")){
                event.setFormat(event.getFormat().replace("&points&", points));
            }
        }
    }


}
