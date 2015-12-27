package pl.grzegorz2047.survivalcg.mysql;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.managers.MysqlManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by grzeg on 23.12.2015.
 */
public class GroupQuery extends Query {
    public GroupQuery(MysqlManager mysql) {
        super(mysql);
    }


    public boolean existsGroup(String tag) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mysql.getHikari().getConnection();

            statement = connection.prepareStatement("SELECT * FROM " + mysql.getGroupstable() + " WHERE tag='" + tag + "'");
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

    public void getGroup(Guild guild) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mysql.getHikari().getConnection();

            statement = connection.prepareStatement("SELECT * FROM " + mysql.getGroupstable() + " WHERE tag='" + guild.getGuildName() + "'");
            ResultSet set = statement.executeQuery();
            if (set.first() && set.isLast()) {
                guild.setLeader(set.getString("leader"));
                guild.setHome(new Location(Bukkit.getWorld(set.getString("world")), set.getFloat("posx"), set.getFloat("posy"), set.getFloat("posz")));
            } else {
                System.out.print("Nie znaleziono gildii " + guild.getGuildName() + " w tabeli!");
            }
            statement.close();
            statement = connection.prepareStatement("SELECT * FROM " + mysql.getTable() + " WHERE guild='" + guild.getGuildName() + "'");
            set = statement.executeQuery();
            while (set.next()) {
                guild.getMembers().add(set.getString("username"));
            }

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
    }

    public void insertGroup(Guild guild) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("INSERT INTO " + mysql.getGroupstable() + "("
                    + "tag, createdate, leader, world, posx, posy, posz) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, guild.getGuildName());
            statement.setLong(2, guild.getCreateDate());
            statement.setString(3, guild.getLeader());
            statement.setString(4, guild.getHome().getWorld().getName());
            statement.setDouble(5, guild.getHome().getX());
            statement.setDouble(6, guild.getHome().getY());
            statement.setDouble(7, guild.getHome().getZ());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("INSERT Guild: " + guild.getGuildName() + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Guild: " + guild.getGuildName() + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Guild: " + guild.getGuildName() + "Error #1 MySQL ->" + ex.getMessage());
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


    public void updateGroup(Guild guild) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("UPDATE " + mysql.getGroupstable() + " SET leader='" + guild.getLeader() + "', world='" + guild.getHome().getWorld().getName() + "', posx='" + guild.getHome().getX() + "', posy='" + guild.getHome().getY() + "', posz='" + guild.getHome().getZ() + " WHERE tag='" + guild.getGuildName() + "'");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("UPDATE Guild: " + guild.getGuildName() + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Guild: " + guild.getGuildName() + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Guild: " + guild.getGuildName() + "Error #1 MySQL ->" + ex.getMessage());
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

    public void deleteGroup(String name) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("DELETE FROM " + mysql.getGroupstable() + "  WHERE tag='" + name + "'");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("DELETE Guild: " + name + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Guild: " + name + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Guild: " + name + "Error #1 MySQL ->" + ex.getMessage());
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
