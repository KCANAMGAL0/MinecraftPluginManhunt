package org.manhunt.MhClass;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.manhunt.Main;
import org.manhunt.Utils.MathUtil;
import org.manhunt.entity.CustomEntityTypes;
import org.manhunt.entity.CustomZombie;
import org.manhunt.player.actionbar.ActionbarManager;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.attackOnPlayer;

import java.util.HashMap;

public class Guardian extends MhClassManager {
    public static HashMap<Player,Long> GuardianEnergy,NeuronCD;
    static {
        GuardianEnergy = new HashMap<>();
        NeuronCD = new HashMap<>();
    }
    public void GuardRay(Player player){
        if (GuardianEnergy.containsKey(player)) {
            if (GuardianEnergy.get(player) >= 50){
                player.playSound(player.getLocation(),Sound.AMBIENCE_THUNDER,2.0F,3.0F);
                player.sendMessage(ChatColor.GREEN + "你的深海诅咒技能已激活");
                GuardianEnergy.put(player,GuardianEnergy.get(player) - 50);
                new GuardRayManager(player).start();
            }else {
                return;
            }
        }
    }
    public void GuardRay(Player player, long value){
        if (GuardianEnergy.containsKey(player)) {
            if (GuardianEnergy.get(player) + value >= 100){
                GuardianEnergy.put(player,100L);
            }else {
                GuardianEnergy.put(player,GuardianEnergy.get(player) + value);
            }
        }else {
            GuardianEnergy.put(player, value);
        }
    }
    public void Neuron(Player player){
        if (NeuronCD.containsKey(player) && NeuronCD.get(player) > System.currentTimeMillis()){
            return;
        }else {
            player.sendMessage(ChatColor.GREEN + "你的神经元技能已激活");
            player.playSound(player.getLocation(),"mob.guardian.curse",1.0F,1.0F);
            playerManager pM = new playerManager();
            NeuronCD.put(player,System.currentTimeMillis() + 20 * 1000L);
            Zombie zombie = CustomEntityTypes.spawnEntity(new CustomZombie(player.getWorld()), player.getLocation());
            zombie.setBaby(false);
            zombie.setTarget(MathUtil.getNearestRunner(player.getLocation(),10));
            Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new BukkitRunnable() {
                @Override
                public void run() {
                    zombie.remove();
                }
            },20 * 20L);

            GuardianEnergy.put(player, 0L);
        }
    }
    public void Display(Player player){
        final String baseColor = ChatColor.AQUA + ChatColor.BOLD.toString();
        String s = NeuronCD.containsKey(player) && NeuronCD.get(player) > System.currentTimeMillis() ? ChatColor.GRAY + String.valueOf((NeuronCD.get(player) - System.currentTimeMillis()) / 1000) + "秒 " : ChatColor.GREEN + ChatColor.BOLD.toString() +"✔ ";
        String s1 = GuardianEnergy.containsKey(player) && GuardianEnergy.get(player) >= 50 ? ChatColor.GREEN + ChatColor.BOLD.toString() + "✔ " : ChatColor.RED + ChatColor.BOLD.toString() + "✖ ";
        ActionbarManager actionbarManager = new ActionbarManager();
        actionbarManager.sendActionbar(player,baseColor + "神经元 " + s,baseColor + "充能 " + ChatColor.AQUA + "[" + GuardianEnergy.getOrDefault(player,0L) + "] " + s1);
    }
}

class GuardRayManager {
    private Player player;
    private Location location;
    private Vector vector;
    private double distance = 0;
    private double maxDistance = 64;
    public GuardRayManager(Player player){
        this.player = player;
        this.location = player.getEyeLocation();
        this.vector = player.getLocation().getDirection();
    }


    public void start(){
        new BukkitRunnable() {
            @Override
            public void run() {
                Location currentLocation = location.clone().add(vector.clone().multiply(distance));
                if (distance > maxDistance) this.cancel();
                else {
                    showHitPlayerEffect(currentLocation);
                    distance += 1;
                }
            }
            private void showHitPlayerEffect(Location location){
                playerManager pM = new playerManager();

                //location.getWorld().playEffect(location,Effect.WITCH_MAGIC,0);
                //MathUtil.getSphere(location,4,4,vector).forEach(location1 -> location1.getWorld().playEffect(location1,Effect.WITCH_MAGIC,0));
                for (Player player1 : player.getWorld().getPlayers()){
                    ((CraftWorld) location.getWorld()).getHandle().sendParticles(((CraftPlayer) player1).getHandle(),EnumParticle.CRIT_MAGIC,true,location.getX(),location.getY(),location.getZ(),10,0.1,0.1,0.1,0.1,0);
                    for (Entity entity : location.getWorld().getNearbyEntities(location,0.5,0.5,0.5)) {
                        if (entity instanceof Player) {
                            if (entity.equals(player1) && !entity.equals(player)) {
                                player1.playSound(player1.getLocation(), "mob.guardian.curse", 2.0F, 1.0F);
                                Packet<?> packet = new PacketPlayOutWorldParticles(EnumParticle.MOB_APPEARANCE, true, 1, 1, 1, 1, 1, 1, 0, 1);
                                ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(packet);
                                pM.addEffect(player1, PotionEffectType.POISON, 3 * 20, 1, false);
                                pM.addEffect(player1, PotionEffectType.SLOW_DIGGING, 3 * 20, 3, false);
                                new attackOnPlayer().setPlayerLastDamager(player1, player);
                                player1.damage(1.5d);
                            }
                        }
                    }
                }
            }
            private boolean HitDetect(Location location0,Location location1){
                double x = location0.getX();
                double y = location0.getY();
                double z = location0.getZ();
                double a = location1.getX();
                double b = location1.getY();
                double c = location1.getZ();
                if (Math.abs(x-a) <= 0.5 && y-b <=2 && Math.abs(z-c) <= 0.5){
                    return true;
                }else {
                    return false;
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class),0L,1L);
    }


}
