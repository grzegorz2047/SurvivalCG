package pl.grzegorz2047.survivalcg.mysql;

import org.bukkit.Bukkit;
import pl.grzegorz2047.survivalcg.SurvivalCG;
import pl.grzegorz2047.survivalcg.user.SurvUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by grzeg on 23.12.2015.
 */
public class UserQuery extends Query{
    public UserQuery(Mysql mysql) {
        super(mysql);
    }


    public void getPlayer(SurvUser user) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean insert = false;
        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + mysql.getTable() + " WHERE username = ?");
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
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("INSERT INTO " + mysql.getTable() + "("
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


    public void updatePlayer(SurvUser user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("UPDATE " + mysql.getTable() + " SET points='" + user.getPoints() + "', kills='" + user.getKills() + "', deaths='" + user.getDeaths() + "', guild='" + user.getGroup() + "' WHERE username='" + user.getUsername() + "'");
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
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("UPDATE " + mysql.getTable() + " SET guild='" + user.getGroup() + "' WHERE username='" + user.getUsername() + "'");
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
