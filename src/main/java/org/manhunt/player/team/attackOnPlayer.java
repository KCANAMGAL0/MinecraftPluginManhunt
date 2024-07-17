package org.manhunt.player.team;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.manhunt.Main;
import org.manhunt.MhClass.Doctor;
import org.manhunt.MhClass.MhClassManager;

import java.util.HashMap;

public class attackOnPlayer implements Listener {
    public static HashMap<Player,Player> playerLastDamager;
    static {
        playerLastDamager = new HashMap<>();
    }
    MhClassManager mhClassManager = new MhClassManager();

    @EventHandler
    public void onNormalAttack(EntityDamageByEntityEvent e){
        if (!e.isCancelled()) {
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                Player attacker = (Player) e.getDamager();
                Player victim = (Player) e.getEntity();
                MhTeam mhTeam = new MhTeam();
                if (mhTeam.isSameTeam(attacker, victim)) {
                    e.setCancelled(true);
                } else {
                    setPlayerLastDamager(victim, attacker);
                }
            }
        }
    }
    @EventHandler
    public void onProjectile(EntityDamageByEntityEvent e){
        if (!e.isCancelled()) {
            if (e.getDamager() instanceof Projectile && e.getEntity() instanceof Player) {
                Projectile projectile = (Projectile) e.getDamager();
                Player attacker = (Player) projectile.getShooter();
                Player victim = (Player) e.getEntity();
                if (new MhTeam().isSameTeam(attacker, victim) || attacker.equals(victim)) {
                    if (mhClassManager.getPlayerClass(attacker).equals("Doc") && e.getDamager() instanceof Arrow) {
                        if (!attacker.equals(victim)) {
                            projectile.remove();
                            new Doctor().HealArrowSkill(attacker, victim);
                        }
                    }
                    e.setCancelled(true);
                } else {
                    setPlayerLastDamager(victim, attacker);
                    HealthDisplay(attacker,victim);
                }
            }
        }
    }
    public void setPlayerLastDamager(Player victim,Player damager) {
        playerLastDamager.put(victim,damager);
    }
    public void resetPlayerLastDamager(Player player) {
        playerLastDamager.put(player,player);
    }
    public Player getPlayerLastDamager(Player player) {
        return playerLastDamager.get(player);
    }
    public boolean hasDamagedFromPlayer(Player player) {
        return !getPlayerLastDamager(player).equals(player);
    }
    public double Keep1DecimalPlace(double a) {
        int b = (int) (a*10);
        return (double) b/10;
    }

    private void HealthDisplay(Player player,Player victim){
        Plugin plugin = Main.getPlugin(Main.class);
        new BukkitRunnable() {
            @Override
            public void run() {
                double a =Keep1DecimalPlace(victim.getHealth());
                player.sendMessage(ChatColor.AQUA + victim.getName() + ChatColor.YELLOW + "还剩" + ChatColor.RED + a + ChatColor.YELLOW + "点生命值");
            }
        }.runTaskLater(plugin,1L);
    }
}
