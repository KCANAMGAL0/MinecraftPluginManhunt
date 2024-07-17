package org.manhunt.player.SelectClass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.manhunt.MhClass.MhClassManager;
import org.manhunt.MhClass.MhClasses;
import org.manhunt.Utils.ItemStackUtil;
import org.manhunt.player.team.MhTeam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassMenuManager implements Listener {
    final String name = ChatColor.AQUA + "职业选择";

    public void openClassMenu(Player player){
        MhTeam mhTeam = new MhTeam();
        MhClassManager mhClassManager = new MhClassManager();
        if (mhTeam.isHunter(player)) {
            Inventory classMenu = Bukkit.createInventory(player,27,name);
            classMenu.setItem(0,ItemStackUtil.getNBTItemStack(new ItemStack(Material.GOLDEN_APPLE,1),MhClasses.Doc.getClassMenuName(),MhClasses.Doc.getLore()));
            classMenu.setItem(1,ItemStackUtil.getNBTItemStack(new ItemStack(Material.LAVA_BUCKET,1),MhClasses.Esc.getClassMenuName(),MhClasses.Esc.getLore()));
            classMenu.setItem(2,ItemStackUtil.getNBTItemStack(new ItemStack(Material.DIAMOND_AXE,1),MhClasses.Lng.getClassMenuName(),MhClasses.Lng.getLore()));
            classMenu.setItem(3,ItemStackUtil.getNBTItemStack(new ItemStack(Material.CACTUS,1),MhClasses.Cac.getClassMenuName(),MhClasses.Cac.getLore()));
            classMenu.setItem(4,ItemStackUtil.getNBTItemStack(new ItemStack(Material.EYE_OF_ENDER,1),MhClasses.Sac.getClassMenuName(),MhClasses.Sac.getLore()));
            classMenu.setItem(5,ItemStackUtil.getNBTItemStack(new ItemStack(Material.PRISMARINE_CRYSTALS,1),MhClasses.Gua.getClassMenuName(),MhClasses.Gua.getLore()));
            classMenu.setItem(6,ItemStackUtil.getNBTItemStack(new ItemStack(Material.BOW,1),MhClasses.Rob.getClassMenuName(),MhClasses.Rob.getLore()));
            player.openInventory(classMenu);
        }else if (mhTeam.isRunner(player)){
            Inventory classMenu = Bukkit.createInventory(player,27,name);
            classMenu.setItem(0,ItemStackUtil.getNBTItemStack(new ItemStack(Material.DIAMOND_PICKAXE,1),MhClasses.Miner.getClassMenuName(),MhClasses.Miner.getLore()));
            classMenu.setItem(1,ItemStackUtil.getNBTItemStack(new ItemStack(Material.REDSTONE,1),MhClasses.Vam.getClassMenuName(),MhClasses.Vam.getLore()));
            classMenu.setItem(2,ItemStackUtil.getNBTItemStack(new ItemStack(Material.ARROW,1),MhClasses.Arc.getClassMenuName(),MhClasses.Arc.getLore()));
            classMenu.setItem(3,ItemStackUtil.getNBTItemStack(new ItemStack(Material.GOLD_CHESTPLATE,1),MhClasses.Pal.getClassMenuName(),MhClasses.Pal.getLore()));
            classMenu.setItem(4,ItemStackUtil.getNBTItemStack(new ItemStack(Material.IRON_BLOCK,1),MhClasses.Irg.getClassMenuName(),MhClasses.Irg.getLore()));
            classMenu.setItem(5,ItemStackUtil.getNBTItemStack(new ItemStack(Material.IRON_SWORD,1),MhClasses.Ass.getClassMenuName(),MhClasses.Ass.getLore()));
            classMenu.setItem(6,ItemStackUtil.getNBTItemStack(new ItemStack(Material.REDSTONE_BLOCK,1),MhClasses.MM.getClassMenuName(),MhClasses.MM.getLore()));

            player.openInventory(classMenu);
        }
    }
    @EventHandler
    public void rightClickOnNetherStar(PlayerInteractEvent e){
        Player player = e.getPlayer();
        final String name = ChatColor.AQUA + "职业选择";
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if (player.getItemInHand().getType().equals(Material.NETHER_STAR)){
                openClassMenu(player);
            }
        }
    }

    @EventHandler
    public void clickOnClassMenu(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getClickedInventory();
        ItemStack clickedItem = e.getCurrentItem();
        MhClassManager mhClassManager = new MhClassManager();
        if (inventory.getTitle().equals(name)){
            e.setCancelled(true);
            if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.Arc.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,"Arc");
            }else if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.Vam.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,"Vam");
            }else if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.Miner.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,"Miner");
            }else if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.Doc.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,"Doc");
            }else if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.Esc.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,"Esc");
            } else if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.Lng.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,"Lng");
            } else if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.Cac.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,"Cac");
            } else if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.Pal.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,"Pal");
            } else if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.Sac.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,"Sac");
            }else if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.Irg.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,"Irg");
            }else if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.Ass.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,"Ass");
            }else if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.Gua.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,MhClasses.Gua.getName());
            }else if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.Rob.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,MhClasses.Rob.getName());
            }else if (clickedItem.getItemMeta().getDisplayName().equals(MhClasses.MM.getClassMenuName())){
                mhClassManager.setPlayerClassInfo(player,MhClasses.MM.getName());
            }
            player.sendMessage("你当前的职业为 " + mhClassManager.getClassStringName(mhClassManager.getPlayerClass(player)));
            player.playSound(player.getLocation(), Sound.NOTE_PLING,1.0F,1.0F);
        }
    }
}
