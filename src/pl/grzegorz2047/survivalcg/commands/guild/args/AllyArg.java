package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.managers.MsgManager;
import pl.grzegorz2047.survivalcg.managers.Relation;

/**
 * Created by grzegorz2047 on 29.12.2015.
 */
public class AllyArg extends Arg {
    private final SCG plugin;

    public AllyArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(!(sender instanceof Player)) {
            sender.sendMessage(plugin.getManager().getMsgManager().getMsg("cmdonlyforplayer"));
            return;
        }
        Player player = (Player) sender;
        if(args.length>=2){
            String guildToCheck = args[1].toUpperCase();
            Guild guild = plugin.getManager().getGuildManager().getGuilds().get(guildToCheck);
            if(guild == null) {
                sender.sendMessage(plugin.getManager().getMsgManager().getMsg("guilddoesntexists"));
                return;
            }
            User user = plugin.getManager().getUserManager().getUsers().get(p.getName());
            Guild requestingGuild = user.getGuild();
            if(requestingGuild == null) {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("notinguild"));
                return;
            }
            if(!requestingGuild.getLeader().equals(p.getName())) {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("playernotleader"));
                return;
            }


            if(guild.equals(requestingGuild)){
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("allyyourselferror"));
                return;
            }
            Player leader = Bukkit.getPlayer(guild.getLeader());
            if(leader == null){
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("leadernotonline"));
                return;
            }
            for(Relation r : plugin.getManager().getGuildManager().getPendingRelations()) {
                if(r.getWithWho().equals(requestingGuild.getGuildName())) {

                    Bukkit.broadcastMessage(plugin.getManager().getMsgManager().getMsg("broadcast-ally")
                            .replace("{GUILD1}", guild.getGuildName())
                            .replace("{GUILD2}", requestingGuild.getGuildName()));
                    r.setExpired(true);
                    plugin.getManager().getMysqlManager().getRelationQuery().addRelation(requestingGuild, guild);
                    return;
                }
            }
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("sentallyrequest"));
            plugin.getManager().getGuildManager().requestAlly(requestingGuild, guild);
        }else{
            sender.sendMessage("/g ally <guild>");
        }
    }
}
