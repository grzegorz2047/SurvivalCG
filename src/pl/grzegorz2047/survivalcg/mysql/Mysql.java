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

/**
 * Created by Grzegorz2047. 06.12.2015.
 */
public class Mysql {
    private String host, user, password, db, table;
    private String groupstable;
    private Integer port;
    private HikariDataSource hikari;
    //Uzywaj hikari

    public Mysql(String host, int port, String user, String password, String db, String table, String groupsTable) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.db = db;
        this.table = table;
        this.groupstable = groupsTable;
        connectToDB();
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

    public void getRanking(RankingManager rank) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = hikari.getConnection();
            statement = connection.prepareStatement("SELECT username, points FROM " + table + " ORDER by points DESC LIMIT 10");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                rank.addPoints(set.getString("username"), set.getInt("points"));
            }

        } catch (SQLException ex) {
            Bukkit.getLogger().warning("GET Ranking: " + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Player: " + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Player: " + "Error #1 MySQL ->" + ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public boolean existsGroup(String tag) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = hikari.getConnection();

            statement = connection.prepareStatement("SELECT * FROM " + groupstable + " WHERE tag='" + tag + "'");
            ResultSet set = statement.executeQuery();
            boolean exists = false;
            if (set.first() && set.isLast()) {
                exists = true;
            }
            statement.close();
            return exists;
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("GET Ranking: " + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Player: " + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Player: " + "Error #1 MySQL ->" + ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
            }
        }
        return false;
    }

    public void getGroup(Group group) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = hikari.getConnection();

            statement = connection.prepareStatement("SELECT * FROM " + groupstable + " WHERE tag='" + group.getGroupname() + "'");
            ResultSet set = statement.executeQuery();
            if (set.first() && set.isLast()) {
                group.setLeader(set.getString("leader"));
                group.setHome(new Location(Bukkit.getWorld(set.getString("world")), set.getFloat("posx"), set.getFloat("posy"), set.getFloat("posz")));
            } else {
                System.out.print("Nie znaleziono gildii " + group.getGroupname() + " w tabeli!");
            }
            statement.close();
            statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE guild='" + group.getGroupname() + "'");
            set = statement.executeQuery();
            while (set.next()) {
                group.getMembers().add(set.getString("username"));
            }

        } catch (SQLException ex) {
            Bukkit.getLogger().warning("GET Group: " + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Player: " + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Player: " + "Error #1 MySQL ->" + ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public void insertGroup(Group group) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = hikari.getConnection();
            statement = connection.prepareStatement("INSERT INTO " + groupstable + "("
                    + "tag, createdate, leader, world, posx, posy, posz) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, group.getGroupname());
            statement.setLong(2, group.getCreateDate());
            statement.setString(3, group.getLeader());
            statement.setString(4, group.getHome().getWorld().getName());
            statement.setDouble(5, group.getHome().getX());
            statement.setDouble(6, group.getHome().getY());
            statement.setDouble(7, group.getHome().getZ());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("INSERT Group: " + group.getGroupname() + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Group: " + group.getGroupname() + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Group: " + group.getGroupname() + "Error #1 MySQL ->" + ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
            }
        }

    }

    public void getPlayer(SurvUser user) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean insert = false;
        try {
            connection = hikari.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE username = ?");
            statement.setString(1, user.getUsername());
            ResultSet set = statement.executeQuery();
            if (set.first() && set.isLast()) {
                user.setPoints(set.getInt("points"));
                user.setKills(set.getInt("kills"));
                user.setDeaths(set.getInt("deaths"));
                user.setGroup(set.getString("guild"));
            } else {
                insert = true;
            }

        } catch (SQLException ex) {
            Bukkit.getLogger().warning("GET Player: " + user.getUsername() + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Player: " + user.getUsername() + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Player: " + user.getUsername() + "Error #1 MySQL ->" + ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
            }
        }
        if (insert) {
            insertPlayer(user);
        }

    }

    public void insertPlayer(SurvUser user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = hikari.getConnection();
            statement = connection.prepareStatement("INSERT INTO " + table + "("
                    + "username, points, kills, deaths, guild) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, user.getUsername());
            statement.setInt(2, 0);
            statement.setInt(3, 0);
            statement.setInt(4, 0);
            statement.setString(5, "");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("INSERT Player: " + user.getUsername() + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Player: " + user.getUsername() + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Player: " + user.getUsername() + "Error #1 MySQL ->" + ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
            }
        }

    }

    public void updateGroup(Group group) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = hikari.getConnection();
            statement = connection.prepareStatement("UPDATE " + groupstable + " SET leader='" + group.getLeader() + "', world='" + group.getHome().getWorld().getName() + "', posx='" + group.getHome().getX() + "', posy='" + group.getHome().getY() + "', posz='" + group.getHome().getZ() + " WHERE tag='" + group.getGroupname() + "'");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("UPDATE Group: " + group.getGroupname() + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Group: " + group.getGroupname() + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Group: " + group.getGroupname() + "Error #1 MySQL ->" + ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public void deleteGroup(Group group) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = hikari.getConnection();
            statement = connection.prepareStatement("DELETE FROM " + groupstable + "  WHERE tag='" + group.getGroupname() + "'");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("DELETE Group: " + group.getGroupname() + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Group: " + group.getGroupname() + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Group: " + group.getGroupname() + "Error #1 MySQL ->" + ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public void updatePlayer(SurvUser user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = hikari.getConnection();
            statement = connection.prepareStatement("UPDATE " + table + " SET points='" + user.getPoints() + "', kills='" + user.getKills() + "', deaths='" + user.getDeaths() + "', guild='" + user.getGroup() + "' WHERE username='" + user.getUsername() + "'");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("UPDATE Player: " + user.getUsername() + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Player: " + user.getUsername() + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Player: " + user.getUsername() + "Error #1 MySQL ->" + ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
            }
        }

    }

    public void updateGuildPlayer(SurvUser user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = hikari.getConnection();
            statement = connection.prepareStatement("UPDATE " + table + " SET guild='" + user.getGroup() + "' WHERE username='" + user.getUsername() + "'");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("UPDATE Player: " + user.getUsername() + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Player: " + user.getUsername() + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Player: " + user.getUsername() + "Error #1 MySQL ->" + ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
            }
        }

    }
}
