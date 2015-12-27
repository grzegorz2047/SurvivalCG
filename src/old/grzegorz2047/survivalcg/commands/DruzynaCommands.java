package old.grzegorz2047.survivalcg.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import old.grzegorz2047.survivalcg.group.Group;
import old.grzegorz2047.survivalcg.user.SurvUser;

/**
 * Created by grzeg on 20.12.2015.
 */
public class DruzynaCommands implements CommandExecutor {

    private final SurvivalCG plugin;

    public DruzynaCommands(SurvivalCG plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("druzyna")) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage(" ");
                p.sendMessage(ChatColor.GRAY+"Lista dostepnych argumentow:");
                p.sendMessage(ChatColor.GREEN+"/druzyna stworz <nazwa> "+ChatColor.GRAY+"- Tworzy nowa druzyne");
                p.sendMessage(ChatColor.GREEN+"/druzyna zaloz <nazwa> "+ChatColor.GRAY+"- Tworzy nowa druzyne");
                p.sendMessage(ChatColor.GREEN+"/druzyna usun "+ChatColor.GRAY+"- Usuwa druzyne");
                p.sendMessage(ChatColor.GREEN+"/druzyna wyrzuc <nick> "+ChatColor.GRAY+"- Wyrzuca gracza z druzyny");
                p.sendMessage(ChatColor.GREEN+"/druzyna zapros <nick> "+ChatColor.GRAY+"- Zaprasza gracza do druzyny");
                p.sendMessage(ChatColor.GREEN+"/druzyna dom "+ChatColor.GRAY+"- Teleportuje na spawn druzyny");
                p.sendMessage(ChatColor.GREEN+"/druzyna opusc "+ChatColor.GRAY+"- Opuszczasz obecna druzyne");
                p.sendMessage(" ");

            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("stworz") || args[0].equalsIgnoreCase("zaloz")) {
                    boolean created = plugin.getGroups().createGroup(p,args[1].toUpperCase());
                    if(created){
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Twoja druzyna zostala stworzona!");
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("zapros")) {
                    SurvUser inviter = plugin.getPlayers().getUsers().get(p.getName());
                    if (inviter.getGroup().equalsIgnoreCase("")) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie jestes w zadnej gildii!");
                        return true;
                    }
                    Group g = plugin.getGroups().getGroups().get(inviter.getGroup());
                    if (!g.getLeader().equalsIgnoreCase(p.getName())) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Tylko liderzy druzyn moga zapraszac graczy!");
                        return true;
                    }
                    if (g.getMembers().size() == 8) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Posiadasz maksymalna liczbe osob w druzynie (8)!");
                        return true;
                    }
                    String friend = args[1];
                    if (g.getWaiting().contains(friend)) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Ten gracz zostal juz zaproszony!");
                        return true;
                    }
                    Player fp = Bukkit.getPlayer(friend);
                    if (fp != null) {
                        SurvUser fuser = plugin.getPlayers().getUsers().get(fp.getName());
                        if (!fuser.getGroup().equalsIgnoreCase("")) {
                            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Ten osobnik jest juz w jakiejs druzynie!");
                            return true;
                        } else {
                            g.getWaiting().add(friend);
                            fp.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Gracz " + g.getLeader() + " wyslal ci zaproszenie do druzyny " + g.getDisplaytag() + "!");
                            fp.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Aby zaakceptowac wpisz /druzyna akceptuj " + g.getGroupname());
                            fp.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Aby odmowic wpisz /druzyna odrzuc " + g.getGroupname());
                            p.sendMessage(plugin.getPrefix()+"Zaproszenie zostalo wyslane do gracza o nicku " + friend);
                            return true;
                        }
                    } else {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Ten gracz nie jest obecnie na serwerze!");
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("akceptuj")) {
                    String groupname = args[1].toUpperCase();
                    boolean accepted = plugin.getGroups().addToGroup(p, groupname);
                    p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Pomyslnie dolaczyles do druzyny " + groupname);
                    Bukkit.broadcastMessage(plugin.getPrefix()+"Gracz "+ChatColor.RED+" dolaczyl do druzyny "+ChatColor.RED+groupname+ChatColor.GRAY+"!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("odrzuc")) {

                }
                if (args[0].equalsIgnoreCase("wyrzuc")) {
                    String whoKick = args[1];
                    boolean kicked = plugin.getGroups().removeFromGroup(p, whoKick);
                    if(kicked){
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Gracz zostal pomyslnie wyrzucony z druzyny!");
                    }
                    return true;
                }
                p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Cos zle wpisales! Wpisz /druzyna po wiecej info");
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("usun")) {
                    boolean deleted = plugin.getGroups().deleteGroup(p, false);
                    if(deleted){
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Pomyslnie usunales druzyne!");
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("opusc")) {
                    boolean left = plugin.getGroups().leaveGroup(p);
                    if(left){
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Pomyslnie opusciles druzyne!");
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("dom")) {
                    plugin.getGroups().requestTeleportHome(p);
                } else {
                    p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Cos zle wpisales! Wpisz /druzyna po wiecej info");
                }
            } else {
                p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Cos zle wpisales! Wpisz /druzyna po wiecej info");
            }
        }

        return true;
    }
}
