package pl.grzegorz2047.survivalcg.mysql;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.grzegorz2047.survivalcg.managers.MysqlManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by grzeg on 23.12.2015.
 */
public class DeathQuery extends Query {

    public DeathQuery(MysqlManager mysql) {
        super(mysql);
    }


    public void banPlayer(String name, long time) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("INSERT INTO " + mysql.getBantable() + "("
                    + "username, bantime) VALUES (?, ?)");
            statement.setString(1, name);
            String sum = String.valueOf(System.currentTimeMillis() + time);
            statement.setString(2, sum);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("INSERT Player: " + name + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Player: " + name + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Player: " + name + "Error #1 MySQL ->" + ex.getMessage());
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

    public String getPlayer(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        String answer = "-1";
        try {
            connection = mysql.getHikari().getConnection();

            statement = connection.prepareStatement("SELECT * FROM " + mysql.getBantable() + " WHERE username='" + username + "'");
            ResultSet set = statement.executeQuery();
            if (set.first() && set.isLast()) {
                answer = set.getString("bantime");
            }
            statement.close();
            return answer;
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("GET Guild: " + "Error #1 MySQL ->" + ex.getSQLState());

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
        return answer;
    }

    public void unbanPlayer(String username) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("DELETE FROM " + mysql.getBantable() + "  WHERE username='" + username + "'");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("DELETE Guild: " + username + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Guild: " + username + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Guild: " + username + "Error #1 MySQL ->" + ex.getMessage());
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
    public void checkIfTableExists(){
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("CREATE IF NOT EXIST " + mysql.getBantable() + " (" +
                    "  `username` varchar(16) NOT NULL, " +
                    "  `bantime` bigint(20) NOT NULL, " +
                    "  PRIMARY KEY (`username`) ");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("Create table: " +  mysql.getBantable() + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Create table: " +  mysql.getBantable() + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Create table: " +  mysql.getBantable() + "Error #1 MySQL ->" + ex.getMessage());
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
