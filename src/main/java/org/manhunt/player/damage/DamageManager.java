package org.manhunt.player.damage;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderDragon;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.manhunt.Main;
import org.manhunt.MhClass.*;
import org.manhunt.entity.DragonBreath;
import org.manhunt.entity.Rewards;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;
import org.bukkit.util.Vector.*;


public class DamageManager implements Listener {
    @EventHandler
    public void getSpecialDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && !e.isCancelled()) {
            Player player = (Player) e.getEntity();
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (player.getWorld().getName().equals("world_the_end"))
                    e.setDamage(e.getDamage() * 0.5); //在末地时摔落伤害减少50%
                if (new MhClassManager().getPlayerClass(player).equals(MhClasses.Rob.getName()) && Robber.f.containsKey(player) && Robber.f.get(player)){
                    e.setCancelled(true);
                    Robber.f.put(player,false);
                }
            } else if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                e.setDamage(e.getDamage() * 0.5);  //爆炸(tnt,床)伤害减少60%
            }
        }
    }
    @EventHandler
    public void DamageOnEnderDragon(EntityDamageByEntityEvent e) {
        MhTeam mhTeam = new MhTeam();
        if (!e.isCancelled() && e.getEntity() instanceof EnderDragon) {
            EnderDragon enderDragon = (EnderDragon) e.getEntity();
            if (e.getDamager() instanceof Player) {
                Player player = (Player) e.getDamager();
                //player.sendMessage(String.format("%s/%s", ((EnderDragon) e.getEntity()).getHealth(), ((EnderDragon) e.getEntity()).getMaxHealth()));
                if (mhTeam.getMhTeam(player).equals("HUNTER")) {
                    e.setCancelled(true);
                } else {
                    for (Player player1 : mhTeam.getHunterPlayers()) {
                        player1.sendTitle(" ", ChatColor.RED + "末影龙正在受到攻击");
                        player1.playSound(player.getLocation(), Sound.ENDERDRAGON_HIT, 1.0F, 1.0F);
                    }
                }
            } else if (e.getDamager() instanceof Projectile) {
                int x;
                Projectile projectile = (Projectile) e.getDamager();
                Player player = (Player) projectile.getShooter();
                //player.sendMessage(((EnderDragon) e.getEntity()).getHealth() + "/" + ((EnderDragon) e.getEntity()).getMaxHealth());
                if (mhTeam.getMhTeam(player).equals("HUNTER")) {
                    e.setCancelled(true);
                } else {
                    ((CraftEnderDragon) enderDragon).getHandle().setGoalTarget(((CraftLivingEntity) player).getHandle());
                    x = (int) ((Math.random() * 1000 + 1) - ((enderDragon.getMaxHealth() - enderDragon.getHealth()) / 2));
                    if (x <= 125) {
                        if (org.manhunt.entity.enderDragon.DragonBreathTimer < System.currentTimeMillis()) {
                            player.sendTitle("", ChatColor.DARK_RED + "末影龙向你发射了龙息弹");
                            org.manhunt.entity.enderDragon.DragonBreathTimer = System.currentTimeMillis() + (10 * 1000L);
                            new DragonBreath(enderDragon, player).start();
                        }
                    }else if (x >= 500){
                        if (org.manhunt.entity.enderDragon.RewardTimer.containsKey(player) && org.manhunt.entity.enderDragon.RewardTimer.get(player) > System.currentTimeMillis()){

                        }else {
                            player.sendMessage(ChatColor.GREEN + "末影龙的身上似乎掉落了什么物资");
                            player.playSound(player.getLocation(),Sound.ORB_PICKUP,1.0F,1.0F);
                            org.manhunt.entity.enderDragon.RewardTimer.put(player,System.currentTimeMillis() + 30 * 1000L);
                            new Rewards(enderDragon,player).start();
                        }

                    }
                    if (new MhClassManager().getPlayerClass(player).equals("Arc")) {
                        if (projectile instanceof Arrow) {
                            if (((Arrow) projectile).isCritical()) {
                                e.setDamage(e.getDamage() * 1.2);
                                player.sendMessage(ChatColor.GREEN + "你的强化箭矢命中了" + ChatColor.AQUA + "末影龙");
                                ItemStack returnArrow = new ItemStack(Material.ARROW,1);
                                player.getInventory().addItem(returnArrow);
                            }
                        }
                    }
                    for (Player player1 : mhTeam.getHunterPlayers()) {
                        player1.sendTitle(" ", ChatColor.RED + "末影龙正在受到攻击");
                        player1.playSound(player.getLocation(), Sound.ENDERDRAGON_HIT, 1.0F, 1.0F);
                    }
                }
            }
        }
    }
    @EventHandler
    public void DamageByEnderPearl(PlayerTeleportEvent e){
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL){
            e.getPlayer().damage(5);
        }
    }
    @EventHandler
    public void FireTickReset(EntityDamageByEntityEvent e){
        if (!e.isCancelled() && e.getDamager() instanceof Player && e.getEntity() instanceof Player){
            Player damager = (Player) e.getDamager();
            if (damager.getItemInHand().containsEnchantment(Enchantment.FIRE_ASPECT)){

                switch (damager.getItemInHand().getEnchantments().get(Enchantment.FIRE_ASPECT)){
                    case 1:
                        //e.getEntity().sendMessage(String.valueOf(e.getEntity().getFireTicks()));
                        Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {
                            @Override
                            public void run() {
                                e.getEntity().setFireTicks(Math.max(e.getEntity().getFireTicks(), 60));
                            }
                        },1L);
                        break;
                    case 2:
                        Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {
                            @Override
                            public void run() {
                                e.getEntity().setFireTicks(Math.max(e.getEntity().getFireTicks(), 120));
                            }
                        },1L);
                        break;
                }
            }
        }
    }

}

