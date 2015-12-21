package pl.grzegorz2047.api.manager;

import org.bukkit.Bukkit;
import pl.grzegorz2047.api.util.ColoringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Grzegorz2047. 30.08.2015.
 */
public class MsgManager {

    private CoreManager coreManager;
    protected Map<String, String> messages = new HashMap<String, String>();

    private static boolean debugging = false;

    public MsgManager(CoreManager coreManager) {
        this.coreManager = coreManager;
    }

    public static boolean isDebugging() {
        return debugging;
    }

    public static void setDebugging(boolean debugging) {
        MsgManager.debugging = debugging;
    }

    protected static String prefix = ColoringUtil.colorText("&7[&cPLUGIN&7] ");

    public static String msg(String msg) {
        String message = ColoringUtil.colorText(prefix + msg);

        return message;
    }

    public static void debug(String msg) {
        if (MsgManager.debugging) {
            Bukkit.broadcastMessage(ColoringUtil.colorText(msg));
            //Bukkit.getLogger().log(Level.WARNING,msg);
        }
    }

    public static String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        MsgManager.prefix = prefix;
    }

    public String getMsg(String msg) {
        String r = this.messages.get(msg);
        if (r != null) {
            return MsgManager.msg(r);
        }
        return "Brak takiej wiadomosci!";

    }

}
