package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 31.12.2015.
 */
public class PlayerEnchantsListener implements Listener {

    private final SCG plugin;

    public PlayerEnchantsListener(SCG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPrepEnchant(EnchantItemEvent e){
        if(e.getEnchantsToAdd().containsKey(Enchantment.DAMAGE_ALL)){
            e.getEnchantsToAdd().remove(Enchantment.DAMAGE_ALL);
            e.getEnchanter().sendMessage("Zablokowane");
        }
    }

}
