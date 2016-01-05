package pl.grzegorz2047.api.user;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.world.Cuboid;

import java.util.logging.Level;

public class User {

    protected String username;
    protected Long firstJoinTime;

    protected int money, kills, deaths, wins = 0;//?
    private int points = 1000;
    private Guild guild; //Used as pointer to guild
    private String lastKilledPlayer = "";
    private Cuboid currentCuboid;
    private boolean toBan = false;
    private boolean onSpawn = false;
    private float kFactor;
    private int constant = 10;

    private User() {
    }

    public User(String username) {
        Validate.notNull(username);
        if (username == null) {
            Bukkit.getLogger().log(Level.WARNING, "Username null");
        }
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(username);
    }

    public int getMoney() {
        return money;
    }

    public int getKills() {
        return kills;
    }


    public int getWins() {
        return wins;
    }

    public void heal() {
        this.getPlayer().setHealth(this.getPlayer().getMaxHealth());
        this.getPlayer().setFoodLevel(20);
    }

    public void setKills(int kills) {
        this.kills = kills;
    }


    public Long getFirstJoinTime() {
        return firstJoinTime;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getPoints() {
        return points;
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild g) {
        this.guild = g;
    }

    public String getLastKilledPlayer() {
        return lastKilledPlayer;
    }

    public void setLastKilledPlayer(String lastKilledPlayer) {
        this.lastKilledPlayer = lastKilledPlayer;
    }

    public Cuboid getCurrentCuboid() {
        return currentCuboid;
    }

    public void setCurrentCuboid(Cuboid currentCuboid) {
        this.currentCuboid = currentCuboid;
    }

    public boolean isToBan() {
        return toBan;
    }

    public void setToBan(boolean toBan) {
        this.toBan = toBan;
    }

    public boolean isOnSpawn() {
        return onSpawn;
    }

    public void setOnSpawn(boolean onSpawn) {
        this.onSpawn = onSpawn;
    }

    public float getkFactor() {
        return kFactor;
    }

    public void setkFactor(float kFactor) {
        this.kFactor = kFactor;
    }

    public int getConstant() {
        return constant;
    }

    public void setConstant(int constant) {
        this.constant = constant;
    }
}