package org.manhunt.MhClass;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftBat;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.manhunt.Main;
import org.manhunt.Utils.MathUtil;
import org.manhunt.player.actionbar.ActionbarManager;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;
import org.manhunt.player.team.attackOnPlayer;

import java.util.HashMap;

public class Robber extends MhClassManager {
    public static HashMap<Player,Long> aCD,bCD,cCD;
    public static HashMap<Player,Integer> bC;
    public static HashMap<Player,Boolean> f;
    static {
        aCD = new HashMap<>();
        bC = new HashMap<>();
        bCD = new HashMap<>();
        cCD = new HashMap<>();
        f = new HashMap<>();
    }
    public void a(Player player) {
        if (aCD.containsKey(player)){

            if (aCD.get(player) >= 60) {
                if (bCD.containsKey(player) && bCD.get(player) > System.currentTimeMillis()){
                    return;
                }else {
                    long a = aCD.get(player);
                    Vector vector = player.getLocation().getDirection().clone().multiply(1.5);
                    player.getWorld().playSound(player.getLocation(), Sound.IRONGOLEM_DEATH, 1.0F, 1.0F);
                    ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.ARMOR_STAND);
                    armorStand.setSmall(true);
                    armorStand.setHelmet(new ItemStack(Material.ANVIL));
                    armorStand.setVisible(false);
                    armorStand.setVelocity(vector);
                    Bat bat = (Bat) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.BAT);
                    bat.setLeashHolder(player);
                    bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000000, 1));
                    NBTTagCompound compound = new NBTTagCompound();
                    compound.setInt("NoAI", 1);
                    compound.setInt("Invulnerable", 1);
                    compound.setInt("Silent", 1);
                    ((CraftBat) bat).getHandle().f(compound);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Location currentLocation = armorStand.getLocation();
                            if (armorStand.isOnGround() || armorStand.getVelocity().getX() == 0 || armorStand.getVelocity().getZ() == 0 || hasNearbyRunner(currentLocation)) {
                                armorStand.remove();
                                bat.remove();
                                Vector vector1 = currentLocation.toVector().subtract(player.getLocation().toVector()).clone().normalize().multiply(a == 100 ? 2 : 1.25);
                                vector1.setY(Math.abs(vector1.getY()));
                                player.setLastDamageCause(null);
                                f.put(player,true);
                                player.setVelocity(vector1);
                                this.cancel();
                            } else {
                                bat.teleport(currentLocation);
                            }
                        }

                        private boolean hasNearbyRunner(Location location) {
                            for (Player p : location.getWorld().getPlayers()) {
                                if (p.getEyeLocation().distance(location) <= 1 && new MhTeam().isRunner(p) && !p.equals(player)) {
                                    p.damage(1);
                                    return true;
                                }
                            }
                            return false;
                        }
                    }.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 1L, 1L);
                    aCD.put(player, 0L);
                    bCD.put(player,System.currentTimeMillis() + 30 * 1000L);
                }
            }
        }
    }

    public void Arrow(Player player,Player victim){
        if (aCD.containsKey(player)) aCD.put(player,aCD.get(player) + 10 >= 100 ? 100 : aCD.get(player) + 10L);
        else aCD.put(player,10L);
        if (bC.containsKey(victim)){
            if (bC.get(victim) < 8) bC.put(victim,bC.get(victim) + 1);
        }else bC.put(victim,1);
        player.sendMessage(ChatColor.YELLOW + victim.getName() + "身上已有" + bC.get(victim) + "支箭矢!");
    }
    public void Melee(Player player){
        if (aCD.containsKey(player)) aCD.put(player,aCD.get(player) + 8 >= 100 ? 100 : aCD.get(player) + 8L);
        else aCD.put(player,8L);
    }
    public void Arrow1(Player player){
        if (aCD.containsKey(player) && aCD.get(player) == 100) {
            if (cCD.containsKey(player) && cCD.get(player) > System.currentTimeMillis()){
                return;
            }else {
                for (Player victim : MathUtil.getNearByPlayer(player.getLocation(), 4d)) {
                    if (bC.containsKey(victim) && victim != player && bC.get(victim) > 0) {
                        new attackOnPlayer().setPlayerLastDamager(victim, player);
                        victim.damage(bC.get(victim) * 1.25);
                        victim.getWorld().playSound(victim.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0F, 1.0F);
                        for (int i = 0; i <= 10 * bC.get(victim); i++)
                            victim.getWorld().playEffect(victim.getEyeLocation(), Effect.TILE_BREAK, 152);
                        new playerManager().addEffect(player, PotionEffectType.WEAKNESS, 10 * 20, 1, false);
                        bC.put(victim, 0);
                        aCD.put(player, 0L);
                        cCD.put(player,System.currentTimeMillis() + 15 * 1000L);
                    }
                }
            }
        }
    }
    public void Display(Player player){
        ActionbarManager actionbarManager = new ActionbarManager();
        String baseColor = ChatColor.GOLD + ChatColor.BOLD.toString();
        String s = bCD.containsKey(player) && bCD.get(player) > System.currentTimeMillis() ? ChatColor.GRAY + String.valueOf((bCD.get(player) - System.currentTimeMillis()) / 1000) + "秒 ": (aCD.containsKey(player) && aCD.get(player) >= 60 ? ChatColor.GREEN + ChatColor.BOLD.toString() + "✔ ": ChatColor.RED + ChatColor.BOLD.toString() + "✖ ");
        String s1 = cCD.containsKey(player) && cCD.get(player) > System.currentTimeMillis() ? ChatColor.GRAY + String.valueOf((cCD.get(player) - System.currentTimeMillis()) / 1000) + "秒 ": (aCD.containsKey(player) && aCD.get(player) == 100 ? ChatColor.GREEN + ChatColor.BOLD.toString() + "✔ ": ChatColor.RED + ChatColor.BOLD.toString() + "✖ ");
        String s3 = aCD.containsKey(player) ? String.valueOf(aCD.get(player)) : "0";
        actionbarManager.sendActionbar(player,baseColor + "抓钩 " + s,baseColor + "充能 " + ChatColor.GOLD + "[" + s3 + "]  " + baseColor + "撕裂箭矢 " + s1);
    }
}
