package pl.grzegorz2047.api.playersclasses;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by Grzegorz2047. 14.09.2015.
 */
public abstract class PlayerClass {

    protected int price;
    protected int maxLevel;


    protected HashMap<Integer, CustomInventory> inventory;

    public PlayerClass() {
        this.maxLevel = 0;
        inventory = new HashMap<Integer, CustomInventory>();
        inventory.put(0, new CustomInventory());
        inventory.put(1, new CustomInventory());
        inventory.put(2, new CustomInventory());
        inventory.put(3, new CustomInventory());
        inventory.put(4, new CustomInventory());
        inventory.put(5, new CustomInventory());
        this.addItemsToInventory();
    }

    protected ItemStack createItem(Material mat, int amount) {
        return new ItemStack(mat, amount);
    }

    public void giveClass(Player p, int lvl) {
        p.getInventory().setContents(inventory.get(lvl).getInventory().getContents().clone());
        p.getInventory().setArmorContents(inventory.get(lvl).getArmorContents().clone());
        p.updateInventory();
    }

    protected void addItemsToInventory() {
        //inventory.get(0).addItem(new ItemStack(Material.POTION,3),new ItemStack(Material.NETHER_WARTS,1),new ItemStack(Material.SPECKLED_MELON,1),new ItemStack(Material.BREWING_STAND,1));
        //example
    }

}
