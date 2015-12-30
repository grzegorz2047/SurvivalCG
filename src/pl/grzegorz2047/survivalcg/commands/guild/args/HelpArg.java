package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class HelpArg extends Arg {

    private final SCG plugin;

    public HelpArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Player p = (Player) sender;
            p.sendMessage(" ");
            p.sendMessage(ChatColor.GRAY+"Lista dostepnych komend:");
            p.sendMessage(ChatColor.GREEN+"/g zaloz <NazwaGildii> "+ChatColor.GRAY+"- Tworzy nowa gildie");
            p.sendMessage(ChatColor.GREEN+"/g usun "+ChatColor.GRAY+"- Usuwa gildie");
            p.sendMessage(ChatColor.GREEN+"/g wyrzuc <nick> "+ChatColor.GRAY+"- Wyrzuca gracza z gildii");
            p.sendMessage(ChatColor.GREEN+"/g zapros <nick> "+ChatColor.GRAY+"- Zaprasza gracza do gildii");
            p.sendMessage(ChatColor.GREEN+"/g dom "+ChatColor.GRAY+"- Teleportuje na spawn gildii");
            p.sendMessage(ChatColor.GREEN+"/g opusc "+ChatColor.GRAY+"- Opuszczasz obecna gildie");
            p.sendMessage(ChatColor.GREEN+"/g przedmioty "+ChatColor.GRAY+"- Pokazuje wymagane przedmioty na gildie");
            p.sendMessage(ChatColor.GREEN+"/g sojusz <NazwaGildii> "+ChatColor.GRAY+"- Dodaje sojusznika twojej gildii");
            p.sendMessage(ChatColor.GREEN+"/g wrog <NazwaGildii> "+ChatColor.GRAY+"- Rozwiazuje sojusz z gildia");
            p.sendMessage(ChatColor.GREEN+"/g user "+ChatColor.GRAY+"- Pokazuje informacje o swoim koncie");
            p.sendMessage(ChatColor.GREEN+"/g info <NazwaGildii> "+ChatColor.GRAY+"- Pokazuje informacje o danej gildii");
            p.sendMessage(" ");

        }
    }
}
