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
    private DeathManager deathManager;
    private RankingManager rankingManager;
    private TabManager tabManager;
    private ScoreboardTagsManager scoreboardTagsManager;
    private RecipeManager recipeManager;
    private RandomTpManager randomTpManager;
    private StoneGeneratorManager stoneGeneratorManager;

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
        this.stoneGeneratorManager = new StoneGeneratorManager(plugin);
        this.taskManager = new TaskManager(plugin);
        this.commandsManager = new CommandsManager(plugin);
        this.cuboidManager = new CuboidManager(plugin);
        this.rankingManager = new RankingManager(plugin);
        this.scoreboardTagsManager = new ScoreboardTagsManager(plugin);


        String host = this.settingsManager.getSqlhost();
        int port = this.settingsManager.getSqlport();
        String user = this.settingsManager.getSqluser();
        String password = this.settingsManager.getSqlpassword();
        String db = this.settingsManager.getSqldb();
        String rankingTable = this.settingsManager.getSqlrankingtable();
        String guildTable = this.settingsManager.getSqlguildTable();
        String banTable = this.settingsManager.getSQLBanTable();
        String relationTable = this.settingsManager.getSQLRelationTable();
        this.mysqlManager = new MysqlManager(host, port, user, password, db, rankingTable, guildTable, banTable, relationTable, plugin);
        this.mysqlManager.getRankingQuery().getRankingUser(rankingManager);
        this.mysqlManager.getRankingQuery().getRankingGuilds(rankingManager);

        this.guildManager = new GuildManager(plugin);
        this.deathManager = new DeathManager(plugin);
        this.tabManager = new TabManager(plugin);
        this.guildManager.loadGuildTags();
        this.recipeManager = new RecipeManager(plugin);
        this.randomTpManager = new RandomTpManager(plugin);
    }

    public DeathManager getDeathManager() {
        return deathManager;

    }

    public RankingManager getRankingManager() {
        return rankingManager;
    }

    public TabManager getTabManager() {
        return tabManager;
    }

    public void setTabManager(TabManager tabManager) {
        this.tabManager = tabManager;
    }

    public ScoreboardTagsManager getScoreboardTagsManager() {
        return scoreboardTagsManager;
    }

    public void setScoreboardTagsManager(ScoreboardTagsManager scoreboardTagsManager) {
        this.scoreboardTagsManager = scoreboardTagsManager;
    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

    public void setRecipeManager(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    public RandomTpManager getRandomTpManager() {
        return randomTpManager;
    }

    public void setRandomTpManager(RandomTpManager randomTpManager) {
        this.randomTpManager = randomTpManager;
    }

    public StoneGeneratorManager getStoneGeneratorManager() {
        return stoneGeneratorManager;
    }
}
