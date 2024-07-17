package org.manhunt.entity;

import jdk.nashorn.internal.ir.SplitReturn;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderDragon;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.manhunt.Main;
import org.manhunt.MhClass.MhClasses;
import org.manhunt.Utils.BossbarUtil;
import org.manhunt.game.TheNether;
import org.manhunt.game.gameManager;
import org.manhunt.player.nametag.NameTagManager;
import org.manhunt.player.team.MhTeam;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Random;


public class enderDragon implements Listener {
    public static Long DragonBreathTimer = 0L;
    public static HashMap<Player,Long> RewardTimer;
    static {
        RewardTimer = new HashMap<>();
    }
    Vector vector;

    @EventHandler
    public void DamageByEnderDragon(EntityDamageByEntityEvent e) {
        if (!e.isCancelled() && (e.getEntity() instanceof Player && e.getDamager() instanceof EnderDragon)) {
            if (!(e.getCause() == EntityDamageEvent.DamageCause.FALL)) {
                e.setCancelled(true);
                Player player = (Player) e.getEntity();
                if (new MhTeam().getMhTeam(player).equals("RUNNER")) {
                    EnderDragon enderDragon = (EnderDragon) e.getDamager();
                    player.damage(5);
                    player.setVelocity(enderDragon.getLocation().getDirection().multiply(-4.5).add(new Vector(0, 1.5, 0)));
                }
            }
        }else if (!e.isCancelled() && (e.getEntity() instanceof EnderDragon && e.getDamager() instanceof Player)){
            if (new MhTeam().getMhTeam(((Player) e.getDamager()).getPlayer()).equals("HUNTER")) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void KillEnderDragon(EntityDeathEvent e) {
        if (e.getEntity() instanceof EnderDragon) {
            new gameManager().gameOver(false);
        }
    }

    public void DragonBreath(EnderDragon enderDragon){
        double[] a = new double[]{0,0},b = new double[]{0,0};
        //Location location = enderDragon.getLocation();
        a[0] = enderDragon.getLocation().getX();
        a[1] = enderDragon.getLocation().getZ();
        MhTeam mhTeam = new MhTeam();
        for (Player player : mhTeam.getRunnerPlayers()){
            b[0] = player.getLocation().getX();
            b[1] = player.getLocation().getZ();
            for (int i=0;i<=1;i++){
                if (((a[i]-b[i])*(a[i]*b[i])) < 10){
                    new DragonBreath(enderDragon,player).start();
                }
            }
        }
    }
    public void Summon(Location location){
        World theEnd = Bukkit.getWorld("world_the_end");
        theEnd.getChunkAt(location).load();
        EnderDragon enderDragon = (EnderDragon) theEnd.spawnEntity(location, EntityType.ENDER_DRAGON);
        MhTeam mhTeam = new MhTeam();
        if (new TheNether().ReduceEnderDragonHP) enderDragon.setMaxHealth((200 + mhTeam.getRunnerCount() * 50) * 0.9);
        else enderDragon.setMaxHealth(200 + mhTeam.getRunnerCount() * 50);
        enderDragon.setHealth(enderDragon.getMaxHealth());
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!enderDragon.isDead()) {
                    vector = new Vector(enderDragon.getLocation().getX() - 30, 0, enderDragon.getLocation().getZ());
                    if (getDistance(location, enderDragon.getLocation()) >= 50) {
                        enderDragon.setVelocity(vector.clone().multiply(-1 / (enderDragon.getLocation().distance(location))));
                    }
                    if (enderDragon.getLocation().getY() > 80){
                        enderDragon.setVelocity(enderDragon.getVelocity().clone().add(new Vector(0,-0.1,0)));
                    }else if (enderDragon.getLocation().getY() < 55){
                        enderDragon.setVelocity(enderDragon.getVelocity().clone().add(new Vector(0,0.1,0)));
                    }
                }else {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class),1L,1L);

    }
    private double getDistance(Location location1,Location location2){
        double[] a = new double[]{location1.getX()-location2.getX(),location1.getZ()-location2.getZ()};
        return Math.sqrt(a[0]*a[0] + a[1]*a[1]);
    }
    @EventHandler
    public void c(EntityRegainHealthEvent e) {
        if (e.getRegainReason() == EntityRegainHealthEvent.RegainReason.ENDER_CRYSTAL) {
            if (new TheNether().EnderCrystalBoost){
                e.setAmount(e.getAmount() * 1.25);
            }
        }
    }
    @EventHandler
    public void d(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof EnderCrystal){
            MhTeam mhTeam = new MhTeam();
            EnderCrystal enderCrystal = (EnderCrystal) e.getEntity();
            if (e.getDamager() instanceof Projectile){
                Player player = (Player) ((Projectile) e.getDamager()).getShooter();
                if (mhTeam.isHunter(player)) {
                    e.setCancelled(true);
                }
            }else if (e.getDamager() instanceof Player){
                Player player = (Player) e.getDamager();
                if (mhTeam.isHunter(player)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
