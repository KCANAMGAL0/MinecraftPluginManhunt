package org.manhunt.mining;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.manhunt.MhClass.MhClassManager;
import org.manhunt.player.playerManager;

import java.util.Random;

public class oreBreak implements Listener {
    @EventHandler
    public void orebreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        Material material = block.getType();
        Player player = e.getPlayer();
        Location location = block.getLocation();
        World world = block.getWorld();
        MhClassManager mhClassManager = new MhClassManager();
        //player.sendMessage(material.toString());
        if (material.equals(Material.GLOWING_REDSTONE_ORE)) {
            new playerManager().addEffect(player,PotionEffectType.REGENERATION,100,2,false);
            player.giveExp(6);
        }else if (material.equals(Material.EMERALD_ORE) && !e.isCancelled()) {
            int rd = new Random().nextInt(100);
            String getFromEmeraldOre = "NULL";
            if (rd <= 22){
                getFromEmeraldOre = "金苹果";
                setItemDrop(block,world,location,Material.GOLDEN_APPLE,1);
            }else if (rd <= 44){
                getFromEmeraldOre = "箭";
                setItemDrop(block,world,location,Material.ARROW,10);
            }else if (rd <= 66) {
                getFromEmeraldOre = "牛排";
                setItemDrop(block,world,location,Material.COOKED_BEEF,2);
            } else if (rd <= 77) {
                getFromEmeraldOre = "甘蔗";
                setItemDrop(block,world,location,Material.SUGAR_CANE,2);
            } else if (rd <= 88){
                getFromEmeraldOre = "兔子脚";
                setItemDrop(block,world,location,Material.RABBIT_FOOT,2);
            } else {
                getFromEmeraldOre = "玻璃瓶";
                setItemDrop(block,world,location,Material.GLASS_BOTTLE,3);
            }
            if (mhClassManager.getPlayerClass(player).equals("Miner")){
                setItemDrop(block,world,location,Material.DIAMOND,1);
                player.sendMessage(ChatColor.GREEN + "你的" + ChatColor.RED + ChatColor.BOLD + " 幸运 " + ChatColor.GREEN + "技能已激活!");
            }
            player.sendMessage(ChatColor.GRAY + "你通过挖掘" +ChatColor.RED +" 绿宝石矿 " +ChatColor.GRAY + "发现了 " + ChatColor.RED + getFromEmeraldOre);
            player.giveExp(9);
        }else if (material.equals(Material.IRON_ORE) && !e.isCancelled()) {
            player.giveExp(2);
            setItemDrop(block,world,location,Material.IRON_INGOT,3);
        }else if (material.equals(Material.GOLD_ORE) && !e.isCancelled()) {
            player.giveExp(3);
            int rd = (int)(Math.random()*100+1);
            if (rd <= 25 || mhClassManager.getPlayerClass(player).equals("Miner")){
                if (mhClassManager.getPlayerClass(player).equals("Miner")){
                    player.sendMessage(ChatColor.GREEN + "你的" + ChatColor.RED + ChatColor.BOLD + " 幸运 " + ChatColor.GREEN + "技能已激活!");
                }
                setItemDrop(block,world,location,Material.GOLD_INGOT,2);
            }else {
                setItemDrop(block,world,location,Material.GOLD_INGOT,1);
            }
        }else if (material.equals(Material.DIAMOND_ORE) && !e.isCancelled()) {
            player.giveExp(7);
        }else if (material.equals(Material.LAPIS_ORE) && !e.isCancelled()) {
            player.giveExp(6);
        }else if (material.equals(Material.COAL_ORE) && !e.isCancelled()){
            player.giveExp(3);
        }
    }
    public void setItemDrop(Block block,World world,Location dropLoc,Material dropItem,int count){
        block.setType(Material.AIR);
        ItemStack itemDrop = new ItemStack(dropItem,count);
        Location finalDropLoc = dropLoc.add(0.3,0.3,0.3);
        world.dropItem(finalDropLoc,itemDrop);
    }
}
