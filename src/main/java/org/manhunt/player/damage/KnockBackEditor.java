package org.manhunt.player.damage;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import java.util.HashMap;

public class KnockBackEditor implements Listener {
    public static HashMap<Player,Boolean> playerBooleanHashMap;
    static {
        playerBooleanHashMap = new HashMap<>();
    }
    @EventHandler
    public void a(PlayerVelocityEvent e){
        Player player = e.getPlayer();
        if (!e.isCancelled()) {
            if (player.getLastDamageCause() != null && player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                if (((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager() instanceof Player) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void b(EntityDamageByEntityEvent e){
        if (!e.isCancelled()){
            if (e.getDamager() instanceof Player && e.getEntity() instanceof Player){
                Player attacker = (Player) e.getDamager();
                Player victim = (Player) e.getEntity();
                if (!((double) victim.getNoDamageTicks() > (double)victim.getMaximumNoDamageTicks() / 2.0)){
                    double horMultiplier = 1.209;
                    double verMultiplier = 1.001;
                    double sprintMultiplier = attacker.isSprinting() ? 0.8 : 0.5;
                    double kbMultiplier = attacker.getItemInHand() == null ? 0.0 : (double)attacker.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK) * 0.2;
                    double airMultiplier = victim.isOnGround() ? 1.0 : 0.5;
                    Vector knockback = victim.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize();
                    knockback.setX((knockback.getX() * sprintMultiplier + kbMultiplier) * horMultiplier);
                    knockback.setY(0.35 * airMultiplier * verMultiplier);
                    knockback.setZ((knockback.getZ() * sprintMultiplier + kbMultiplier) * horMultiplier);
                    ((CraftPlayer)victim).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityVelocity(victim.getEntityId(), knockback.getX(), knockback.getY(), knockback.getZ()));
                }
            }
        }
    }
}
