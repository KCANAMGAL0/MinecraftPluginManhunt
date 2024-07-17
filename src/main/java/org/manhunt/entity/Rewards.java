package org.manhunt.entity;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.manhunt.Main;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;

import java.util.ArrayList;
import java.util.List;

public class Rewards {

    int Timer = 20;
    World theEnd = Bukkit.getWorld("world_the_end");
    Player target;
    EnderDragon enderDragon;
    Location startLocation;
    Location targetLocation;
    double distance = 0;
    double maxDistance = 75;
    Vector vector;

    public void start(){
        vector = targetLocation.toVector().subtract(startLocation.toVector());
        new BukkitRunnable() {
            @Override
            public void run() {
                Location currentLocation = startLocation.clone().add(vector.clone().multiply(distance));
                if (distance > maxDistance){
                    this.cancel();
                }
                showHitBlockEffect(currentLocation);
                showHitPlayerEffect(currentLocation);
                theEnd.playEffect(currentLocation, Effect.WITCH_MAGIC,0);
                distance+=0.025;
            }
            private void showHitBlockEffect(Location location){
                Block block = theEnd.getBlockAt(location);
                ItemStack itemStack = new ItemStack(Material.ARROW,12);
                if (block.getType() != Material.AIR){
                    dropRandomItem(theEnd,location);
                    this.cancel();
                }
            }

            private void showHitPlayerEffect(Location location){
                ItemStack itemStack = new ItemStack(Material.ARROW,12);
                for (Player player : Bukkit.getOnlinePlayers()){
                    if (player.getWorld().equals(theEnd)){
                        if (player.getLocation().distance(location) <= 1.5){
                            dropRandomItem(theEnd,location);
                            this.cancel();
                            return;
                        }
                    }
                }
            }

        }.runTaskTimer(Main.getPlugin(Main.class),0,1);
    }


    private void dropRandomItem(World world,Location location){
        int a = 0;
        ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE,1);
        ItemStack cooked_beef = new ItemStack(Material.COOKED_BEEF,3);
        ItemStack arrow = new ItemStack(Material.ARROW,12);
        Potion potion = new Potion(PotionType.INSTANT_HEAL,2);
        potion.setSplash(true);
        a = (int) (Math.random() * 100 + 1);
        if (a <= 20){
            world.dropItem(location,gapple);
        }else if (a <= 66){
            world.dropItem(location,arrow);
        }else if (a <= 88){
            world.dropItem(location,cooked_beef);
        }else {
            world.dropItem(location,potion.toItemStack(1));
        }

    }
    public Rewards(EnderDragon enderDragon, Player target){
        this.target = target;
        this.enderDragon = enderDragon;
        this.startLocation = enderDragon.getLocation();
        this.targetLocation = target.getLocation();
    }
}
