package org.manhunt.MhClass;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.manhunt.Main;
import org.manhunt.Utils.ItemStackUtil;
import org.manhunt.Utils.MathUtil;
import org.manhunt.player.actionbar.ActionbarManager;
import org.manhunt.player.team.MhTeam;
import org.manhunt.player.team.attackOnPlayer;

import javax.crypto.Mac;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MachineMaster extends MhClassManager {
    public static HashMap<Player,Long> CannonCD;
    public static HashMap<Player,Integer> CannonTimer;
    public static HashMap<ArmorStand, BukkitRunnable> bukkitRunnableHashMap;
    static {
        CannonCD = new HashMap<>();
        CannonTimer = new HashMap<>();
        bukkitRunnableHashMap = new HashMap<>();
    }

    public void Cannon(Player player){
        if (CannonCD.containsKey(player) && CannonCD.get(player) > System.currentTimeMillis()){
            return;
        }else {
            Cannon cannon = new Cannon(player.getLocation(),player);
            CannonTimer.put(player,15);
            CannonCD.put(player,System.currentTimeMillis() + 50 * 1000L);
            player.sendMessage(ChatColor.GREEN + "你的加农炮技能已激活!");
            player.playSound(player.getLocation(),Sound.ORB_PICKUP,1.0F,1.0F);
            cannon.run();
        }
    }
    public void AimAssist(Arrow arrow,Player player0){
        final double cos60 = 0.5;
        World world = arrow.getWorld();
        MhTeam mhTeam = new MhTeam();
        List<Player> players = world.getPlayers();
        players.removeIf(player -> mhTeam.isRunner(player) || player.getLocation().distance(player0.getLocation()) >= 32 || player.equals(player0));
        List<Double> doubles = new ArrayList<>();
        Location location = arrow.getLocation();
        Vector vector = arrow.getVelocity();
        vector.setY(0);
        Location location0 = location.clone().add(vector.clone());
        double d0 = MathUtil.getPlaneDistance(location0,location),d1,d2;
        for (int i = 0; i < players.size() ; i ++) {
            Location playerLocation = players.get(i).getLocation();
            d1 = MathUtil.getPlaneDistance(playerLocation,location);
            d2 = MathUtil.getPlaneDistance(playerLocation,location0);
            doubles.add(i,MathUtil.getCosine(d0,d1,d2));
        }
        for (double dd : doubles) {
            if (dd < cos60) doubles.remove(dd);
        }
        double d = MathUtil.getMaxValue(doubles);
        player0.sendMessage(String.valueOf(d));
        Player target = null;
        for (int j = 0 ; j < doubles.size() ; j++){
            if (doubles.get(j) == d){
                target = players.get(j);
            }
        }
        if (target != null && d >= cos60) {
            Player finalTarget = target;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (arrow.isOnGround() || arrow.isDead()) this.cancel();
                    arrow.getWorld().playEffect(arrow.getLocation(),Effect.WITCH_MAGIC,0);
                    Vector vector1 = finalTarget.getEyeLocation().toVector().subtract(arrow.getLocation().toVector()).normalize().multiply(2);
                    arrow.setVelocity(vector1);
                }
            }.runTaskTimerAsynchronously(Main.getPlugin(Main.class),1L,1L);

        }
    }
    public void Display(Player player){
        ActionbarManager actionbarManager = new ActionbarManager();
        String s = CannonCD.containsKey(player) && CannonCD.get(player) > System.currentTimeMillis() ? ChatColor.GRAY.toString() + ((CannonCD.get(player) - System.currentTimeMillis()) / 1000) + "秒 " : ChatColor.GREEN + ChatColor.BOLD.toString() + "✔ ";
        actionbarManager.sendActionbar(player,s,ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "加农炮 ");
    }
}
class Cannon {
    private Location location;
    private Player player;

    public double a = 0;
    public double b = 32;

    Cannon(Location location, Player player) {
        this.location = location;
        this.player = player;
    }

    public Location getLocation() {
        return location;
    }

    public Player getPlayer() {
        return player;
    }

    public void run() {
        Location spawnLocation = getLocation().clone();
        spawnLocation.add(0,-0.30,0);
        Location location1 = spawnLocation.clone().add(spawnLocation.getDirection().normalize());
        location1.setY(spawnLocation.getY());
        ArmorStand armorStand = getSummonArmorStand(spawnLocation, player);
        ArmorStand base = getSummonArmorStandBase(spawnLocation.clone().add(0,-1.05,0),player);
        new BukkitRunnable() {
            @Override
            public void run() {
                armorStand.remove();
                base.remove();
            }
        }.runTaskLaterAsynchronously(Main.getPlugin(Main.class), 15 * 20L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (MachineMaster.CannonTimer.get(player) == 0) {
                    player.sendMessage(ChatColor.RED + "炮塔已消失!");
                    this.cancel();
                } else {
                    MachineMaster.CannonTimer.put(player, MachineMaster.CannonTimer.get(player) - 1);
                    armorStand.setCustomName(ChatColor.RED + String.valueOf(MachineMaster.CannonTimer.get(player)) + "秒");
                    //Player player1 = MathUtil.getNearestHunter(getLocation(), 5);
                    Player player1 = MathUtil.getHighestHPHunter(location,7.5);
                    if (player1 != null) {
                        Location location2 = armorStand.getLocation();
                        location2.setDirection(player1.getLocation().toVector().subtract(armorStand.getLocation().toVector()).normalize());
                        armorStand.teleport(location2);
                        if (MachineMaster.bukkitRunnableHashMap.containsKey(armorStand))
                            MachineMaster.bukkitRunnableHashMap.get(armorStand).cancel();
                        launch(armorStand, player1,player);
                    }
                }
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0L, 20L);
    }

    private ArmorStand getSummonArmorStand(Location location, Player player) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setMarker(true);
        armorStand.setBasePlate(false);
        armorStand.setVisible(false);
        armorStand.setHelmet(ItemStackUtil.getSkull(player));
        armorStand.setCustomNameVisible(true);
        return armorStand;
    }
    private ArmorStand getSummonArmorStandBase(Location location, Player player) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setSmall(false);
        armorStand.setMarker(true);
        armorStand.setBasePlate(false);
        armorStand.setVisible(false);
        armorStand.setHelmet(new ItemStack(Material.ENCHANTMENT_TABLE));
        return armorStand;
    }

    public void launch(ArmorStand armorStand,Player target,Player attacker){
        Location targetLocation = target.getEyeLocation();
        Location location = armorStand.getEyeLocation();
        location.add(0,0.9,0);
        Vector vector = targetLocation.toVector().subtract(location.toVector()).normalize();
        new BukkitRunnable() {
            double aa = a;
            final double bb = b;
            @Override
            public void run() {
                MachineMaster.bukkitRunnableHashMap.put(armorStand,this);
                if (aa > bb) this.cancel();
                aa+=0.6;
                Location currentLocation = location.clone().add(vector.clone().multiply(aa));
                for (Player player1 : currentLocation.getWorld().getPlayers()) {
                    ((CraftWorld) currentLocation.getWorld()).getHandle().sendParticles(
                            ((CraftPlayer) player1).getHandle(), EnumParticle.SMOKE_NORMAL, true,
                            currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(),
                            10, 0.1, 0.1, 0.1, 0.1, 0
                    );
                }
                if (!currentLocation.getBlock().getType().equals(Material.AIR) && !currentLocation.getBlock().getType().equals(Material.LONG_GRASS)) HitEffect(currentLocation);
                if (HitDetect(currentLocation,target)){
                    HitEffect(currentLocation,target);
                }
            }
            private boolean HitDetect(Location location, Player target){
                for (Entity entity : location.getWorld().getNearbyEntities(location,0.95,0.95,0.95)){
                    if (target.equals(entity) && new MhTeam().isHunter(target)){
                        return true;
                    }
                }
                return false;
            }
            private void HitEffect(Location location){
                this.cancel();
                location.getWorld().playEffect(location,Effect.EXPLOSION_LARGE,0);
                location.getWorld().playSound(location,Sound.EXPLODE,1.0F,1.0F);
                for (Entity entity : location.getWorld().getNearbyEntities(location,0.95,0.95,0.95)){
                    if (entity instanceof Player && new MhTeam().isHunter((Player) entity)){
                        Player player1 = (Player) entity;
                        player1.setLastDamageCause(null);
                        player1.damage(0.1,armorStand);
                        player1.setLastDamageCause(null);
                        new attackOnPlayer().setPlayerLastDamager(((Player) entity),attacker);
                        if (new MhClassManager().getPlayerClass(player1).equals(MhClasses.Doc.getName())) {
                            new Doctor().addDamage(player1,2);
                        }
                        player1.damage(2);
                        player1.setLastDamageCause(null);
                    }
                }

            }
            private void HitEffect(Location location,Player player){
                this.cancel();
                for (Entity entity : location.getWorld().getNearbyEntities(location,0.95,0.95,0.95)){
                    if (entity instanceof Player && new MhTeam().isHunter((Player) entity)){
                        location.getWorld().playEffect(location,Effect.EXPLOSION_LARGE,0);
                        location.getWorld().playSound(location,Sound.EXPLODE,1.0F,1.0F);
                        Player player1 = (Player) entity;
                        player1.setLastDamageCause(null);
                        player1.damage(0.1,armorStand);
                        player1.setLastDamageCause(null);
                        new attackOnPlayer().setPlayerLastDamager(player1,attacker);
                        if (new MhClassManager().getPlayerClass(player1).equals(MhClasses.Doc.getName())) {
                            new Doctor().addDamage(player1,2);
                        }
                        player1.damage(2);
                        player1.setLastDamageCause(null);
                    }
                }

            }
        }.runTaskTimer(Main.getPlugin(Main.class),0L,1L);
    }
}

