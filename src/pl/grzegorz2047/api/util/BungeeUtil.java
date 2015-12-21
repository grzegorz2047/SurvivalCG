package pl.grzegorz2047.api.util;

/**
 * Created by Grzegorz2047. 31.08.2015.
 */

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class BungeeUtil {

    public static String lobbyServer = "LobbyLevelsPVP1";

    public static void changeServer(Plugin plugin, Player player, String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void setLobbyServer(String lobbyServer) {
        BungeeUtil.lobbyServer = lobbyServer;
    }

    public static void registerChannels(Plugin plugin) {
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");

    }

}