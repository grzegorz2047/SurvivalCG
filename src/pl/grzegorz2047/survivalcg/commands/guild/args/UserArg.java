package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class UserArg implements Arg {

    private final SCG plugin;

    public UserArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getManager().getMsgManager().getMsg("cmdonlyforplayer"));
            return;
        }
        Player p = (Player) sender;
        if (args.length == 1) {
            User killuser = plugin.getManager().getUserManager().getUsers().get(sender.getName());
            p.sendMessage(ChatColor.GOLD + "=========================");
            p.sendMessage(ChatColor.GRAY + "Informacje o twoim koncie:");
            p.sendMessage(ChatColor.GRAY + "Liczba punktow: " + ChatColor.GREEN + killuser.getPoints());
            p.sendMessage(ChatColor.GRAY + "Liczba zabojstw: " + ChatColor.GREEN + killuser.getKills());
            p.sendMessage(ChatColor.GRAY + "Liczba smierci: " + ChatColor.GREEN + killuser.getDeaths());
            if (killuser.getGuild() != null) {
                p.sendMessage(ChatColor.GRAY + "Aktualnie jest w druzynie " + ChatColor.GREEN + killuser.getGuild().getGuildTag());
            } else {
                p.sendMessage(ChatColor.GRAY + "Nie jestes w zadnej druzynie!");

            }
            if (killuser.getDeaths() > 0) {
                p.sendMessage(ChatColor.GRAY + "Liczba zabojstw/liczba smierci (K/D): " + ChatColor.GREEN + ((float) killuser.getKills() / killuser.getDeaths()));

            } else {
                p.sendMessage(ChatColor.GRAY + "Liczba zabojstw/liczba smierci (K/D): " + ChatColor.GREEN + 0);

            }
            p.sendMessage(ChatColor.GOLD + "=========================");
        }

    }

}
