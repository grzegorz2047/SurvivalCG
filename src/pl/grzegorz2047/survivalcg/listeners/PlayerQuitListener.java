package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.managers.Fight;

/**
 * Created by grzeg on 26.12.2015.
 */
public class PlayerQuitListener implements Listener {

    private final SCG plugin;

    public PlayerQuitListener(SCG plugin){
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage("");
        Player p = e.getPlayer();

        Fight f = plugin.getManager().getAntiLogoutManager().getFightList().get(p.getName());
        if (f != null) {
            p.damage(30);
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("logoutduringfight"));
            if (!f.getAttacker().equals(p.getName())) {
                Player attacker = Bukkit.getPlayer(f.getAttacker());
                if (attacker != null) {
                    attacker.sendMessage(plugin.getManager().getMsgManager().getMsg("playerlogoutduringfight").replace("{PLAYER}",p.getName()));

                }
            }
        }

        plugin.getManager().getUserManager().removeUser(p.getName());
    }

}
