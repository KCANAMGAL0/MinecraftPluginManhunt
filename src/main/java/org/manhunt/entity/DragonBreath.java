package org.manhunt.entity;


import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.manhunt.Main;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.logging.MemoryHandler;

public class DragonBreath {
    int Timer = 20;
    World theEnd = Bukkit.getWorld("world_the_end");
    Player target;
    EnderDragon enderDragon;
    Location startLocation;
    Location targetLocation;
    double distance = 0,maxDistance = 64;

    public void start(){
        Vector vector = targetLocation.toVector().subtract(startLocation.toVector()).normalize();
        new BukkitRunnable() {

            @Override
            public void run() {
                Location currentLocation = startLocation.clone().add(vector.clone().multiply(distance));
                if (distance > maxDistance) this.cancel();
                showHitBlockEffect(currentLocation);
                showHitPlayerEffect(currentLocation);
                theEnd.playEffect(currentLocation,Effect.EXPLOSION_LARGE,0);
                distance+=0.5;
            }
            private void showHitBlockEffect(Location location){
                Block block = theEnd.getBlockAt(location);
                if (block.getType() != Material.AIR){
                    theEnd.playEffect(location,Effect.EXPLOSION_HUGE,0);
                    theEnd.playSound(location,Sound.EXPLODE,1.0F,1.0F);
                    DragonBreathCircle(location);
                    this.cancel();
                }
            }
            private void showHitPlayerEffect(Location location){
                MhTeam mhTeam = new MhTeam();
                for (Player player : mhTeam.getRunnerPlayers()){
                    if (player.getWorld().equals(theEnd)) {
                        if (player.getLocation().distance(location) <= 2) {
                            player.damage(6);
                            new playerManager().addEffect(player, PotionEffectType.BLINDNESS, 60, 0, false);
                            player.sendTitle("", ChatColor.DARK_RED + "你被龙息弹击中了");
                            //player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "呆在一个地方站桩输出是不安全的，末影龙不允许这种弱智一般的打法");
                            player.setFireTicks(100);
                            theEnd.playEffect(location, Effect.EXPLOSION_HUGE, 0);
                            theEnd.playSound(location, Sound.EXPLODE, 1.0F, 1.0F);
                            Location location1 = player.getLocation();
                            location1.setY(location1.getY() + 0.5);
                            DragonBreathCircle(location1);
                            this.cancel();
                            return;
                        }
                    }
                }
            }

        }.runTaskTimerAsynchronously(Main.getPlugin(Main.class),0,1);
    }

    public void DragonBreathCircle(Location midLocation){
        MhTeam mhTeam = new MhTeam();
        playerManager pM = new playerManager();
        midLocation.setY(midLocation.getY() + 0.5);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Timer == 0){
                    this.cancel();
                }else {
                    for (double i = 5;i >=0 ; i-=0.25) {
                        for (Location location : getCircle(midLocation, i)) {
                            if (location != null) theEnd.playEffect(location, Effect.WITCH_MAGIC, 0);
                        }
                    }
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getWorld().equals(theEnd)) {
                            double t = Math.abs(player.getLocation().getY() - midLocation.getY());
                            if (getDistance(player.getLocation(), midLocation) <= 5 && t <= 1.25) {
                                if (mhTeam.isHunter(player)) {
                                    pM.addEffect(player, PotionEffectType.SPEED, 100, 0, false);
                                    pM.addEffect(player, PotionEffectType.REGENERATION, 100, 0, false);
                                } else if (mhTeam.isRunner(player)) {
                                    player.damage(2);
                                    if (player.getFireTicks() <= 100) player.setFireTicks(100);
                                }
                            }
                        }
                    }
                    Timer--;
                }
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(Main.class),20,10);
    }

    public Location[] getCircle(Location mid,double r){
        final double pi = Math.PI;
        Location location = mid;
        double height = mid.getY(),t0 = 4*r;
        int t = 0;
        Location[] locations = new Location[64];
        for (int i = 0;i < locations.length;i++){
            locations[t] = mid;
        }
        for (double x = 0; x <= (2*pi); x += (pi/t0)){
            t++;
            location = new Location(theEnd,mid.getX() + r * Math.cos(x),height,mid.getZ() + r * Math.sin(x));
            locations[t] = location;
        }
        return locations;
    }

    public double getDistance(Location location1,Location location2){
        double[] a = new double[]{0,0};
        double[] b = new double[]{0,0};
        double[] c = new double[]{0,0};
        a[0] = location1.getX(); a[1] = location1.getZ();
        b[0] = location2.getX(); b[1] = location2.getZ();
        for (int i=0;i<=1;i++){
            c[i] = (a[i]-b[i])*(a[i]-b[i]);
        }
        return Math.sqrt(c[0]+c[1]);
    }
    public DragonBreath(EnderDragon enderDragon, Player target){
        this.target = target;
        this.enderDragon = enderDragon;
        this.startLocation = enderDragon.getLocation();
        this.targetLocation = target.getLocation();
    }

}
