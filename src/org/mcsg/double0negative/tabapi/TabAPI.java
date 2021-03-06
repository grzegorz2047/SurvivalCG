package org.mcsg.double0negative.tabapi;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.base.Charsets;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * TabAPI
 *
 * Provides a simple interface for adding custom text to display on the minecraft tab menu on a player/plugin basis
 *
 * @author Double0negative
 *
 *
 */
public class TabAPI implements Listener {


    public TabAPI(SCG scg){
        TabAPI.scg = scg;
    }

    private static HashMap<String, TabObject> playerTab = new HashMap<String, TabObject>();
    private static HashMap<String, TabHolder> playerTabLast = new HashMap<String, TabHolder>();
    private static HashMap<String, TabObject47> playerTab47 = new HashMap<String, TabObject47>();
    private static HashMap<String, TabHolder47> playerTabLast47 = new HashMap<String, TabHolder47>();
    private static HashMap<Player, ArrayList<PacketContainer>> cachedPackets = new HashMap<Player, ArrayList<PacketContainer>>();
    private static HashMap<Player, Integer> updateSchedules = new HashMap<Player, Integer>();
    private static int horizTabSize = 3;
    private static int vertTabSize = 20;
    private static int horizTabSize47 = 4;
    private static int vertTabSize47 = 20;
    private static String[] colors =
    {
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    };
    private static int e = 0;
    private static int r = 0;
    private static long flickerPrevention = 5L;
    public static ProtocolManager protocolManager;
    private static boolean shuttingdown = false;
    private static SCG scg;


    public void enable()
    {
        protocolManager = ProtocolLibrary.getProtocolManager();
        Bukkit.getServer().getPluginManager().registerEvents(this, scg);
        for (Player p : Bukkit.getOnlinePlayers())
        {
            Plugin plugin = scg;
            setPriority(plugin, p, 2);
            resetTabList(p);
            setPriority(plugin, p, -2);
        }
        protocolManager.addPacketListener(new PacketAdapter(
                scg,
                ListenerPriority.NORMAL,
                PacketType.Play.Server.PLAYER_INFO) {
                    @Override
                    public void onPacketSending(PacketEvent event)
                    {
                        PacketContainer p = event.getPacket();
                        String s = p.getStrings().read(0);
                        if (s.startsWith("$"))
                        {  // this is a packet sent by TabAPI **Work around until I figure out how to make my own
                            p.getStrings().write(0, s.substring(1));  // packets bypass this block**
                            event.setPacket(p);
                        }
                        else
                        {
                            if (protocolManager.getProtocolVersion(event.getPlayer()) >= 47)
                            {
                                // send skins and entity normally in protocol 47
                            }
                            else
                            {
                                event.setCancelled(true);
                            }
                        }
                    }
                });
    }


    public void disable()
    {
        shuttingdown = true;
        for (Player p : Bukkit.getOnlinePlayers())
        {
            clearTab(p);
        }
        flushPackets();
        playerTab = null;
        playerTabLast = null;
        playerTab47 = null;
        playerTabLast47 = null;
    }

    private static void addPacket(Player p, String msg, int slotId, WrappedGameProfile gameProfile, boolean b, int ping)
    {
        PacketContainer message = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
        String nameToShow = ((!shuttingdown) ? "$" : "") + msg;
        if (protocolManager.getProtocolVersion(p) >= 47)
        {
            nameToShow = ((!shuttingdown) ? "$" : "") + ChatColor.DARK_GRAY + "" + slotId + ": " + msg.substring(0, Math.min(msg.length(), 10));
        }
        int action;
        if (b)
        {
            action = 0;
        }
        else
        {
            action = 4;
        }
        message.getIntegers().write(0, action);  // int - ACTION
        if (gameProfile != null)
        {
            message.getGameProfiles().write(0, gameProfile.withName(nameToShow.substring(1)).withId(java.util.UUID.nameUUIDFromBytes(("OfflinePlayer:" + nameToShow.substring(1)).getBytes(Charsets.UTF_8)).toString()));
        }
        else
        {
            message.getGameProfiles().write(0, new WrappedGameProfile(java.util.UUID.nameUUIDFromBytes(("OfflinePlayer:" + nameToShow.substring(1)).getBytes(Charsets.UTF_8)), nameToShow.substring(1)));
        }
        message.getIntegers().write(1, 0); // int - GAMEMODE
        message.getIntegers().write(2, ping); // int - PING
        message.getStrings().write(0, nameToShow); // string - DISPLAYNAME
        //
        ArrayList<PacketContainer> packetList = cachedPackets.get(p);
        if (packetList == null)
        {
            packetList = new ArrayList<PacketContainer>();
            cachedPackets.put(p, packetList);
        }
        packetList.add(message);
    }

    private static void flushPackets()
    {
        final Player[] packetPlayers = cachedPackets.keySet().toArray(new Player[0]);
        for (Player p : packetPlayers)
        {
            flushPackets(p, null);
        }
    }

    private static void flushPackets(final Player p, final Object tabCopy)
    {
        final PacketContainer[] packets = cachedPackets.get(p).toArray(new PacketContainer[0]);
        // cancel old task (prevents flickering)
        Integer taskID = updateSchedules.get(p);
        if (taskID != null)
        {
            Bukkit.getScheduler().cancelTask(taskID);
        }
        taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(scg, new Runnable() {
            @Override
            public void run()
            {
                if (p.isOnline())
                {
                    for (PacketContainer packet : packets)
                    {
                        try
                        {
                            protocolManager.sendServerPacket(p, packet);
                        }
                        catch (InvocationTargetException e)
                        {
                            e.printStackTrace();
                            System.out.println("[TabAPI] Error sending packet to client");
                        }
                    }
                }
                if (tabCopy != null)
                {
                    if (tabCopy instanceof TabHolder47)
                    {
                        playerTabLast47.put(p.getName(), (TabHolder47) tabCopy); // we set this only if we really finally flush it (which is just now)
                    }
                    else if (tabCopy instanceof TabHolder)
                    {
                        playerTabLast.put(p.getName(), (TabHolder) tabCopy); // we set this only if we really finally flush it (which is just now)
                    }
                }
                updateSchedules.remove(p); // we're done, no need to cancel this one on next run
            }
        }, flickerPrevention);
        // let's keep a reference to be able to cancel this (see above)
        updateSchedules.put(p, taskID);
        cachedPackets.remove(p);
    }

    private static TabObject getTab(Player p)
    {
        TabObject tabo = playerTab.get(p.getName());
        if (tabo == null)
        {
            tabo = new TabObject();
            playerTab.put(p.getName(), tabo);
        }
        return tabo;
    }

    private static TabObject47 getTab47(Player p)
    {
        TabObject47 tabo = playerTab47.get(p.getName());
        if (tabo == null)
        {
            tabo = new TabObject47();
            playerTab47.put(p.getName(), tabo);
        }
        return tabo;
    }

    /**
     * Priorities
     *
     * -2 = no longer active, remove -1 = background, only show if nothing else is there 0 = normal 1 = high priority 2
     * = always show, only use if MUST show
     *
     * @param plugin
     * @param player
     * @param pri
     */
    public static void setPriority(Plugin plugin, Player player, int pri)
    {
        getTab(player).setPriority(plugin, pri);
    }

    /**
     * Returns the tab list to the vanilla tab list for a player. If another plugin holds higher priority, this does
     * notning
     *
     * @param p
     */
    public static void disableTabForPlayer(Player p)
    {
        playerTab.put(p.getName(), null);
        playerTab47.put(p.getName(), null);
        resetTabList(p);
    }

    /**
     * Resets tab to normal tab.
     *
     * @param p
     */
    public static void resetTabList(Player p)
    {
        int a = 0;
        int b = 0;
        for (Player pl : Bukkit.getOnlinePlayers())
        {
            setTabString(Bukkit.getPluginManager().getPlugin("SurvivalCG"), p, a, b, pl.getPlayerListName());//Be aware of it!
            b++;
            if (b > TabAPI.getHorizSize(protocolManager.getProtocolVersion(pl)))
            {
                b = 0;
                a++;
            }
        }
    }

    public static void setTabString(Plugin plugin, Player p, int x, int y, String msg)
    {
        setTabString(plugin, p, x, y, msg, 0, null);
    }

    public static void setTabString(Plugin plugin, Player p, int x, int y, String msg, int ping)
    {
        setTabString(plugin, p, x, y, msg, ping, null);
    }

    /**
     * Set the tab for a player.
     *
     * If the plugin the tab is being set from does not have a priority, It will automatically be give a base priority
     * of 0
     *
     * @param plugin
     * @param p
     * @param x
     * @param y
     * @param msg
     * @param ping
     * @param gameProfile
     */
    public static void setTabString(Plugin plugin, Player p, int x, int y, String msg, int ping, WrappedGameProfile gameProfile)
    {
        try
        {
            if (protocolManager.getProtocolVersion(p) >= 47)
            {
                TabObject47 tabo = getTab47(p);
                tabo.setTab(plugin, x, y, msg, ping, gameProfile);
                playerTab47.put(p.getName(), tabo);
            }
            else
            {
                TabObject tabo = getTab(p);
                tabo.setTab(plugin, x, y, msg, ping);
                playerTab.put(p.getName(), tabo);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Updates a players tab
     *
     * A tab will be updated with the tab from the highest priority plugin
     *
     * @param p
     */
    public static void updatePlayer(Player p)
    {
        if (!p.isOnline())
        {
            return;
        }
        r = 0;
        e = 0;
        if (protocolManager.getProtocolVersion(p) >= 47)
        {
            TabObject47 tabo = playerTab47.get(p.getName());
            TabHolder47 tab = tabo.getTab();
            if (tab == null)
            {
                return;
            }
            /* need to clear the tab first */
            clearTab(p);
            for (int b = 0; b < tab.maxv; b++)
            {
                for (int a = 0; a < tab.maxh; a++)
                {
                    // fix empty tabs
                    if (tab.tabs[a][b] == null)
                    {
                        tab.tabs[a][b] = nextNull();
                    }
                    String msg = tab.tabs[a][b];
                    int ping = tab.tabPings[a][b];
                    WrappedGameProfile gameProfile = tab.tabGameProfiles[a][b];
                    addPacket(p, (msg == null) ? " " : msg.substring(0, Math.min(msg.length(), 16)), getSlotId(b, a), gameProfile, true, ping);
                }
            }
            flushPackets(p, tab.getCopy());
        }
        else
        {
            TabObject tabo = playerTab.get(p.getName());
            TabHolder tab = tabo.getTab();
            if (tab == null)
            {
                return;
            }
            /* need to clear the tab first */
            clearTab(p);
            for (int b = 0; b < tab.maxv; b++)
            {
                for (int a = 0; a < tab.maxh; a++)
                {
                    // fix empty tabs
                    if (tab.tabs[a][b] == null)
                    {
                        tab.tabs[a][b] = nextNull();
                    }
                    String msg = tab.tabs[a][b];
                    int ping = tab.tabPings[a][b];
                    addPacket(p, (msg == null) ? " " : msg.substring(0, Math.min(msg.length(), 16)), 0, null, true, ping);
                }
            }
            flushPackets(p, tab.getCopy());
        }
    }

    /**
     * Clear a players tab menu
     *
     * @param p
     */
    public static void clearTab(Player p)
    {
        if (!p.isOnline())
        {
            return;
        }
        if (protocolManager.getProtocolVersion(p) >= 47)
        {
            TabHolder47 tabold = playerTabLast47.get(p.getName());
            if (tabold != null)
            {
                for (int b = 0; b < tabold.maxv; b++)
                {
                    for (int a = 0; a < tabold.maxh; a++)
                    {
                        String msg = tabold.tabs[a][b];
                        WrappedGameProfile gameProfile = tabold.tabGameProfiles[a][b];
                        addPacket(p, msg.substring(0, Math.min(msg.length(), 16)), getSlotId(b, a), gameProfile, false, 0);
                    }
                }
            }
        }
        else
        {
            TabHolder tabold = playerTabLast.get(p.getName());
            if (tabold != null)
            {
                for (String[] s : tabold.tabs)
                {
                    for (String msg : s)
                    {
                        if (msg != null)
                        {
                            addPacket(p, msg.substring(0, Math.min(msg.length(), 16)), 0, null, false, 0);
                        }
                    }
                }
            }
        }
    }

    public static void updateAll()
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            updatePlayer(p);
        }
    }


    /* return the next null filler */
    public static String nextNull()
    {
        String s = "";
        for (int a = 0; a < r; a++)
        {
            s = " " + s;
        }
        s = s + "\u00A7" + colors[e];
        e++;
        if (e > 14)
        {
            e = 0;
            r++;
        }
        return s;
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent e)
    {
        //cleanup
        playerTab.remove(e.getPlayer().getName());
        playerTabLast.remove(e.getPlayer().getName());
        playerTab47.remove(e.getPlayer().getName());
        playerTabLast47.remove(e.getPlayer().getName());
    }

    @EventHandler
    public void PlayerKick(PlayerKickEvent e)
    {
        //cleanup
        playerTab.remove(e.getPlayer().getName());
        playerTabLast.remove(e.getPlayer().getName());
        playerTab47.remove(e.getPlayer().getName());
        playerTabLast47.remove(e.getPlayer().getName());
    }

    @Deprecated
    public static int getVertSize()
    {
        return vertTabSize;
    }

    @Deprecated
    public static int getHorizSize()
    {
        return horizTabSize;
    }

    public static int getVertSize(int protocol)
    {
        if (protocol >= 47)
        {
            return vertTabSize47;
        }
        return vertTabSize;
    }

    public static int getHorizSize(int protocol)
    {
        if (protocol >= 47)
        {
            return horizTabSize47;
        }
        return horizTabSize;
    }

    public static int getSlotId(int x, int y)
    {
        if (y == 0)
        {
            return 11 + x;
        }
        if (y == 1)
        {
            return 31 + x;
        }
        if (y == 2)
        {
            return 51 + x;
        }
        if (y == 3)
        {
            return 71 + x;
        }
        return 0;
    }
}
