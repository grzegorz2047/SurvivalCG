package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.world.Cuboid;

/**
 * Created by grzegorz2047 on 29.12.2015.
 */
public class PlayerPlaceListener implements Listener {

    private final SCG plugin;

    public PlayerPlaceListener(SCG plugin){
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());
        Guild guild = user.getGuild();
        Cuboid cuboid = user.getCurrentCuboid();
        if(cuboid != null){
            if(guild != null){
                if(!user.getGuild().equals(cuboid.getGuild())){
                    p.sendMessage(plugin.getManager().getMsgManager().getMsg("enemyguildblockplace"));
                    e.setCancelled(true);
                }
            }else{
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("enemyguildblockplace"));
                e.setCancelled(true);
            }
            Bukkit.broadcastMessage("Gracz "+p.getName()+" robi cos na cuboidzie "+cuboid.getGuild().getGuildName());

        }
    }

}
