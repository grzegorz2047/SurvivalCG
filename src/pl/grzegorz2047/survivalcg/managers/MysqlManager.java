package pl.grzegorz2047.survivalcg.managers;

import com.zaxxer.hikari.HikariDataSource;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.mysql.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

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
    private RelationQuery relationQuery;
    private String relationTable;
    private String databaseType;

    public MysqlManager(String host, int port, String user, String password, String db, String usertable, String guildTable, String bantable, String relationTable, String databaseType, SCG plugin) {
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
        this.relationTable = prefix + relationTable;
        this.plugin = plugin;
        this.databaseType = databaseType;
        connectToDB();
        initiateQueries();
    }


    private void initiateQueries() {
        this.deathQuery = new DeathQuery(this);
        this.deathQuery.checkIfTableExists();
        this.guildQuery = new GuildQuery(this, plugin);
        this.guildQuery.checkIfTableExists();
        this.rankingQuery = new RankingQuery(this);
        this.userQuery = new UserQuery(this, plugin);
        this.userQuery.checkIfTableExists();
        this.relationQuery = new RelationQuery(this, plugin);
        this.relationQuery.checkIfTableExists();
    }

    private void connectToDB() {
        hikari = new HikariDataSource();
        hikari.setMaximumPoolSize(3);
        if (plugin.getManager().getSettingsManager().getDatabasetype().equalsIgnoreCase("mysql")) {
            hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            hikari.addDataSourceProperty("serverName", host);

            hikari.addDataSourceProperty("databaseName", db);
            hikari.addDataSourceProperty("port", port);
            hikari.addDataSourceProperty("user", user);
            hikari.addDataSourceProperty("password", password);
            hikari.addDataSourceProperty("cachePrepStmts", true);
            hikari.addDataSourceProperty("prepStmtCacheSize", 250);
            hikari.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        } else {
           /* File dataFolder = new File(plugin.getDataFolder(), db + ".db");
            if (!dataFolder.exists()) {
                try {
                    dataFolder.createNewFile();
                } catch (IOException e) {
                    plugin.getLogger().log(Level.SEVERE, "File write error: " + db + ".db");
                }
            }*/

             hikari.setDriverClassName("org.sqlite.JDBC");
            //hikari.setDataSourceClassName("org.sqlite.SQLiteDataSource");
            hikari.setPoolName("GuildSQLitePool");
            hikari.setJdbcUrl("jdbc:sqlite:" + plugin.getDataFolder() + File.separator + db + ".db");
            System.out.println("path ========== " + plugin.getDataFolder() + File.separator + db + ".db");
            hikari.setConnectionTestQuery("SELECT 1");
            hikari.setMaxLifetime(60000); // 60 Sec
            hikari.setIdleTimeout(45000); // 45 Sec
            hikari.setMaximumPoolSize(50);

        }


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

    public RelationQuery getRelationQuery() {
        return relationQuery;

    }

    public String getRelationTable() {
        return relationTable;
    }

    public void setRelationTable(String relationTable) {
        this.relationTable = relationTable;
    }

    public String getDatabaseType() {
        return databaseType;
    }
}
