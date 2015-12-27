package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class AcceptArg extends Arg {
    private final SCG plugin;

    public AcceptArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String groupname = args[1].toUpperCase();
        boolean accepted = plugin.getGroups().addToGroup(p, groupname);
        p.sendMessage(plugin.getPrefix()+ ChatColor.GRAY + "Pomyslnie dolaczyles do druzyny " + groupname);
        Bukkit.broadcastMessage(plugin.getPrefix()+"Gracz "+ChatColor.RED+" dolaczyl do druzyny "+ChatColor.RED+groupname+ChatColor.GRAY+"!");
        return true;
    }
}
