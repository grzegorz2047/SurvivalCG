package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzeg on 26.12.2015.
 */
public class PlayerDeathListener implements Listener {

    private final SCG plugin;

    public PlayerDeathListener(SCG plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    void onPlayerDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
        Player victim = e.getEntity();

        String victimname = victim.getName();
        User victimuser = plugin.getManager().getUserManager().getUsers().get(victimname);

        int points = 0;
        points = victimuser.getPoints()/100;


        victimuser.setDeaths(victimuser.getDeaths() + 1);

        boolean canGivePoints = false;

        if (victim.getKiller() == null) {
            plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(victimuser);
        } else {
            User killeruser = plugin.getManager().getUserManager().getUsers().get(victim.getKiller().getName());
            killeruser.setKills(killeruser.getKills() + 1);
            if (!killeruser.getLastKilledPlayer().equals(victimname)) {
                killeruser.setLastKilledPlayer(victimname);
                if (victimuser.getPoints() > points) {
                    canGivePoints = true;
                }
            } else {
                canGivePoints = false;

            }
            if (canGivePoints) {
                victimuser.setPoints(victimuser.getPoints() - points);
                killeruser.setPoints(killeruser.getPoints() + points);
                plugin.getManager().getRankingManager().checkUserpoints(killeruser.getUsername(), killeruser);
                plugin.getManager().getRankingManager().checkUserpoints(victimuser.getUsername(), victimuser);
            }
            Bukkit.broadcastMessage(plugin.getManager().getMsgManager().
                    getMsg("killerkilledvictim").
                    replace("{VICTIM}", victimname).
                    replace("{KILLER}", killeruser.getUsername()).
                    replace("{VICTIMLOSE}", "-" + points).
                    replace("{KILLEREARN}", "+" + points));

            plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(victimuser);
            plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(killeruser);

        }
        if (victim.hasPermission("scg.hardcore.bypass")) {
            e.getEntity().sendMessage(plugin.getManager().getMsgManager().getMsg("bypasshcban"));
            return;
        } else {
            victimuser.setToBan(true);
        }
        victim.getWorld().strikeLightningEffect(victim.getLocation());


    }


}
