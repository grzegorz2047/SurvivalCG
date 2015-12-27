package old.grzegorz2047.survivalcg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by grzeg on 24.12.2015.
 */
public class VIPCommand implements CommandExecutor {


    private final SurvivalCG plugin;

    public VIPCommand(SurvivalCG plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        p.sendMessage(" ");
        p.sendMessage(ChatColor.DARK_GREEN+"Dostepne funkcje dla VIPA:");
        p.sendMessage(ChatColor.DARK_GREEN+"- Mozliwosc natychmiastowego unbana przy smierci");
        p.sendMessage(ChatColor.DARK_GREEN+"- Lepsze przedmioty startowe niz gracz");
        p.sendMessage(ChatColor.DARK_GREEN+"- Wieksza liczba osob w gildii");
        p.sendMessage(ChatColor.DARK_GREEN+"- ");
        return true;
    }
}
