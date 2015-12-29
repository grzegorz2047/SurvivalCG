package pl.grzegorz2047.survivalcg.managers;

import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzeg on 26.12.2015.
 */
public class Manager {
    private final SCG plugin;
    private MsgManager msgManager;
    private GuildManager guildManager;
    private CuboidManager cuboidManager;
    private TeleportManager teleportManager;
    private MysqlManager mysqlManager;
    private TaskManager taskManager;
    private AntiLogoutManager antiLogoutManager;
    private UserManager userManager;
    private SettingsManager settingsManager;
    private CommandsManager commandsManager;

    public Manager(SCG scg) {
        this.plugin = scg;

    }


    public MsgManager getMsgManager() {
        return msgManager;
    }

    public TeleportManager getTeleportManager() {
        return teleportManager;
    }

    /**
     * Run this to make cleanup. For example when someone tries to use /reload
     */
    public void disposeManager() {
        this.mysqlManager.dispose();
        this.taskManager.dispose();
    }

    public AntiLogoutManager getAntiLogoutManager() {
        return antiLogoutManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public CuboidManager getCuboidManager() {
        return cuboidManager;
    }

    public GuildManager getGuildManager() {
        return guildManager;
    }

    public MysqlManager getMysqlManager() {
        return mysqlManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    /**
     * Run this outside class constructor due to accessing manager class until
     * being properly initialised
     */
    public void initiateManagers() {
        this.settingsManager = new SettingsManager(plugin);
        this.settingsManager.loadSettings();
        this.msgManager = new MsgManager(plugin);
        this.teleportManager = new TeleportManager(plugin);
        this.antiLogoutManager = new AntiLogoutManager(plugin);
        this.userManager = new UserManager(plugin);
        this.taskManager = new TaskManager(plugin);
        this.commandsManager = new CommandsManager(plugin);
        this.cuboidManager = new CuboidManager(plugin);

        String host = this.settingsManager.getSqlhost();
        int port = this.settingsManager.getSqlport();
        String user = this.settingsManager.getSqluser();
        String password = this.settingsManager.getSqlpassword();
        String db = this.settingsManager.getSqldb();
        String rankingTable = this.settingsManager.getSqlrankingtable();
        String guildTable = this.settingsManager.getSqlguildTable();

        this.mysqlManager = new MysqlManager(host, port, user, password, db, rankingTable, guildTable, plugin);
        this.guildManager = new GuildManager(plugin);


    }

}
