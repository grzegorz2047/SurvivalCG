package pl.grzegorz2047.survivalcg.mysql;

import org.bukkit.Bukkit;
import pl.grzegorz2047.survivalcg.managers.MysqlManager;
import pl.grzegorz2047.survivalcg.managers.RankingManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by grzeg on 23.12.2015.
 */
public class RankingQuery extends Query{


    public RankingQuery(MysqlManager mysql) {
        super(mysql);
    }


    public void getRanking(RankingManager rank) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mysql.getHikari().getConnection();
            statement = connection.prepareStatement("SELECT username, points FROM " + mysql.getTable() + " ORDER by points DESC LIMIT 10");
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


}
