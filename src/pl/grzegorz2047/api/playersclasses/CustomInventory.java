package pl.grzegorz2047.api.playersclasses;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Grzegorz2047. 28.09.2015.
 */
public class CustomInventory {

    private Inventory inventory;

    private ItemStack[] contents;
    private ItemStack[] armorContents;

    public CustomInventory() {
        this.contents = new ItemStack[36];
        this.armorContents = new ItemStack[4];
        this.inventory = Bukkit.createInventory(null, InventoryType.PLAYER);
        contents = inventory.getContents();
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public ItemStack[] getArmorContents() {
        return armorContents;
    }


    public Inventory getInventory() {
        return inventory;
    }

    public void setBoots(ItemStack boots){
        this.armorContents[0] = boots;
    }

    public void setLeggings(ItemStack leggings){
        this.armorContents[1] = leggings;
    }
    public void setChestplate(ItemStack chestplate){
        this.armorContents[2] = chestplate;
    }
    public void setHelmet(ItemStack helmet){
        this.armorContents[3] = helmet;
    }
}
