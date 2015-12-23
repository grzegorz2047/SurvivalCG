package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import pl.grzegorz2047.survivalcg.SurvivalCG;
import pl.grzegorz2047.survivalcg.managers.Fight;
import pl.grzegorz2047.survivalcg.user.SurvUser;

/**
 * Created by grzeg on 22.12.2015.
 */
public class PlayerDamagingOtherListeners implements Listener {

    private final SurvivalCG plugin;

    public PlayerDamagingOtherListeners(SurvivalCG plugin) {
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
                SurvUser victimuser = plugin.getPlayers().getUsers().get(attacked.getName());
                SurvUser attackeruser = plugin.getPlayers().getUsers().get(attacker.getName());

                if (victimuser.getGroup().equalsIgnoreCase(attackeruser.getGroup())) {
                    attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNie mozesz uderzyc gracza swojej gildii"));
                    event.setCancelled(true);
                    return;
                }
                if(event.getDamager().getLocation().distance(event.getDamager().getWorld().getSpawnLocation()) < 80){
                    return;
                }
                Fight vf = plugin.getAntiLogoutManager().getFightList().get(attacked.getName());
                Fight af = plugin.getAntiLogoutManager().getFightList().get(attacker.getName());
                if (vf == null) {
                    vf = new Fight(attacker.getName(), attacked.getName(), System.currentTimeMillis());
                    plugin.getAntiLogoutManager().getFightList().put(attacked.getName(), vf);
                    attacked.sendMessage(plugin.getPrefix() + ChatColor.BOLD+"Jestes w trakcie walki! Musisz poczekac okolo " + vf.getCooldown() + " sekund, aby moc wyjsc z serwera!");
                } else {
                    vf.setAttacker(attacker.getName());
                    vf.setVictim(attacked.getName());
                    vf.setLastHitTime(System.currentTimeMillis());
                }
                if (af == null) {
                    af = new Fight(attacker.getName(), attacked.getName(), System.currentTimeMillis());
                    plugin.getAntiLogoutManager().getFightList().put(attacker.getName(), af);
                    attacker.sendMessage(plugin.getPrefix() + ChatColor.BOLD+"Jestes w trakcie walki! Musisz poczekac okolo" + af.getCooldown() + " sekund, aby moc wyjsc z serwera!");
                } else {
                    af.setAttacker(attacker.getName());
                    af.setVictim(attacked.getName());
                    af.setLastHitTime(System.currentTimeMillis());
                }

            }
        } else if (event.getDamager() instanceof Arrow) {
            if (event.getEntity() instanceof Player) {
                Player attacked = (Player) event.getEntity();
                ProjectileSource attackerEntity = ((Arrow) event.getDamager()).getShooter();

                if (attackerEntity instanceof Player) {
                    Player attacker = (Player) attackerEntity;

                    SurvUser victimuser = plugin.getPlayers().getUsers().get(attacked.getName());
                    SurvUser attackeruser = plugin.getPlayers().getUsers().get(attacker.getName());

                    if (victimuser.getGroup().equalsIgnoreCase(attackeruser.getGroup())) {
                        attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNie mozesz uderzyc gracza swojej gildii"));
                        event.setCancelled(true);
                        return;
                    }
                    if(event.getDamager().getLocation().distance(event.getDamager().getWorld().getSpawnLocation()) < 80){
                        return;
                    }
                    Fight vf = plugin.getAntiLogoutManager().getFightList().get(attacked.getName());
                    Fight af = plugin.getAntiLogoutManager().getFightList().get(attacker.getName());
                    if (vf == null) {
                        vf = new Fight(attacker.getName(), attacked.getName(), System.currentTimeMillis());
                        plugin.getAntiLogoutManager().getFightList().put(attacked.getName(), vf);
                        attacked.sendMessage(plugin.getPrefix() + ChatColor.BOLD+"Jestes w trakcie walki! Musisz poczekac okolo " + vf.getCooldown() + " sekund, aby moc wyjsc z serwera!");
                    } else {
                        vf.setAttacker(attacker.getName());
                        vf.setVictim(attacked.getName());
                        vf.setLastHitTime(System.currentTimeMillis());
                    }
                    if (af == null) {
                        af = new Fight(attacker.getName(), attacked.getName(), System.currentTimeMillis());
                        plugin.getAntiLogoutManager().getFightList().put(attacker.getName(), af);
                        attacker.sendMessage(plugin.getPrefix() +ChatColor.BOLD+ "Jestes w trakcie walki! Musisz poczekac okolo" + af.getCooldown() + " sekund, aby moc wyjsc z serwera!");
                    } else {
                        af.setAttacker(attacker.getName());
                        af.setVictim(attacked.getName());
                        af.setLastHitTime(System.currentTimeMillis());
                    }
                }
            }
        } else if (event.getDamager() instanceof Snowball) {
            if (event.getEntity() instanceof Player) {
                Player attacked = (Player) event.getEntity();
                ProjectileSource attackerEntity = ((Snowball) event.getDamager()).getShooter();

                if (attackerEntity instanceof Player) {
                    Player attacker = (Player) attackerEntity;

                    SurvUser victimuser = plugin.getPlayers().getUsers().get(attacked.getName());
                    SurvUser attackeruser = plugin.getPlayers().getUsers().get(attacker.getName());

                    if (victimuser.getGroup().equalsIgnoreCase(attackeruser.getGroup())) {
                        attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNie mozesz uderzyc gracza swojej gildii"));
                        event.setCancelled(true);
                        return;
                    }
                    if(event.getDamager().getLocation().distance(event.getDamager().getWorld().getSpawnLocation()) < 80){
                        return;
                    }
                    Fight vf = plugin.getAntiLogoutManager().getFightList().get(attacked.getName());
                    Fight af = plugin.getAntiLogoutManager().getFightList().get(attacker.getName());
                    if (vf == null) {
                        vf = new Fight(attacker.getName(), attacked.getName(), System.currentTimeMillis());
                        plugin.getAntiLogoutManager().getFightList().put(attacked.getName(), vf);
                        attacked.sendMessage(plugin.getPrefix() + ChatColor.BOLD+"Jestes w trakcie walki! Musisz poczekac okolo " + vf.getCooldown() + " sekund, aby moc wyjsc z serwera!");
                    } else {
                        vf.setAttacker(attacker.getName());
                        vf.setVictim(attacked.getName());
                        vf.setLastHitTime(System.currentTimeMillis());
                    }
                    if (af == null) {
                        af = new Fight(attacker.getName(), attacked.getName(), System.currentTimeMillis());
                        plugin.getAntiLogoutManager().getFightList().put(attacker.getName(), af);
                        attacker.sendMessage(plugin.getPrefix() + ChatColor.BOLD+"Jestes w trakcie walki! Musisz poczekac okolo" + af.getCooldown() + " sekund, aby moc wyjsc z serwera!");
                    } else {
                        af.setAttacker(attacker.getName());
                        af.setVictim(attacked.getName());
                        af.setLastHitTime(System.currentTimeMillis());
                    }
                }
            }
        }
    }

}
