package pl.grzegorz2047.survivalcg.mysql;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.managers.MysqlManager;
import pl.grzegorz2047.survivalcg.world.Cuboid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by grzeg on 23.12.2015.
 */
public class GuildQuery extends Query {
    private final SCG plugin;

    public GuildQuery(MysqlManager mysql, SCG plugin) {
        super(mysql);
        this.plugin = plugin;
    }

    public void checkIfTableExists() {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + mysql.getGuildTable() + "(" +
                    "  `tag` varchar(6) NOT NULL," +
                    "  `guildname` varchar(18) NOT NULL," +
                    "  `createdate` bigint(20) NOT NULL," +
                    "  `leader` varchar(16) NOT NULL," +
                    "  `world` varchar(30) NOT NULL," +
                    "  `posx` double NOT NULL," +
                    "  `posy` double NOT NULL," +
                    "  `posz` double NOT NULL," +
                    "  `guildpoints` int(11) NOT NULL," +
                    "  PRIMARY KEY (`tag`))");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("Create table: " + mysql.getGuildTable() + " Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Create table: " + mysql.getGuildTable() + " Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Create table: " + mysql.getGuildTable() + " Error #1 MySQL ->" + ex.getMessage());
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

    public boolean existsGuild(String tag) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mysql.getHikari().getConnection();

            statement = connection.prepareStatement("SELECT * FROM " + mysql.getGuildTable() + " WHERE tag='" + tag + "'");
            ResultSet set = statement.executeQuery();
            boolean exists = false;
            while (set.next()) {
                exists = true;
            }
            statement.close();
            return exists;
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("GET GUILD: " + "  #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Player: " + " Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Player: " + " Error #1 MySQL ->" + ex.getMessage());
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

    public void getGuild(Guild guild) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mysql.getHikari().getConnection();

            statement = connection.prepareStatement("SELECT * FROM " + mysql.getGuildTable() + " WHERE tag='" + guild.getGuildTag() + "'");
            ResultSet set = statement.executeQuery();
            int recordNumber = 0;
            while (set.next()) {
                if (recordNumber > 0) {
                    Bukkit.getLogger().warning("INCONSISTENCE IN SQL DB for guild: " + guild.getGuildTag());
                    break;
                } else {
                    guild.setLeader(set.getString("leader"));
                    guild.setHome(new Location(Bukkit.getWorld(set.getString("world")), set.getFloat("posx"), set.getFloat("posy"), set.getFloat("posz")));
                    guild.setGuildPoints(set.getInt("guildpoints"));
                    guild.setGuildName(set.getString("guildnames"));
                    recordNumber++;
                }

            }
            if (recordNumber == 0) {
                Bukkit.getLogger().warning("GUILD not found " + guild.getGuildTag() + " in table!");
            }
            statement.close();
            statement = connection.prepareStatement("SELECT * FROM " + mysql.getUsertable() + " WHERE guild='" + guild.getGuildTag() + "'");
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

    public void insertGuild(Guild guild) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("INSERT INTO " + mysql.getGuildTable() + "("
                    + "tag, guildname, createdate, leader, world, posx, posy, posz, guildpoints) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, guild.getGuildTag());
            statement.setString(2, guild.getGuildName());
            statement.setLong(3, guild.getCreateTime());
            statement.setString(4, guild.getLeader());
            statement.setString(5, guild.getHome().getWorld().getName());
            statement.setDouble(6, guild.getHome().getX());
            statement.setDouble(7, guild.getHome().getY());
            statement.setDouble(8, guild.getHome().getZ());
            statement.setInt(9, guild.getGuildPoints());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("INSERT Guild: " + guild.getGuildTag() + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Guild: " + guild.getGuildTag() + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Guild: " + guild.getGuildTag() + "Error #1 MySQL ->" + ex.getMessage());
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


    public void updateGuild(Guild guild) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("UPDATE " + mysql.getGuildTable() + " SET leader='" + guild.getLeader() + "', world='" + guild.getHome().getWorld().getName() + "', posx='" + guild.getHome().getX() + "', posy='" + guild.getHome().getY() + "', posz='" + guild.getHome().getZ() + ", guildpoints='" + guild.getGuildPoints() + "' WHERE tag='" + guild.getGuildTag() + "'");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("UPDATE Guild: " + guild.getGuildTag() + "Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Guild: " + guild.getGuildTag() + "Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Guild: " + guild.getGuildTag() + "Error #1 MySQL ->" + ex.getMessage());
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

    public void deleteGuild(String name) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("DELETE FROM " + mysql.getGuildTable() + "  WHERE tag='" + name + "'");
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

    public HashMap<String, Guild> loadGuilds() {
        HashMap<String, Guild> guilds = new HashMap<String, Guild>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + mysql.getGuildTable());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                System.out.println("Wczytuje " + set.getString("tag"));
                Guild guild = new Guild(set.getString("tag"));
                guild.setGuildName(set.getString("guildname"));
                guild.setLeader(set.getString("leader"));
                guild.setHome(new Location(Bukkit.getWorld(set.getString("world")), set.getFloat("posx"), set.getFloat("posy"), set.getFloat("posz")));
                guild.setCreateTime(set.getLong("createdate"));
                guilds.put(guild.getGuildTag(), guild);
                Cuboid cuboid = new Cuboid(guild, plugin.getManager().getSettingsManager().getCuboidRadius());
                plugin.
                        getManager().
                        getCuboidManager().
                        getCuboids().
                        put(guild.getGuildTag(),
                                cuboid);
            }
            statement.close();
            for (Map.Entry<String, Guild> entry : guilds.entrySet()) {
                statement = connection.prepareStatement("SELECT * FROM " + mysql.getUsertable() + " WHERE guild='" + entry.getValue().getGuildTag() + "'");
                set = statement.executeQuery();
                while (set.next()) {
                    entry.getValue().getMembers().add(set.getString("username"));
                }
            }
            statement.close();
            for (Map.Entry<String, Guild> entry : guilds.entrySet()) {
                statement = connection.prepareStatement("SELECT * FROM " + mysql.getRelationTable() + " WHERE inviter='" + entry.getValue().getGuildTag() + "' OR withwho='" + entry.getValue().getGuildTag() + "'");
                set = statement.executeQuery();
                while (set.next()) {
                    String inviter = set.getString("inviter");
                    String withwho = set.getString("withwho");
                    if (inviter.equals(entry.getKey())) {
                        entry.getValue().getAlly().add(withwho);
                    } else {
                        entry.getValue().getAlly().add(inviter);

                    }
                }
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
        return guilds;
    }

}
