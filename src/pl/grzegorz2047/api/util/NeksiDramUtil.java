package pl.grzegorz2047.api.util;

import dram.CoinsMod.CoinsMod;
import org.bukkit.entity.Player;
import pl.neksi.craftgames.game.ArenaStatus;

/**
 * Created by Grzegorz2047. 25.09.2015.
 */
public class NeksiDramUtil {

    private static boolean activated = false;

    public static void setArenaStatus(ArenaStatus.Status status) {
        if (activated) {
            ArenaStatus.setStatus(status);
        }
    }

    public static void initArenaStatus(int maxPlayers) {
        if (activated) {
            ArenaStatus.initStatus(maxPlayers);
        }
    }

    public static void setPlayers(int numPlayers) {
        if (activated) {
            ArenaStatus.setPlayers(numPlayers);
        }
    }

    public static void setLore(String lore) {
        if (activated) {
            ArenaStatus.setLore(lore);
        }
    }

    public static void changePlayerMoneyWithMultiplier(Player p, int change, boolean info) {
        if (activated) {
            CoinsMod.ChangePlayerMoneyWOMultiplier(p, change, info);
        }
    }
    /*public static void set(){
        if(activated){

        }
    }
    public static void set(){
        if(activated){

        }
    }*/

}
