package pl.grzegorz2047.api.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.runnable.Counter;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.api.util.BungeeUtil;
import pl.grzegorz2047.api.util.NameTagUtil;
import pl.grzegorz2047.api.util.NeksiDramUtil;
import pl.neksi.craftgames.game.ArenaStatus;

import java.util.*;

/**
 * Created by Grzegorz2047. 28.08.2015.
 */
public class MinigameManager {

    protected CoreManager coreManager;
    private Plugin plugin;
    private WorldManager wm;
    protected int minReqPlayers;//(active/2);

    private String worldName;

    public String getWorldName() {
        return worldName;
    }

    /*                                 */
    protected int moneyForKills = 5;
    protected int expForKills = 3;
    protected int moneyForWin = 15;
    protected int expForWin = 15;
    protected int expForleaving = -20;
    protected int expForDeath = -10;
    /*                                 */

    protected Random r = new Random();

    public MinigameManager(CoreManager coreManager) {
        NameTagUtil tag = new NameTagUtil(true);//Init scoreboard
        this.coreManager = coreManager;
        this.setGameState(GameState.RESTARTING);
        this.plugin = coreManager.getPlugin();
        this.minReqPlayers = 10;
        NeksiDramUtil.initArenaStatus(Bukkit.getMaxPlayers());
        NeksiDramUtil.setLore(ChatColor.GOLD + "Beta testy");
        this.setGameState(GameState.WAITING);
        NeksiDramUtil.setArenaStatus(ArenaStatus.Status.WAITING);
    }

    protected Map<String, User> players = new HashMap<String, User>();

    public int getMoneyForKills() {
        return moneyForKills;
    }

    public int getExpForDeath() {
        return expForDeath;
    }

    public int getExpForKills() {
        return expForKills;
    }

    public int getMoneyForWin() {
        return moneyForWin;
    }

    public int getExpForWin() {
        return expForWin;
    }

    public int getExpForleaving() {
        return expForleaving;
    }

    public int getMinReqPlayers() {
        return minReqPlayers;
    }

    public enum GameState {WAITING, STARTING, INGAME, RESTARTING}

    public Map<String, User> getPlayers() {
        return players;
    }

    protected GameState state = GameState.RESTARTING;

    public GameState getGameState() {
        return this.state;
    }

    public void setGameState(GameState state) {
        this.state = state;
    }

    public boolean startGame(int time) {
        if (this.getGameState().equals(GameState.WAITING)) {
            Counter counter = new Counter(plugin, time);//Start counting down to start
            counter.start();
            this.setGameState(GameState.STARTING);
            Bukkit.broadcastMessage(MsgManager.msg("Odliczanie zostalo rozpoczete!"));
            return true;
        }
        return false;
    }

    public boolean isInGame() {
        // MsgManager.debug("IS IN GAME!");
        return state.equals(GameState.INGAME);
    }

    public boolean isRestarting() {
        return this.getGameState().equals(GameState.RESTARTING);
    }

    public User addPlayer(Player p, boolean spectator) {
        User user = new User(p.getName(), spectator);
        this.players.put(p.getName(), user);
        return user;
    }


    public void removePlayer(Player p) {
        this.players.remove(p.getName());
    }

    protected boolean end = false;

    public void endGame() {
        Bukkit.broadcastMessage(MsgManager.msg("Rozgrywka zostala zakonczona! Arena restartuje sie za 5 sekund!"));

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    BungeeUtil.changeServer(plugin, p, BungeeUtil.lobbyServer);
                }
                NeksiDramUtil.setArenaStatus(ArenaStatus.Status.RESTARTING);
            }
        }, 5 * 20l);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {

                MinigameManager.this.resetSelf();
            }
        }, 7 * 20l);
    }


    public List<User> getActivePlayers() {
        List<User> users = new ArrayList<User>();
        for (User u : this.players.values()) {
            if (!u.isSpectator()) {
                users.add(u);
            }
        }
        return users;
    }

    public void resetSelf() {

    }


}
