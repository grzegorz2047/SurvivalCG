package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzeg on 26.12.2015.
 */
public class PlayerChatListener implements Listener {

    private final SCG plugin;

    public PlayerChatListener(SCG plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player p = event.getPlayer();
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());
        String message = event.getMessage();

        if (event.getFormat().contains("{GUILD_TAG}")) {
            String tag = user.getGuild().getGuildName();
            String points = String.valueOf(user.getPoints());

            event.setFormat(event.getFormat().replace("{GUILD_TAG}", tag));
            if(event.getFormat().contains("{points}")){
                event.setFormat(event.getFormat().replace("{points}", points));
            }
        }else{

        }
    }

}
