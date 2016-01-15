package pl.grzegorz2047.survivalcg.mysql;

import org.bukkit.Bukkit;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.managers.MysqlManager;
import pl.grzegorz2047.survivalcg.guild.relation.Relation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class RelationQuery extends Query {


    private final SCG plugin;

    public RelationQuery(MysqlManager mysql, SCG plugin) {
        super(mysql);
        this.plugin = plugin;
    }
    public void checkIfTableExists(){
        Connection connection = null;
        PreparedStatement statement = null;
        String autoIncrementKeyword = "AUTO_INCREMENT";
        try {
            if(mysql.getDatabaseType().equals("sqlite")){
                autoIncrementKeyword = "";
            }
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + mysql.getRelationTable() + "(" +
                    "  `id` int(11) "+autoIncrementKeyword+", " +
                    "  `inviter` varchar(16) NOT NULL, " +
                    "  `withwho` varchar(16) NOT NULL, " +
                    "  `relation` varchar(11) NOT NULL, " +
                    "  `createdate` bigint(20) NOT NULL, " +
                    "  PRIMARY KEY (`id`))");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("Create table: " +  mysql.getRelationTable() + " Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("Create table: " +  mysql.getRelationTable() + " Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("Create table: " +  mysql.getRelationTable() + " Error #1 MySQL ->" + ex.getMessage());
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
    public void addRelation(Guild requester, Guild withWho){
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("INSERT INTO " + mysql.getRelationTable() + "("
                    + "id, inviter, withwho, relation, createdate ) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, 0);
            statement.setString(2, requester.getGuildTag());
            statement.setString(3, withWho.getGuildTag());
            statement.setString(4, String.valueOf(Relation.Status.ALLY));
            statement.setLong(5, System.currentTimeMillis());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("INSERT relation:    Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("relation:  Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("relation:  Error #1 MySQL ->" + ex.getMessage());
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
    public void removeRelation(String requester, String withWho){

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("DELETE FROM " + mysql.getRelationTable() + "  WHERE (inviter='" + requester + "' AND inviter='"+withWho+"') OR (withwho='"+requester+"' AND withwho='"+withWho+"')");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().warning("DELETE relation:   Error #1 MySQL ->" + ex.getSQLState());

            Bukkit.getLogger().warning("relation:  Error #1 MySQL ->" + ex.getSQLState());
            Bukkit.getLogger().warning("relation:  Error #1 MySQL ->" + ex.getMessage());
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
