package org.manhunt.MhClass;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.manhunt.Main;
import org.manhunt.player.actionbar.ActionbarManager;
import org.manhunt.player.playerManager;

import java.util.HashMap;

public class Cactus extends MhClassManager {
    public static HashMap<Player,Long> ThornsCD,ForwardCD,ForwardCD0,GetMoreDamage;
    public static HashMap<Player,Boolean> ForwardCD1;
    static {
        ThornsCD = new HashMap<>();ForwardCD = new HashMap<>();ForwardCD0 = new HashMap<>();ForwardCD1 = new HashMap<>();
        GetMoreDamage = new HashMap<>();
    }

    public static HashMap<Player,Integer> ParticleTimer;
    static {
        ParticleTimer = new HashMap<>();
    }
    public void ThornsSkill(Player attacker, Player victim, EntityDamageByEntityEvent e){
        if (ThornsCD.containsKey(victim) && ThornsCD.get(victim) > System.currentTimeMillis()) {
            return;
        }else {
            double a = 1;
            playerManager pM = new playerManager();
            if (pM.hasEffect(attacker, PotionEffectType.INCREASE_DAMAGE, 0)) {
                a -= 0.4348;
            }
            if (GetMoreDamage.containsKey(victim) && GetMoreDamage.get(victim) > System.currentTimeMillis()){
                a += 0.3;
            }
            e.setDamage(e.getDamage() * a);
            attacker.damage(0,victim);
            attacker.damage(e.getFinalDamage());
            victim.sendMessage(ChatColor.GREEN + "你的反伤技能已激活!");
            victim.playSound(victim.getLocation(), Sound.GLASS,1.0F,1.0F);
            attacker.playSound(victim.getLocation(), Sound.GLASS,1.0F,1.0F);
            pM.addEffect(victim,PotionEffectType.SPEED,100,0,false);
            ThornsCD.put(victim,System.currentTimeMillis() + 15 * 1000L);
        }
    }
    public void ForwardSkill(Player player){
        Location location;
        Vector vector;
        if (ForwardCD.containsKey(player) && ForwardCD.get(player) > System.currentTimeMillis()){
            return;
        }else {
            if (ForwardCD0.containsKey(player) && ForwardCD0.get(player) > System.currentTimeMillis()){
                ForwardCD.put(player,System.currentTimeMillis() + 5L);
                ForwardCD1.put(player,true);
                ForwardCD0.put(player,0L);
                GetMoreDamage.put(player,System.currentTimeMillis() + 3000L);
                ParticleTimer.put(player,20);
                player.sendMessage(ChatColor.GREEN + "你的突进技能已激活!");
                //player.sendMessage("1");
                location = player.getLocation();
                vector = location.getDirection().normalize();
                double airMultiplier = player.isOnGround() ? 2.0 : 1.0;
                //player.sendMessage("2");
                /*
                Location location1 = location.clone().add(vector.clone().multiply(2));
                Vector vector1 = location1.toVector().subtract(location.toVector());
                //player.sendMessage("3");
                //player.sendMessage(vector.normalize().toString());

                 */
                vector.setX(vector.getX() * 1.1 * airMultiplier);
                double y = vector.getY() > 0.25 ? vector.getY() / 2 : vector.getY();
                double y1 = y > 0.25 ? y / 2 : y;
                vector.setY(y1 * airMultiplier);
                vector.setZ(vector.getZ() * 1.1 * airMultiplier);
                player.setLastDamageCause(null);
                player.setVelocity(vector);
                //player.sendMessage("4");
                player.getWorld().playSound(player.getLocation(),Sound.GHAST_FIREBALL,2.0F,2.0F);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (ParticleTimer.get(player) == 0){
                            this.cancel();
                        }else {
                            ParticleTimer.put(player, ParticleTimer.get(player) - 1);
                            player.getWorld().playEffect(player.getLocation(), Effect.COLOURED_DUST, Integer.MAX_VALUE);
                        }
                    }
                }.runTaskTimer(Main.getPlugin(Main.class),1L,1L);
            }else {
                return;
            }
        }
    }

    public void GetMoreDamage(Player attacker,Player player,EntityDamageByEntityEvent e){
        if (GetMoreDamage.containsKey(player) && GetMoreDamage.get(player) > System.currentTimeMillis()){
            playerManager pM = new playerManager();
            if (pM.hasEffect(attacker,PotionEffectType.INCREASE_DAMAGE,0)){
                e.setDamage((e.getDamage() / 2.3) * 1.6);
            }else {
                e.setDamage(e.getDamage() * 1.3);
            }
        }else {
            return;
        }
    }
    public void CacDisplay(Player player){
        ActionbarManager a = new ActionbarManager();
        boolean b = (GetMoreDamage.containsKey(player) && GetMoreDamage.get(player) > System.currentTimeMillis());
        if (ThornsCD.containsKey(player) && ThornsCD.get(player) > System.currentTimeMillis()){
            String t = (ThornsCD.get(player) - System.currentTimeMillis()) / 1000 + "秒 ";
            if (b){
                String g = (GetMoreDamage.get(player) - System.currentTimeMillis()) / 1000 + "秒 ";
                a.sendActionbar(player,ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "易损 " + ChatColor.RED + g,ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "反伤 " + ChatColor.GRAY + t);
            }else {
                a.sendActionbar(player,ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "易损 " + ChatColor.RED + ChatColor.BOLD + "✖ ",ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "反伤 " + ChatColor.GRAY + t);
            }
        }else {
            if (b){
                String g = (GetMoreDamage.get(player) - System.currentTimeMillis()) / 1000 + "秒 ";
                a.sendActionbar(player,ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "易损 " + ChatColor.RED + g,ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "反伤 " + ChatColor.GREEN + ChatColor.BOLD + "✔ ");
            }else {
                a.sendActionbar(player,ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "易损 " + ChatColor.RED + ChatColor.BOLD + "✖ ",ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "反伤 " + ChatColor.GREEN + ChatColor.BOLD + "✔ ");
            }
        }
    }

    public boolean isGetMoreDamage(Player player){
        if (GetMoreDamage.containsKey(player) && GetMoreDamage.get(player) > System.currentTimeMillis()){
            return true;
        }else {
            return false;
        }
    }
}
