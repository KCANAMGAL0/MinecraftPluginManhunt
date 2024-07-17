package org.manhunt.Utils;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class ItemStackUtil {
    public static ItemStack getNBTItemStack(ItemStack itemStack, String DisplayName, List<String> Lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (DisplayName != null && Lore != null){
            itemMeta.setDisplayName(DisplayName);
            itemMeta.setLore(Lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public static ItemStack getNBTItemStack(ItemStack itemStack,String DisplayName){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!DisplayName.isEmpty()) itemMeta.setDisplayName(DisplayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public static ItemStack getNBTItemStack(ItemStack itemStack, PotionEffect potionEffect){
        if (itemStack.getType() == Material.POTION){
            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
            potionMeta.addCustomEffect(potionEffect,true);
            itemStack.setItemMeta(potionMeta);
        }
        return itemStack;
    }


    public static boolean isEmpty(Inventory inventory, int i){
        boolean b = false;
        try {
            ItemStack itemStack = inventory.getItem(i);
            Material itemType = itemStack.getType();
        } catch (NullPointerException exception){
            b = true;
        } finally {
            return b;
        }
    }

    public static boolean isEmpty(ItemStack i){
        boolean b = false;
        try {
            Material itemType = i.getType();
        } catch (NullPointerException exception){
            b = true;
        } finally {
            return b;
        }
    }
    public static ItemStack getSkull(Player owner){
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        skullMeta.setOwner(owner.getName());
        head.setItemMeta(skullMeta);
        return head;
    }
}
