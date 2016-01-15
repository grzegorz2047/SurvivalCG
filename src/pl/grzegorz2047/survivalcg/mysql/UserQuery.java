package pl.grzegorz2047.survivalcg.mysql;

import org.bukkit.Bukkit;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.managers.MysqlManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by grzeg on 23.12.2015.
 */
public class UserQuery extends Query {
    private final SCG plugin;

    public UserQuery(MysqlManager mysql, SCG plugin) {
        super(mysql);
        this.plugin = plugin;
    }

    public void checkIfTableExists() {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + mysql.getUsertable() + "( " +
                    "  `username` varchar(16) NOT NULL,  " +
                    "  `points` int(11) NOT NULL,  " +
                    "  `kills` int(11) NOT NULL,  " +
                    "  `deaths` int(11) NOT NULL,  " +
                    "  `guild` varchar(6) NOT NULL,  " +
                    "  PRIMARY KEY (`username`))");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("Create table: " + mysql.getUsertable() + " Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Create table: " + mysql.getUsertable() + " Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Create table: " + mysql.getUsertable() + " Error #1 MySQL ->" + ex.getMessage());
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

    public void getPlayer(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean insert = false;
        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + mysql.getUsertable() + " WHERE username = ? LIMIT 1");
            statement.setString(1, user.getUsername());
            ResultSet set = statement.executeQuery();
            int recordNumber = 0;
            while (set.next()) {
                if (recordNumber > 0) {
                    Bukkit.getLogger().warning("INCONSISTENCE IN SQL DB for player: " + user.getUsername());
                    break;
                } else {
                    user.setPoints(set.getInt("points"));
                    user.setKills(set.getInt("kills"));
                    user.setDeaths(set.getInt("deaths"));
                    user.setGuild(plugin.getManager().getGuildManager().getGuilds().get(set.getString("guild")));//Return null if not exists
                    recordNumber++;
                }

            }
            if (recordNumber == 0) {
                insert = true;
            }

        } catch (SQLException ex) {
            Bukkit.getLogger().warning("GET Player: " + user.getUsername() + " Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Player: " + user.getUsername() + " Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Player: " + user.getUsername() + " Error #1 MySQL ->" + ex.getMessage());
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
            user.setKills(0);
            user.setDeaths(0);
            insertPlayer(user);
        }

    }

    public void insertPlayer(User user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("INSERT INTO " + mysql.getUsertable() + "("
                    + "username, points, kills, deaths, guild) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, user.getUsername());
            statement.setInt(2, user.getPoints());
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


    public void updatePlayer(User user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            Guild g = user.getGuild();
            String guildname = "";
            if (g != null) {
                guildname = g.getGuildTag();
            }
            statement = connection.prepareStatement("UPDATE " + mysql.getUsertable() + " SET points='" +
                    user.getPoints() +
                    "', kills='" +
                    user.getKills() + "', deaths='" +
                    user.getDeaths() + "', guild='" +
                    guildname + "' WHERE username='" + user.getUsername() + "'");
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

    public void updateGuildPlayer(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        Guild g = user.getGuild();
        String guildname = "";
        if (g != null) {
            guildname = g.getGuildTag();
        }
        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("UPDATE " + mysql.getUsertable() + " SET guild='" + guildname + "' WHERE username='" + user.getUsername() + "'");
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
