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
        points = victimuser.getPoints() / 100;


        victimuser.setDeaths(victimuser.getDeaths() + 1);

        boolean canGivePoints = false;

        if (victim.getKiller() == null) {
            plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(victimuser);
        } else {
            User killeruser = plugin.getManager().getUserManager().getUsers().get(victim.getKiller().getName());
            killeruser.setKills(killeruser.getKills() + 1);
            if (!killeruser.getLastKilledPlayer().equals(victimname)) {
                killeruser.setLastKilledPlayer(victimname);
            }

            plugin.getManager().getRankingManager().recountEloFight(killeruser, victimuser);


            plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(victimuser);
            plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(killeruser);

            plugin.getManager().getRankingManager().checkUserpoints();

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
