package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.api.util.ItemGUI;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.managers.MsgManager;
import pl.grzegorz2047.survivalcg.managers.MysqlManager;

import java.util.Arrays;
import java.util.List;

/**
 * Created by grzegorz2047 on 29.12.2015.
 */
public class ItemsArg implements Arg {
    private final SCG plugin;

    public ItemsArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(plugin.getManager().getMsgManager().getMsg("cmdonlyforplayer"));
            return;
        }

        Player player = (Player) sender;
        List<ItemStack> reqitems = plugin.getManager().getSettingsManager().getReqItems();
        if(reqitems.size() > 0) {
            int inventorySize = 9;

            if(reqitems.size() > 9) {
                inventorySize = 18;
            }
            else if(reqitems.size() > 18) {
                inventorySize = 27;
            }
            else if(reqitems.size() > 27) {
                inventorySize = 36;
            }
            else if(reqitems.size() > 36) {
                inventorySize = 45;
            }
            else if(reqitems.size() > 45) {
                inventorySize = 54;
            }

            ItemGUI itemsGUI = new ItemGUI(MsgManager.noprefmsg("gui-items"), inventorySize, plugin);
            for(ItemStack item : reqitems) {
                ItemStack cloned = item.clone();
                ItemMeta meta = cloned.getItemMeta();

                int amount = getAmount(player, cloned);

                if(amount < cloned.getAmount()) {
                    meta.setLore(Arrays.asList(
                            ChatColor.RED + "" + amount + "/" + cloned.getAmount()
                    ));
                } else {
                    meta.setLore(Arrays.asList(
                            ChatColor.GREEN + "" + amount + "/" + cloned.getAmount()
                    ));
                }
                cloned.setItemMeta(meta);

                itemsGUI.addItem(cloned, new ItemGUI.ItemGUIClickEventHandler() {
                    @Override
                    public void handle(ItemGUI.ItemGUIClickEvent event) {
                        event.getPlayer().closeInventory();
                    }
                });
            }
            player.openInventory(itemsGUI.getInventory());
        }
    }

    private int getAmount(Player player, ItemStack item) {
        int amount = 0;

        for(ItemStack i : player.getInventory().getContents()) {
            if(i != null && i.isSimilar(item)) {
                amount += i.getAmount();
            }
        }

        return amount;
    }

}
