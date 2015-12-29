package pl.grzegorz2047.survivalcg.managers;

import com.zaxxer.hikari.HikariDataSource;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.mysql.DeathQuery;
import pl.grzegorz2047.survivalcg.mysql.GuildQuery;
import pl.grzegorz2047.survivalcg.mysql.RankingQuery;
import pl.grzegorz2047.survivalcg.mysql.UserQuery;

/**
 * Created by Grzegorz2047. 06.12.2015.
 */
public class MysqlManager {
    private final SCG plugin;
    private String host, user, password, db, usertable;
    private String guildstable, bantable;
    private Integer port;
    private HikariDataSource hikari;
    //Uzywaj hikari
    DeathQuery deathQuery;
    GuildQuery guildQuery;
    RankingQuery rankingQuery;
    UserQuery userQuery;

    public MysqlManager(String host, int port, String user, String password, String db, String usertable, String guildTable, String bantable, SCG plugin) {
        SettingsManager settings = plugin.getManager().getSettingsManager();
        String prefix = settings.getSqlprefix();
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.db = db;
        this.usertable = prefix + usertable;
        this.guildstable = prefix + guildTable;
        this.bantable = prefix + bantable;
        this.plugin = plugin;
        connectToDB();
        initiateQueries();
    }

    private void initiateQueries() {
        this.deathQuery = new DeathQuery(this);
        this.guildQuery = new GuildQuery(this, plugin);
        this.rankingQuery = new RankingQuery(this);
        this.userQuery = new UserQuery(this, plugin);
    }

    private void connectToDB() {
        hikari = new HikariDataSource();
        hikari.setMaximumPoolSize(3);
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", host);
        hikari.addDataSourceProperty("port", port);
        hikari.addDataSourceProperty("databaseName", db);
        hikari.addDataSourceProperty("user", user);
        hikari.addDataSourceProperty("password", password);
        hikari.addDataSourceProperty("cachePrepStmts", true);
        hikari.addDataSourceProperty("prepStmtCacheSize", 250);
        hikari.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDb() {
        return db;
    }

    public String getUsertable() {
        return usertable;
    }

    public String getGuildTable() {
        return guildstable;
    }

    public Integer getPort() {
        return port;
    }

    public HikariDataSource getHikari() {
        return hikari;
    }

    public DeathQuery getDeathQuery() {
        return deathQuery;
    }

    public GuildQuery getGuildQuery() {
        return guildQuery;
    }

    public RankingQuery getRankingQuery() {
        return rankingQuery;
    }

    public UserQuery getUserQuery() {
        return userQuery;
    }

    public void dispose() {
        this.getHikari().close();
    }

    public String getBantable() {
        return bantable;
    }

    public void setBantable(String bantable) {
        this.bantable = bantable;
    }
}
