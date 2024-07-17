package org.manhunt.mining;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.manhunt.player.team.MhTeam;

import java.util.Random;

public class stoneBreak implements Listener {
    @EventHandler
    public void stonebreak(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            Block block = event.getBlock();
            Material material = block.getType();
            Player player = event.getPlayer();
            Location location = block.getLocation();
            World world = block.getWorld();
            int blockY = block.getY();
            MhTeam mhTeam = new MhTeam();
            if (block.getWorld().getName().equals("world")) {
                if (material.equals(Material.STONE) && !event.isCancelled()) {
                    block.setType(Material.COBBLESTONE);
                    int isSetOres = (int) (Math.random() * 100 + 1);
                    if (blockY > 32 && blockY <= 63) {
                        int heightLevel = 3;
                        getSummoner(player, heightLevel, world, location, block, event);
                        //player.sendMessage("3");
                    } else if (blockY > 16 && blockY <= 32) {
                        int heightLevel = 2;
                        getSummoner(player, heightLevel, world, location, block, event);
                        //player.sendMessage("2");
                    } else if (blockY <= 16) {
                        int heightLevel = 1;
                        getSummoner(player, heightLevel, world, location, block, event);
                        //player.sendMessage("1");
                    }
                }
            }
        }
    }
    public void setOreRUNNER(int heightlvl,World world,Location location,Block block,BlockBreakEvent event){
        int isSetOres = (int)(Math.random() *1000 +1);
        if (heightlvl == 3 && isSetOres <= 107) {
            world.playSound(location, Sound.ORB_PICKUP,1.0F,1.0F);
            event.setCancelled(true);
                block.setType(Material.IRON_ORE);
        }else if (heightlvl == 2 && isSetOres <= 137 ){
            world.playSound(location, Sound.ORB_PICKUP,1.0F,1.0F);
            event.setCancelled(true);
            int rd = (int) (Math.random() * 100 + 1);
            if (rd <= 40) block.setType(Material.IRON_ORE);
            else if (rd <= 70) block.setType(Material.GOLD_ORE);
            else if (rd <= 90) block.setType(Material.LAPIS_ORE);
            else if (rd <= 100) block.setType(Material.EMERALD_ORE);
        }else if (heightlvl == 1 && isSetOres <= 177 ){
            world.playSound(location, Sound.ORB_PICKUP,1.0F,1.0F);
            event.setCancelled(true);
            int rd = (int) (Math.random() * 100 + 1);
            if (rd <= 24) {
                block.setType(Material.IRON_ORE);
            } else if (rd <= 47) {
                block.setType(Material.GOLD_ORE);
            } else if (rd <= 52) {
                block.setType(Material.REDSTONE_ORE);
            } else if (rd <= 71) {
                block.setType(Material.LAPIS_ORE);
            } else if (rd <= 75) {
                block.setType(Material.EMERALD_ORE);
            } else if (rd <= 100) {
                block.setType(Material.DIAMOND_ORE);
            }
        }
    }
    public void setOreHUNTER(int heightlvl,World world,Location location,Block block,BlockBreakEvent event){
        MhTeam mhTeam = new MhTeam();
        int a = (mhTeam.getHunterCount() - mhTeam.getRunnerCount()) + 1;
        int isSetOres = (int)(Math.random()* 1000+1);
        if (heightlvl == 3 && isSetOres <= 99) {
            world.playSound(location, Sound.ORB_PICKUP,1.0F,1.0F);
            event.setCancelled(true);
            block.setType(Material.IRON_ORE);
        }else if (heightlvl == 2 && isSetOres <= 122 ){
            world.playSound(location, Sound.ORB_PICKUP,1.0F,1.0F);
            event.setCancelled(true);
            int rd = (int) (Math.random() * 100 + 1);
            if (rd <= 45) {
                block.setType(Material.IRON_ORE);
            } else if (rd <= 75) {
                block.setType(Material.GOLD_ORE);
            } else if (rd <= 90) {
                block.setType(Material.LAPIS_ORE);
            } else if (rd <= 100) {
                block.setType(Material.EMERALD_ORE);
            }
        }else if (heightlvl == 1 && isSetOres <= 155 ){
            world.playSound(location, Sound.ORB_PICKUP,1.0F,1.0F);
            event.setCancelled(true);
            int rd = new Random().nextInt(100);
            if (rd <= 29) {
                block.setType(Material.IRON_ORE);
            } else if (rd <= 54) {
                block.setType(Material.GOLD_ORE);
            } else if (rd <= 59) {
                block.setType(Material.REDSTONE_ORE);
            } else if (rd <= 74) {
                block.setType(Material.LAPIS_ORE);
            } else if (rd <= 81) {
                block.setType(Material.EMERALD_ORE);
            } else {
                block.setType(Material.DIAMOND_ORE);
            }
        }else if (heightlvl == 1 && isSetOres <= 155 + mhTeam.getRunnerCount() * 5){
            world.playSound(location, Sound.ORB_PICKUP,1.0F,1.0F);
            event.setCancelled(true);
            block.setType(Material.DIAMOND_ORE);
        }
    }
    public void getSummoner(Player player,int heightLevel,World world,Location location,Block block,BlockBreakEvent event){
        MhTeam mhTeam = new MhTeam();
        if (mhTeam.getMhTeam(player).equals("RUNNER")){
            setOreRUNNER(heightLevel,world,location,block,event);
        }else if (mhTeam.getMhTeam(player).equals("HUNTER")) {
            setOreHUNTER(heightLevel,world,location,block,event);
        }
    }
}


