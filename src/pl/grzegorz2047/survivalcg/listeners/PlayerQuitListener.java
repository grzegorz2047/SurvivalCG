package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.api.util.ColoringUtil;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.Fight;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());
        if(user.isToBan()){
            user.setToBan(false);
            plugin.getManager().getDeathManager().banPlayer(p.getName(), plugin.getManager().getSettingsManager().getHcBanTime());
            long bantime = plugin.getManager().getSettingsManager().getHcBanTime() + System.currentTimeMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = new Date(bantime);
            String msg =  plugin.getManager().getSettingsManager().getHcKickMsg().replace("{TIME}", dateFormat.format(date));
            p.kickPlayer(ColoringUtil.colorText(msg));
        }
        Team noGuild = this.plugin.getManager().getScoreboardTagsManager().getMainScoreBoard().getTeam("noguild");
        noGuild.removeEntry(p.getName());
        plugin.getManager().getUserManager().removeUser(p.getName());
        plugin.getManager().getTabManager().removePlayer(p);

    }

}
