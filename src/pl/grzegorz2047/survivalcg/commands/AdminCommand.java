package pl.grzegorz2047.survivalcg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by grzeg on 23.12.2015.
 */
public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender.hasPermission("survivalcg.admin")){
            if(args[0].equalsIgnoreCase("bangroup")){

            }
            if(args[0].equalsIgnoreCase("rollback")){//Jezeli bedzie zapisywalo sie logi z zabic gracza to bedzie mozna cofac zabrane punkty

            }
            if(args[0].equalsIgnoreCase("removegroup")){

            }
            
        }
        return true;
    }
}
