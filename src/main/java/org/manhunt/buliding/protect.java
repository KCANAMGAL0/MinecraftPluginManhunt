package org.manhunt.buliding;

import javafx.print.PageLayout;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;
import org.manhunt.Utils.MathUtil;
import org.manhunt.game.TheNether;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;
import org.omg.PortableInterceptor.ACTIVE;

public class protect implements Listener {
    public Location endTower1 = endTower.getEndTower(1);
    public Location endTower2 = endTower.getEndTower(2);

    @EventHandler
    public void BlockBreakListener(BlockBreakEvent e){
        if (!e.isCancelled()) {
            if (e.getBlock().getWorld().getName().equals("world")) {
                Location location = e.getBlock().getLocation();
                double[] blockPos = new double[]{location.getX(), location.getY(), location.getZ()};

                if (MathUtil.getPlaneDistance(location,endTower1) <= 16) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "你不能在这里破坏方块");
                }
                if (MathUtil.getPlaneDistance(location,endTower2) <= 16){
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "你不能在这里破坏方块");
                }
                if (isProtectBuilding(SpawnLocation.getHunterSpawnLocation(), blockPos, "B", 5)) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "你不能在这里破坏方块");
                }
                if (location.distance(TheNether.getMainWorldTeleportLocation()) < 4){
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "你不能在这里破坏方块");
                }
            } else if (e.getBlock().getWorld().getName().equals("world_the_end")) {
                Location location = e.getBlock().getLocation();
                double[] blockPos = new double[]{location.getX(), location.getY(), location.getZ()};
                if (isProtectBuilding(SpawnLocation.getHunterSpawnTheEndLocation(), blockPos, "B", 6) || isProtectBuilding(SpawnLocation.getRunnerSpawnTheEndLocation(),blockPos,"B",6)) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "你不能在这里破坏方块");
                }
            } else if (e.getBlock().getWorld().getName().equals("world_nether")) {
                Location location = e.getBlock().getLocation();
                if (location.distance(TheNether.getHspawnLocation()) < 4 || location.distance(TheNether.getRspawnLocation()) < 4){
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "你不能在这里破坏方块");
                }
            }
        }
    }

    @EventHandler
    public void BlockPlaceListener(BlockPlaceEvent e){
        if (!e.isCancelled()) {
            if (e.getBlock().getWorld().getName().equals("world")) {
                Location location = e.getBlock().getLocation();
                double[] blockPos = new double[]{location.getX(), location.getY(), location.getZ()};

                if (isProtectBuilding(endTower.getEndTower1Center(), blockPos, "B", 16) || isProtectBuilding(endTower.getEndTower2Center(), blockPos, "B", 16)) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "你不能在这里放置方块");
                }
                if (isProtectBuilding(SpawnLocation.getHunterSpawnLocation(), blockPos, "B", 5)) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "你不能在这里放置方块");
                }

                if (location.distance(TheNether.getMainWorldTeleportLocation()) < 4){
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "你不能在这里放置方块");
                }

            } else if (e.getBlock().getWorld().getName().equals("world_the_end")) {
                Location location = e.getBlock().getLocation();
                double[] blockPos = new double[]{location.getX(), location.getY(), location.getZ()};
                if (isProtectBuilding(SpawnLocation.getHunterSpawnTheEndLocation(), blockPos, "B", 6) || isProtectBuilding(SpawnLocation.getRunnerSpawnTheEndLocation(), blockPos, "B", 6)) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "你不能在这里放置方块");
                }
            } else if (e.getBlock().getWorld().getName().equals("world_nether")) {
                Location location = e.getBlock().getLocation();
                if (location.distance(TheNether.getHspawnLocation()) < 4 || location.distance(TheNether.getRspawnLocation()) < 4){
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "你不能在这里放置方块");
                }
            }
        }
    }
    @EventHandler
    public void DamageHuntersNearSpawnLocation(EntityDamageByEntityEvent e){
        if (!e.isCancelled() && e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (player.getWorld().getName().equals("world") && getDistance(new double[]{player.getLocation().getX(),player.getLocation().getY(),player.getLocation().getZ()}, SpawnLocation.getHunterSpawnLocation(),"B") <= 10){
                if (new MhTeam().getMhTeam(player).equals("HUNTER")){
                    new playerManager().addEffect(player, PotionEffectType.INCREASE_DAMAGE,3 * 20,0,false);
                    new playerManager().addEffect(player, PotionEffectType.DAMAGE_RESISTANCE,3 * 20,0,false);
                }
            }
        }
    }


    public boolean isProtectBuilding(double[] bulidingPos,double[] blockPos,String mode,int distance){
        return getDistance(blockPos, bulidingPos, mode) <= distance && blockPos[1] >= 56;
    }

    public double getDistance(double[] a ,double[] b,String mode){
        if (mode.equals("A")){
            double[] c = new double[]{0,0,0};
            for (int i = 0 ;i <= 2; i++){
                c[i] = (a[i] - b[i])*(a[i] - b[i]);
            }
            return Math.sqrt(c[0] + c[1] + c[2]);
        }else if (mode.equals("B")){
            double[] c = new double[]{0,0};
            c[0] = (a[0] - b[0])*(a[0] - b[0]);
            c[1] = (a[2] - b[2])*(a[2] - b[2]);
            return Math.sqrt(c[0] + c[1]);
        } else return 0;
    }

    @EventHandler
    public void c(PlayerInteractEvent e){
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getItemInHand().getType().equals(Material.LAVA_BUCKET) && !e.isCancelled()){
            if (e.getPlayer().getWorld().equals(Bukkit.getWorld("world"))){
                if (e.getClickedBlock().getLocation().distance(SpawnLocation.ArraytoLocation(SpawnLocation.getHunterSpawnLocation())) <= 5){
                    e.setCancelled(true);
                }
            }else if (e.getPlayer().getWorld().equals(Bukkit.getWorld("world_nether"))){
                if (e.getClickedBlock().getLocation().distance(TheNether.getRspawnLocation()) <= 6 || e.getClickedBlock().getLocation().distance(TheNether.getHspawnLocation()) <= 6){
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void d(EntityExplodeEvent e){
        if (!e.isCancelled()){
            e.blockList().removeIf(block -> MathUtil.getPlaneDistance(endTower1, block.getLocation()) <= 16 && block.getY() > 56);
            e.blockList().removeIf(block -> MathUtil.getPlaneDistance(endTower2, block.getLocation()) <= 16 && block.getY() > 56);
            e.blockList().removeIf(block -> MathUtil.getPlaneDistance(SpawnLocation.ArraytoLocation(SpawnLocation.getHunterSpawnLocation()), block.getLocation()) <= 5 && block.getY() > 56);
        }
    }
}
