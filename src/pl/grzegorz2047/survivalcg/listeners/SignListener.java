package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 31.12.2015.
 */
public class SignListener implements Listener {


    private final SCG plugin;

    public SignListener(SCG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player player = e.getPlayer();

        if (e.getLine(0).equalsIgnoreCase("[tp]") && !player.isOp())
        {
            e.setCancelled(true);
        }
    }

}
