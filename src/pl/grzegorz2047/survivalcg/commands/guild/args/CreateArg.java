package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class CreateArg extends Arg {
    private final SCG plugin;

    public CreateArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        boolean created = plugin.getGroups().createGroup(p,args[1].toUpperCase());
        if(created){
            p.sendMessage(plugin.getPrefix()+ ChatColor.GRAY + "Twoja druzyna zostala stworzona!");
        }
    }
}
