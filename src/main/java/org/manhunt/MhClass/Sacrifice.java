package org.manhunt.MhClass;

import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.manhunt.Main;
import org.manhunt.player.actionbar.ActionbarManager;

import java.util.HashMap;

public class Sacrifice extends MhClassManager {
    public static HashMap<Player,Long> SummonEnderCrystal;
    static {
        SummonEnderCrystal = new HashMap<>();
    }
    public void SummonEnderCrystal(Player player){
        if (super.getPlayerClass(player).equals("Sac") && player.getWorld().getName().equals("world_the_end")){
            if (SummonEnderCrystal.containsKey(player) && SummonEnderCrystal.get(player) > System.currentTimeMillis()) {
                return;
            }else {
                SummonEnderCrystal.put(player,System.currentTimeMillis() + 75 * 1000L);
                player.sendMessage(ChatColor.GREEN + "你的末影水晶技能已激活!");
                player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT,1.0F,1.0F);
                Location location = new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
                EnderCrystal enderCrystal = (EnderCrystal) player.getWorld().spawnEntity(location, EntityType.ENDER_CRYSTAL);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        enderCrystal.remove();
                        World world = Bukkit.getWorld("world_the_end");
                        for (LivingEntity enderDragon : world.getLivingEntities()){
                            if (enderDragon.getType() == EntityType.ENDER_DRAGON){
                                if (enderDragon.getHealth() + 25 <= enderDragon.getMaxHealth()){
                                    enderDragon.setHealth(enderDragon.getHealth() + 25);
                                }
                            }
                        }
                    }
                }.runTaskLater(Main.getPlugin(Main.class),20 * 30L);
            }
        }
    }

    public boolean isOnCoolDown(Player player){
        return SummonEnderCrystal.containsKey(player) && SummonEnderCrystal.get(player) > System.currentTimeMillis();
    }

    public void Display(Player player){
        ActionbarManager actionbarManager = new ActionbarManager();
        String D = ChatColor.DARK_PURPLE + ChatColor.BOLD.toString();
        if (isOnCoolDown(player)) {
            String s = String.valueOf((SummonEnderCrystal.get(player) - System.currentTimeMillis()) / 1000);
            actionbarManager.sendActionbar(player, ChatColor.GRAY + s + "秒", D + "末影水晶 ");
        }else {
            actionbarManager.sendActionbar(player, ChatColor.GREEN + ChatColor.BOLD.toString() + "✔", D + "末影水晶 ");
        }
    }
}
