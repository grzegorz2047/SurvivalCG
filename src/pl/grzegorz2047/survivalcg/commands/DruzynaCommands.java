package pl.grzegorz2047.survivalcg.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.grzegorz2047.survivalcg.SurvivalCG;
import pl.grzegorz2047.survivalcg.group.Group;
import pl.grzegorz2047.survivalcg.user.SurvUser;

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
                p.sendMessage(ChatColor.GRAY+"Lista dostepnych argumentow:");
                p.sendMessage(ChatColor.GREEN+"/druzyna stworz <nazwa> "+ChatColor.GRAY+"- Tworzy nowa druzyne");
                p.sendMessage(ChatColor.GREEN+"/druzyna usun "+ChatColor.GRAY+"- Usuwa druzyne");
                p.sendMessage(ChatColor.GREEN+"/druzyna wyrzuc <nick> "+ChatColor.GRAY+"- Wyrzuca gracza z druzyny");
                p.sendMessage(ChatColor.GREEN+"/druzyna zapros <nick> "+ChatColor.GRAY+"- Zaprasza gracza do druzyny");
                p.sendMessage(ChatColor.GREEN+"/druzyna dom "+ChatColor.GRAY+"- Teleportuje na spawn druzyny");
                p.sendMessage(ChatColor.GREEN+"/druzyna opusc "+ChatColor.GRAY+"- Opuszczasz obecna druzyne");
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("stworz")) {
                    SurvUser user = plugin.getPlayers().getUsers().get(p.getName());
                    if (!user.getGroup().equals("")) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Jestes juz w druzynie " + user.getGroup() + "!");
                        return true;
                    }
                    String tag = args[1].toUpperCase();
                    if (tag.length() < 3 || tag.length() > 6) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Tag druzyny musi miec conajmniej 3 znaki i maxymalnie 6 znakow!");
                        return true;
                    }
                    if (!tag.matches("[0-9a-zA-Z]*")) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nazwa druzyny posiada niewlasciwe znaki");
                        return true;
                    }
                    boolean exists = plugin.getMysql().existsGroup(tag);
                    if (exists) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Taka druzyna juz istnieje!");
                        return true;
                    }
                    if (p.getLocation().distance(p.getWorld().getSpawnLocation()) < 120) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Jestes za blisko spawnu!");
                        return true;
                    }

                    Group group = new Group(tag, p.getName(), p.getLocation(), System.currentTimeMillis());
                    plugin.getGroups().getGroups().put(tag, group);
                    plugin.getMysql().insertGroup(group);
                    plugin.getSc().getTeam(group.getDisplaytag()).setPrefix(group.getDisplaytag());
                    plugin.getSc().getTeam(group.getDisplaytag()).setDisplayName(group.getDisplaytag());
                    plugin.getSc().getTeam(group.getDisplaytag()).addEntry(p.getName());
                    user.setGroup(group.getGroupname());
                    plugin.getMysql().updatePlayer(user);

                    p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Twoja druzyna o tagu " + tag + " zostala stworzona!");
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
                    if (g.getMembers().size() == 3) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Posiadasz maksymalna liczbe osob w druzynie (3)!");
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
                    String groupname = args[1];

                    SurvUser user = plugin.getPlayers().getUsers().get(p.getName());
                    if (!user.getGroup().equalsIgnoreCase("")) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Musisz opusic obecna grupe zanim dolaczysz do innej!");
                        return true;
                    }
                    Group g = plugin.getGroups().getGroups().get(groupname);
                    if (g == null) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Taka gildia nie istnieje badz nie ma jej zadnych czlonkow!");
                        return true;
                    }
                    if (!g.getWaiting().contains(p.getName())) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Twoje zaproszenie nie istnieje!");
                        return true;
                    }
                    g.getMembers().add(p.getName());
                    user.setGroup(g.getGroupname());
                    plugin.getSc().getTeam(g.getDisplaytag()).addEntry(p.getName());
                    plugin.getMysql().updatePlayer(user);
                    g.getWaiting().remove(p.getName());
                    p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Pomyslnie dolaczyles do druzyny " + g.getGroupname());
                    return true;
                }
                if (args[0].equalsIgnoreCase("odrzuc")) {

                }
                if (args[0].equalsIgnoreCase("wyrzuc")) {
                    String whoKick = args[1];
                    SurvUser user = plugin.getPlayers().getUsers().get(p.getName());

                    if (user.getGroup().equalsIgnoreCase("")) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie jestes w zadnej druzynie!");
                        return true;
                    }
                    Group g = plugin.getGroups().getGroups().get(user.getGroup());
                    if (!g.getLeader().equalsIgnoreCase(p.getName())) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie jestes liderem tej druzyny");
                        return true;
                    }
                    if (g.getLeader().equalsIgnoreCase(whoKick)) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie mozesz siebie wyrzucic z druzyny!");
                        return true;
                    }
                    if (!g.getMembers().contains(whoKick)) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Ten gracz nie jest w twojej druzynie!");
                        return true;
                    }
                    g.getMembers().remove(whoKick);
                    Player fp = Bukkit.getPlayer(whoKick);
                    if (fp != null) {
                        SurvUser fuser = plugin.getPlayers().getUsers().get(fp.getName());
                        fuser.setGroup("");
                        plugin.getMysql().updatePlayer(fuser);
                        plugin.getSc().getTeam(g.getDisplaytag()).removeEntry(whoKick);
                    } else {
                        SurvUser fuser = new SurvUser(whoKick, false);
                        plugin.getMysql().updateGuildPlayer(fuser);
                    }

                    p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Gracz zostal pomyslnie wyrzucony z druzyny!");
                    return true;
                }

                p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Podales niepoprawna liczbe argumentow!");
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("usun")) {
                    SurvUser user = plugin.getPlayers().getUsers().get(p.getName());

                    if (user.getGroup().equalsIgnoreCase("")) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie jestes w zadnej druzynie!");
                        return true;
                    }
                    Group g = plugin.getGroups().getGroups().get(user.getGroup());
                    if (!g.getLeader().equalsIgnoreCase(p.getName())) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie jestes liderem tej druzyny");
                        return true;
                    }
                    for(String member: g.getMembers()){
                        if(Bukkit.getPlayer(member) != null){
                            SurvUser fuser = plugin.getPlayers().getUsers().get(member);
                            fuser.setGroup("");
                            plugin.getMysql().updatePlayer(fuser);
                            plugin.getSc().getTeam(g.getDisplaytag()).removeEntry(member);
                        }else {
                            SurvUser fuser = new SurvUser(member, false);
                            plugin.getMysql().updateGuildPlayer(fuser);
                        }
                    }
                    plugin.getSc().getTeam(g.getDisplaytag()).unregister();
                    plugin.getMysql().deleteGroup(g);
                    plugin.getGroups().getGroups().remove(user.getGroup());


                    p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Pomyslnie usunales druzyne!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("opusc")) {
                    SurvUser user = plugin.getPlayers().getUsers().get(p.getName());

                    if (user.getGroup().equalsIgnoreCase("")) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie jestes w zadnej druzynie!");
                        return true;
                    }
                    Group g = plugin.getGroups().getGroups().get(user.getGroup());
                    if (g.getLeader().equalsIgnoreCase(p.getName())) {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Jestes liderem tej druzyny! Aby wyjsc z druzyny musisz ja usunac!");
                        return true;
                    }
                    plugin.getSc().
                            getTeam(g.
                                    getDisplaytag()).
                            removeEntry(user.getUsername());
                    boolean isSomebody = false;
                    for (String member : g.getMembers()) {
                        if (Bukkit.getPlayer(member) != null) {
                            isSomebody = true;
                        }
                    }
                    if (!isSomebody) {
                        plugin.getSc().getTeam(g.getDisplaytag()).unregister();
                        plugin.getGroups().getGroups().remove(user.getGroup());
                    }
                    user.setGroup("");
                    plugin.getMysql().updatePlayer(user);
                    p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Pomyslnie opusciles druzyne!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("dom")) {
                    SurvUser user = plugin.getPlayers().getUsers().get(p.getName());
                    if (!user.getGroup().equalsIgnoreCase("")) {
                        final Location loc = p.getLocation();
                        final String username = user.getUsername();
                        Group g = plugin.getGroups().getGroups().get(user.getGroup());
                        if (g != null) {
                           // System.out.print("g nie jest null");
                            p.teleport(g.getHome());
                        }
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Zostales przeteleportowany!");

                    } else {
                        p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie jestes w druzynie!");
                    }

                } else {
                    p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Podales niepoprawna liczbe argumentow!");
                }
            } else {
                p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Podales niepoprawna liczbe argumentow!");
            }
        }

        return true;
    }
}
