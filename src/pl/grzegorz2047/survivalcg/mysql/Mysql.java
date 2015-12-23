package pl.grzegorz2047.survivalcg.mysql;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.grzegorz2047.survivalcg.user.SurvUser;
import pl.grzegorz2047.survivalcg.group.Group;
import pl.grzegorz2047.survivalcg.managers.RankingManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grzegorz2047. 06.12.2015.
 */
public class Mysql {
    private String host, user, password, db, table;
    private String groupstable;
    private Integer port;
    private HikariDataSource hikari;
    //Uzywaj hikari
    DeathQuery deathQuery;
    GroupQuery groupQuery;
    RankingQuery rankingQuery;
    UserQuery userQuery;

    public Mysql(String host, int port, String user, String password, String db, String table, String groupsTable) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.db = db;
        this.table = table;
        this.groupstable = groupsTable;
        connectToDB();
        initiateQueries();
    }

    private void initiateQueries() {
        this.deathQuery = new DeathQuery(this);
        this.groupQuery = new GroupQuery(this);
        this.rankingQuery = new RankingQuery(this);
        this.userQuery = new UserQuery(this);
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

    public String getTable() {
        return table;
    }

    public String getGroupstable() {
        return groupstable;
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

    public GroupQuery getGroupQuery() {
        return groupQuery;
    }

    public RankingQuery getRankingQuery() {
        return rankingQuery;
    }

    public UserQuery getUserQuery() {
        return userQuery;
    }
}
