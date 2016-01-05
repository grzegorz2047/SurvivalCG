package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import pl.grzegorz2047.api.permission.Permission;
import pl.grzegorz2047.survivalcg.SCG;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzeg on 26.12.2015.
 */
public class SettingsManager {

    private int points;
    private boolean teampvp = false;
    private boolean homeCommand = true;
    private boolean guildChatPrefix = true;
    private int maxClanTag = 6;
    private int minClanTag = 3;
    private List<String> swears = new ArrayList<String>();
    private List<ItemStack> reqItems = new ArrayList<ItemStack>();
    private int cuboidRadius;
    private boolean entryrotection;
    private List<Material> damageCuboidItems = new ArrayList<Material>();
    private boolean guildChat;
    private boolean hcBans;
    private long hcBanTime;
    private String hcKickMsg;
    private String lang;
    private boolean cuboidEntryNotify;
    private boolean cuboidEntrySound;
    private Sound cuboidEntrySoundType;
    private boolean cuboidEnabled;
    private boolean hcLightnings;
    private int cooldownTpTime;
    private boolean blockBuildOnTntExplode;
    private int blockedBuildOnTntTime;

    private final SCG plugin;
    private String sqlhost;
    private int sqlport;
    private String sqldb;
    private String sqluser;
    private String sqlrankingtable;
    private String sqlguildTable;
    private String sqlpassword;
    private int protectedSpawnRadius;
    private String prefix;
    private List<String> blockedWorlds = new ArrayList<String>();
    private String sqlBanTable;
    private String sqlprefix;
    private List<ItemStack> startItems = new ArrayList<ItemStack>();
    private List<String> msgDropInfo;
    private String sqlRelationTable;
    private String allyTagColor;
    private String enemyTagColor;
    private List<ItemStack> cobblexItems;
    private int stoneGeneratorDelayTime;
    private List<ItemStack> cobblexItemsRare;
    private List<String> vipCommandInfo;

    public SettingsManager(SCG plugin) {
        this.plugin = plugin;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isTeampvp() {
        return teampvp;
    }

    public void setTeampvp(boolean teampvp) {
        this.teampvp = teampvp;
    }

    public boolean isHomeCommand() {
        return homeCommand;
    }

    public void setHomeCommand(boolean homeCommand) {
        this.homeCommand = homeCommand;
    }

    public boolean isGuildChatPrefix() {
        return guildChatPrefix;
    }

    public void setGuildChatPrefix(boolean guildChatPrefix) {
        this.guildChatPrefix = guildChatPrefix;
    }

    public int getMaxClanTag() {
        return maxClanTag;
    }

    public void setMaxClanTag(int maxClanTag) {
        this.maxClanTag = maxClanTag;
    }

    public int getMinClanTag() {
        return minClanTag;
    }

    public void setMinClanTag(int minClanTag) {
        this.minClanTag = minClanTag;
    }

    public List<String> getSwears() {
        return swears;
    }

    public void setSwears(List<String> swears) {
        this.swears = swears;
    }

    public List<ItemStack> getReqItems() {
        return reqItems;
    }

    public void setReqItems(List<ItemStack> reqItems) {
        this.reqItems = reqItems;
    }

    public int getCuboidRadius() {
        return cuboidRadius;
    }

    public void setCuboidRadius(int cuboidRadius) {
        this.cuboidRadius = cuboidRadius;
    }

    public int getBlockedBuildOnTntTime() {
        return blockedBuildOnTntTime;
    }

    public void setBlockedBuildOnTntTime(int blockedBuildOnTntTime) {
        this.blockedBuildOnTntTime = blockedBuildOnTntTime;
    }

    public boolean isEntryrotection() {
        return entryrotection;
    }

    public void setEntryrotection(boolean entryrotection) {
        this.entryrotection = entryrotection;
    }


    public List<Material> getDamageCuboidItems() {
        return damageCuboidItems;
    }

    public void setDamageCuboidItems(List<Material> damageCuboidItems) {
        this.damageCuboidItems = damageCuboidItems;
    }

    public boolean isGuildChat() {
        return guildChat;
    }

    public void setGuildChat(boolean guildChat) {
        this.guildChat = guildChat;
    }

    public boolean isHcBans() {
        return hcBans;
    }

    public void setHcBans(boolean hcBans) {
        this.hcBans = hcBans;
    }

    public long getHcBanTime() {
        return hcBanTime;
    }

    public void setHcBanTime(long hcBanTime) {
        this.hcBanTime = hcBanTime;
    }

    public String getHcKickMsg() {
        return hcKickMsg;
    }

    public void setHcKickMsg(String hcKickMsg) {
        this.hcKickMsg = hcKickMsg;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean isCuboidEntryNotify() {
        return cuboidEntryNotify;
    }

    public void setCuboidEntryNotify(boolean cuboidEntryNotify) {
        this.cuboidEntryNotify = cuboidEntryNotify;
    }

    public boolean isCuboidEntrySound() {
        return cuboidEntrySound;
    }

    public void setCuboidEntrySound(boolean cuboidEntrySound) {
        this.cuboidEntrySound = cuboidEntrySound;
    }

    public Sound getCuboidEntrySoundType() {
        return cuboidEntrySoundType;
    }

    public void setCuboidEntrySoundType(Sound cuboidEntrySoundType) {
        this.cuboidEntrySoundType = cuboidEntrySoundType;
    }

    public boolean isCuboidEnabled() {
        return cuboidEnabled;
    }

    public void setCuboidEnabled(boolean cuboidEnabled) {
        this.cuboidEnabled = cuboidEnabled;
    }

    public boolean isHcLightnings() {
        return hcLightnings;
    }

    public void setHcLightnings(boolean hcLightnings) {
        this.hcLightnings = hcLightnings;
    }

    public int getCooldownTpTime() {
        return cooldownTpTime;
    }

    public void setCooldownTpTime(short cooldownTpTime) {
        this.cooldownTpTime = cooldownTpTime;
    }

    public boolean isBlockBuildOnTntExplode() {
        return blockBuildOnTntExplode;
    }

    public void setBlockBuildOnTntExplode(boolean blockBuildOnTntExplode) {
        this.blockBuildOnTntExplode = blockBuildOnTntExplode;
    }

    public String getSqlhost() {
        return sqlhost;
    }

    public void setSqlhost(String sqlhost) {
        this.sqlhost = sqlhost;
    }

    public int getSqlport() {
        return sqlport;
    }

    public void setSqlport(int sqlport) {
        this.sqlport = sqlport;
    }

    public String getSqldb() {
        return sqldb;
    }

    public void setSqldb(String sqldb) {
        this.sqldb = sqldb;
    }

    public String getSqluser() {
        return sqluser;
    }

    public void setSqluser(String sqluser) {
        this.sqluser = sqluser;
    }

    public String getSqlrankingtable() {
        return sqlrankingtable;
    }

    public void setSqlrankingtable(String sqlrankingtable) {
        this.sqlrankingtable = sqlrankingtable;
    }

    public String getSqlguildTable() {
        return sqlguildTable;
    }

    public void setSqlguildTable(String sqlguildTable) {
        this.sqlguildTable = sqlguildTable;
    }

    public String getSqlpassword() {
        return sqlpassword;
    }

    public void setSqlpassword(String sqlpassword) {
        this.sqlpassword = sqlpassword;
    }

    public void loadSettings() {
        this.plugin.reloadConfig();
        this.lang = plugin.getConfig().getString("lang");
        this.sqlhost = plugin.getConfig().getString("mysql.host");
        this.sqlport = plugin.getConfig().getInt("mysql.port");
        this.sqldb = plugin.getConfig().getString("mysql.db");
        this.sqluser = plugin.getConfig().getString("mysql.user");
        this.sqlrankingtable = plugin.getConfig().getString("mysql.usertable");
        this.sqlguildTable = plugin.getConfig().getString("mysql.guildtable");
        this.sqlpassword = plugin.getConfig().getString("mysql.password");
        this.sqlBanTable = plugin.getConfig().getString("mysql.bantable");
        this.sqlRelationTable = plugin.getConfig().getString("mysql.relationtable");
        this.sqlprefix = plugin.getConfig().getString("mysql.tableprefix");
        this.points = plugin.getConfig().getInt("stats.points");
        this.teampvp = plugin.getConfig().getBoolean("guild.team-pvp");
        this.homeCommand = plugin.getConfig().getBoolean("guild.home-command");
        this.maxClanTag = plugin.getConfig().getInt("guild.max-clan-tag");
        this.minClanTag = plugin.getConfig().getInt("guild.min-clan-tag");
        this.blockBuildOnTntExplode = plugin.getConfig().getBoolean("guild.block-on-tnt");
        this.blockedBuildOnTntTime = plugin.getConfig().getInt("guild.block-tnt-time");
        this.guildChatPrefix = plugin.getConfig().getBoolean("chat.guild-prefix");
        this.swears = plugin.getConfig().getStringList("blocked-guild-names");
        this.reqItems = parseItems(plugin.getConfig(), plugin.getConfig().getStringList("required-items"));
        this.cuboidEnabled = plugin.getConfig().getBoolean("cuboids.enabled");
        this.cuboidRadius = plugin.getConfig().getInt("cuboids.radius");
        this.entryrotection = plugin.getConfig().getBoolean("cuboids.entry-protection");
        this.damageCuboidItems = parseDamageCuboidItems(plugin.getConfig());
        this.cuboidEntryNotify = plugin.getConfig().getBoolean("cuboids.entry-notify");
        this.cuboidEntrySound = plugin.getConfig().getBoolean("cuboids.entry-sound");
        this.cuboidEntrySoundType = Sound.valueOf(plugin.getConfig().getString("cuboids.entry-sound-type"));
        if (cuboidEntrySoundType == null) {
            this.cuboidEntrySound = false;
            Bukkit.getLogger().warning("entry-sound-type contains incorrect sound type!");
        }
        this.cooldownTpTime = plugin.getConfig().getInt("teleport.cooldown");
        this.hcBans = plugin.getConfig().getBoolean("hardcore.enabled");
        this.hcBanTime = parseHcTime(plugin.getConfig());
        this.hcKickMsg = plugin.getConfig().getString("hardcore.kickmsg");
        this.hcLightnings = plugin.getConfig().getBoolean("hardcore.lightnings");
        this.protectedSpawnRadius = plugin.getConfig().getInt("protection.spawn-radius");
        this.prefix = plugin.getConfig().getString("chat.prefix").replace('&', '§');
        this.blockedWorlds = plugin.getConfig().getStringList("blocked-worlds");
        this.startItems = parseItems(plugin.getConfig(), plugin.getConfig().getStringList("start-items"));
        this.msgDropInfo = plugin.getConfig().getStringList("msg-drop-info");
        this.vipCommandInfo = plugin.getConfig().getStringList("vip-command");
        Permission.PERMISSIONS_VIP = plugin.getConfig().getString("permissions.vip-permission");

        this.allyTagColor = plugin.getConfig().getString("guild.ally-tag-color");
        this.enemyTagColor = plugin.getConfig().getString("guild.enemy-tag-color");
        this.cobblexItems = parseItems(plugin.getConfig(), plugin.getConfig().getStringList("cobblex-items"));
        this.cobblexItemsRare = parseItems(plugin.getConfig(), plugin.getConfig().getStringList("cobblex-items-rare"));
        this.stoneGeneratorDelayTime = plugin.getConfig().getInt("stone-regenerator-delay");

    }

    private List<ItemStack> parseItems(FileConfiguration config, List<String> reqItems) {
        List<ItemStack> itemstackList = new ArrayList<ItemStack>();
        Bukkit.getLogger().warning("Ilosc przedmiotow do wczytania " + reqItems);
        if (reqItems != null && !reqItems.isEmpty()) {

            if (reqItems.size() > 54) {
                Bukkit.getLogger().warning("Too many specified items (required-items)! Maximum size is 54!");
            } else {
                for (String s : reqItems) {
                    String[] info = s.split(":");
                    if (info.length != 4) {
                        Bukkit.getLogger().warning("Oops! It looks like you're using an old configuration file!/You have made mistake with required-items section! We changed pattern of required-items section. Now it looks like this: Material:Durability:Data:Amount (old was: Material:Amount) - please update your config.yml Exact line is " + s);
                        break;
                    }
                    Material material = Material.valueOf(info[0]);
                    if (material == null) {
                        Bukkit.getLogger().warning("Invalid material: " + info[0] + "! Check your configuration file!");
                        continue;
                    }

                    for (ItemStack i : itemstackList) {
                        if (i.getType().equals(material)) {
                            Bukkit.getLogger().warning("Duplicate item found! Skipping ...");
                            continue;
                        }
                    }

                    ItemStack is = new ItemStack(Material.AIR);
                    short durability = 0;
                    try {
                        durability = Short.valueOf(info[1]);
                    } catch (NumberFormatException e) {
                        Bukkit.getLogger().warning("Durability must be a number! Please fix 'required-items' section in your config.yml");
                    }

                    byte data = 0;
                    try {
                        data = Byte.valueOf(info[2]);
                    } catch (NumberFormatException e) {
                        Bukkit.getLogger().warning("Data must be a number! Please fix 'required-items' section in your config.yml");
                    }

                    int amount = 1;
                    try {
                        amount = Integer.valueOf(info[3]);

                        if (amount > 64) {
                            amount = 64;
                        } else if (amount < 0) {
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        Bukkit.getLogger().warning("Amount must be a number! Please fix 'required-items' section in your config.yml");
                    }

                    ItemStack item = new ItemStack(material, amount, durability, data);
                    itemstackList.add(item);
                }
            }
        } else {
            itemstackList = new ArrayList<ItemStack>();
            Bukkit.getLogger().warning("Brak itemow do wczytania!");
        }
        Bukkit.getLogger().warning("ilosc na wyjsciu: " + itemstackList.size());
        return itemstackList;
    }

    private List<Material> parseDamageCuboidItems(FileConfiguration config) {
        List<String> items = plugin.getConfig().getStringList("cuboids.damageCuboidItems");
        List<Material> materials = new ArrayList<Material>();
        for (String item : items) {
            Material mat = Material.getMaterial(item);
            if (mat == null) {
                Bukkit.getLogger().warning("Material for damageCuboidItems " + item + " is incorrect!");
            } else {
                materials.add(mat);
            }
        }
        return materials;
    }

    private Long parseHcTime(FileConfiguration config) {
        long hcBantime;
        String time = config.getString("hardcore.bantime");
        String length = time.substring(0, time.length() - 1);
        long result;
        try {
            result = Long.parseLong(length);
        } catch (NumberFormatException ex) {
            Bukkit.getLogger().warning("Could not load ban time, defaults using 1 minute. Check your ban-time in config.yml file.");
            hcBantime = 60 * 1000;
            return hcBantime;
        }
        if (time.endsWith("s")) { // Seconds
            result = result * 1000;
        } else if (time.endsWith("m")) { // Minutes
            result = result * 60 * 1000;
        } else if (time.endsWith("h")) { // Hours
            result = result * 60 * 60 * 1000;
        } else if (time.endsWith("d")) { // Days
            result = result * 60 * 24 * 60 * 1000;
        } else {
        } // Ticks or null
        hcBantime = result;
        return hcBantime;
    }

    public int getProtectedSpawnRadius() {
        return protectedSpawnRadius;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<String> getBlockedWorlds() {
        return blockedWorlds;
    }

    public String getSQLBanTable() {
        return sqlBanTable;
    }

    public String getSqlprefix() {
        return sqlprefix;
    }

    public void setSqlprefix(String sqlprefix) {
        this.sqlprefix = sqlprefix;
    }

    public List<ItemStack> getStartItems() {
        return startItems;
    }

    public List<String> getMsgDropInfo() {
        return msgDropInfo;
    }

    public void setMsgDropInfo(List<String> msgDropInfo) {
        this.msgDropInfo = msgDropInfo;
    }

    public String getSQLRelationTable() {
        return sqlRelationTable;

    }

    public void setSqlRelationTable(String sqlRelationTable) {
        this.sqlRelationTable = sqlRelationTable;
    }

    public String getAllyTagColor() {
        return allyTagColor;
    }

    public void setAllyTagColor(String allyTagColor) {
        this.allyTagColor = allyTagColor;
    }

    public String getEnemyTagColor() {
        return enemyTagColor;
    }

    public void setEnemyTagColor(String enemyTagColor) {
        this.enemyTagColor = enemyTagColor;
    }

    public List<ItemStack> getCobblexItems() {
        return cobblexItems;
    }

    public void setCobblexItems(List<ItemStack> cobblexItems) {
        this.cobblexItems = cobblexItems;
    }

    public int getStoneGeneratorDelayTime() {
        return stoneGeneratorDelayTime;
    }

    public void setStoneGeneratorDelayTime(int stoneGeneratorDelayTime) {
        this.stoneGeneratorDelayTime = stoneGeneratorDelayTime;
    }

    public List<ItemStack> getCobblexItemsRare() {
        return cobblexItemsRare;
    }

    public void setCobblexItemsRare(List<ItemStack> cobblexItemsRare) {
        this.cobblexItemsRare = cobblexItemsRare;
    }

    public List<String> getVipCommandInfo() {
        return vipCommandInfo;
    }

    public void setVipCommandInfo(List<String> vipCommandInfo) {
        this.vipCommandInfo = vipCommandInfo;
    }
}
