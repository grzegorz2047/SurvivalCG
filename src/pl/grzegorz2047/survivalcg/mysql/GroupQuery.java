package pl.grzegorz2047.survivalcg.mysql;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.grzegorz2047.survivalcg.SurvivalCG;
import pl.grzegorz2047.survivalcg.group.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by grzeg on 23.12.2015.
 */
public class GroupQuery extends Query {
    public GroupQuery(Mysql mysql) {
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

    public void getGroup(Group group) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mysql.getHikari().getConnection();

            statement = connection.prepareStatement("SELECT * FROM " + mysql.getGroupstable() + " WHERE tag='" + group.getGroupname() + "'");
            ResultSet set = statement.executeQuery();
            if (set.first() && set.isLast()) {
                group.setLeader(set.getString("leader"));
                group.setHome(new Location(Bukkit.getWorld(set.getString("world")), set.getFloat("posx"), set.getFloat("posy"), set.getFloat("posz")));
            } else {
                System.out.print("Nie znaleziono gildii " + group.getGroupname() + " w tabeli!");
            }
            statement.close();
            statement = connection.prepareStatement("SELECT * FROM " + mysql.getTable() + " WHERE guild='" + group.getGroupname() + "'");
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
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("INSERT INTO " + mysql.getGroupstable() + "("
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


    public void updateGroup(Group group) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("UPDATE " + mysql.getGroupstable() + " SET leader='" + group.getLeader() + "', world='" + group.getHome().getWorld().getName() + "', posx='" + group.getHome().getX() + "', posy='" + group.getHome().getY() + "', posz='" + group.getHome().getZ() + " WHERE tag='" + group.getGroupname() + "'");
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
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("DELETE FROM " + mysql.getGroupstable() + "  WHERE tag='" + group.getGroupname() + "'");
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

}
