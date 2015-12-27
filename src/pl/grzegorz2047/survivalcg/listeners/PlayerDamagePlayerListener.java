package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.managers.Fight;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class PlayerDamagePlayerListener implements Listener {

    private final SCG plugin;

    public PlayerDamagePlayerListener(SCG plugin){
        this.plugin = plugin;
    }


    @EventHandler
    void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getDamager() instanceof Player) {
            if (event.getEntity() instanceof Player) {
                Player attacked = (Player) event.getEntity();
                Player attacker = (Player) event.getDamager();

                if (checkIfGuildMembers(attacker,attacked)) {//jedno sprawdzenie powinno wystarczyc
                    attacker.sendMessage(plugin.getManager().getMsgManager().getMsg("pvpguildmember"));
                    event.setCancelled(true);
                    return;
                }
                int protspawnrad = plugin.getManager().getSettingsManager().getProtectedSpawnRadius();
                if(event.getDamager().getLocation().distance(attacker.getWorld().getSpawnLocation()) < protspawnrad){
                    attacker.sendMessage(plugin.getManager().getMsgManager().getMsg("pvp-protection"));
                    return;
                }
                checkFight(attacker, attacked);

            }
        } else if (event.getDamager() instanceof Arrow) {
            if (event.getEntity() instanceof Player) {
                Player attacked = (Player) event.getEntity();
                ProjectileSource attackerEntity = ((Arrow) event.getDamager()).getShooter();

                if (attackerEntity instanceof Player) {
                    Player attacker = (Player) attackerEntity;
                    if (checkIfGuildMembers(attacker,attacked)) {//jedno sprawdzenie powinno wystarczyc
                        attacker.sendMessage(plugin.getManager().getMsgManager().getMsg("pvpguildmember"));
                        event.setCancelled(true);
                        return;
                    }
                    int protspawnrad = plugin.getManager().getSettingsManager().getProtectedSpawnRadius();
                    if(event.getDamager().getLocation().distance(attacker.getWorld().getSpawnLocation()) < protspawnrad){
                        attacker.sendMessage(plugin.getManager().getMsgManager().getMsg("pvp-protection"));
                        return;
                    }
                    checkFight(attacker, attacked);

                }
            }
        } else if (event.getDamager() instanceof Snowball) {
            if (event.getEntity() instanceof Player) {
                Player attacked = (Player) event.getEntity();
                ProjectileSource attackerEntity = ((Snowball) event.getDamager()).getShooter();

                if (attackerEntity instanceof Player) {
                    Player attacker = (Player) attackerEntity;

                    if (checkIfGuildMembers(attacker,attacked)) {//jedno sprawdzenie powinno wystarczyc
                        attacker.sendMessage(plugin.getManager().getMsgManager().getMsg("pvpguildmember"));
                        event.setCancelled(true);
                        return;
                    }
                    int protspawnrad = plugin.getManager().getSettingsManager().getProtectedSpawnRadius();
                    if(event.getDamager().getLocation().distance(attacker.getWorld().getSpawnLocation()) < protspawnrad){
                        attacker.sendMessage(plugin.getManager().getMsgManager().getMsg("pvp-protection"));
                        return;
                    }
                    checkFight(attacker, attacked);

                }
            }
        }
    }

    public boolean checkIfGuildMembers(Player attacker, Player attacked){
        User victimuser = plugin.getManager().getUserManager().getUsers().get(attacked.getName());
        User attackeruser = plugin.getManager().getUserManager().getUsers().get(attacker.getName());

        //jedno sprawdzenie powinno wystarczyc
        return victimuser.getGuild().equals(attackeruser.getGuild());
    }

    public void checkFight(Player attacker, Player attacked){
        Fight vf = plugin.getManager().getAntiLogoutManager().getFightList().get(attacked.getName());
        Fight af = plugin.getManager().getAntiLogoutManager().getFightList().get(attacker.getName());
        if (vf == null) {
            vf = new Fight(attacker.getName(), attacked.getName(), System.currentTimeMillis());
            plugin.getManager().getAntiLogoutManager().getFightList().put(attacked.getName(), vf);
            attacked.sendMessage(plugin.getManager().getMsgManager().getMsg("player-during-fight").replace("{TIME}",String.valueOf(vf.getCooldown())));
        } else {
            vf.setAttacker(attacker.getName());
            vf.setVictim(attacked.getName());
            vf.setLastHitTime(System.currentTimeMillis());
        }
        if (af == null) {
            af = new Fight(attacker.getName(), attacked.getName(), System.currentTimeMillis());
            plugin.getManager().getAntiLogoutManager().getFightList().put(attacker.getName(), af);
            attacker.sendMessage(plugin.getManager().getMsgManager().getMsg("player-during-fight").replace("{TIME}",String.valueOf(af.getCooldown())));
        } else {
            af.setAttacker(attacker.getName());
            af.setVictim(attacked.getName());
            af.setLastHitTime(System.currentTimeMillis());
        }
    }
}
