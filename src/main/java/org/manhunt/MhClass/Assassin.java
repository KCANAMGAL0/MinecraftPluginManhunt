package org.manhunt.MhClass;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.manhunt.Main;
import org.manhunt.Utils.MathUtil;
import org.manhunt.player.actionbar.ActionbarManager;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.attackOnPlayer;

import java.util.HashMap;

public class Assassin extends MhClassManager {
    public static HashMap<Player,Long> InvisibilityCD;
    public static HashMap<Player,Long> InvisibilityDuration;
    static {
        InvisibilityCD = new HashMap<>();
        InvisibilityDuration = new HashMap<>();
    }
    public void backHit(Player attacker, Player victim,EntityDamageByEntityEvent e){
        new attackOnPlayer().setPlayerLastDamager(victim,attacker);
        double a = 1;
        if (new playerManager().hasEffect(attacker, PotionEffectType.INCREASE_DAMAGE,0)) a -= 0.4348;
        if (super.getPlayerClass(victim).equals(MhClasses.Cac.getName()) && new Cactus().isGetMoreDamage(victim)) a += 0.3;
        if (isInvisible(attacker)) {
            a += 0.5;
            InvisibilityDuration.put(attacker,0L);
            Bukkit.getOnlinePlayers().forEach(player -> player.showPlayer(attacker));
            attacker.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
        Location a0 = victim.getLocation();
        Location b0 = attacker.getLocation();
        Vector vector = a0.getDirection();
        vector.setY(0);
        Location a1 = a0.clone().add(vector.clone().multiply(-1));
        double x = MathUtil.getPlaneDistance(a0,a1),y = MathUtil.getPlaneDistance(a0,b0),z = MathUtil.getPlaneDistance(a1,b0);
        if (MathUtil.getCosine(x,y,z) >= 0.5) {
            a += 0.15;
            if (!((double) victim.getNoDamageTicks() > (double)victim.getMaximumNoDamageTicks() / 2.0)){
                if (InvisibilityCD.containsKey(attacker) && InvisibilityCD.get(attacker) > System.currentTimeMillis()) {
                    if (InvisibilityCD.get(attacker) - System.currentTimeMillis() >= 3 * 1000L) {
                        InvisibilityCD.put(attacker, InvisibilityCD.get(attacker) - 3000L);
                    } else InvisibilityCD.put(attacker, 0L);
                }
            }
            for (int i = 0; i <= 60; i++) a0.getWorld().playEffect(victim.getEyeLocation(), Effect.TILE_BREAK, 152);
        }
        e.setDamage(e.getDamage() * a);
    }

    public void Invisibility(Player player){
        if (InvisibilityCD.containsKey(player) && InvisibilityCD.get(player) > System.currentTimeMillis()) {
            return;
        }else {
            Bukkit.getOnlinePlayers().forEach(player1 -> player1.hidePlayer(player));
            InvisibilityDuration.put(player, System.currentTimeMillis() + 6000L);
            InvisibilityCD.put(player, System.currentTimeMillis() + 45 * 1000L);
            playerManager pM= new playerManager();
            pM.addEffect(player, PotionEffectType.INVISIBILITY, 6 * 20, 0, false);
            pM.addEffect(player, PotionEffectType.SPEED, 6 * 20, 0, false);
            player.sendMessage(ChatColor.GREEN + "你的隐匿身形技能已激活");
            player.playSound(player.getLocation(), Sound.WITHER_IDLE, 1.0F, 1.0F);
            new BukkitRunnable() {
                @Override
                public void run() {
                    //player.sendMessage(String.valueOf(InvisibilityDuration.get(player) - System.currentTimeMillis()));
                    if (InvisibilityDuration.get(player) - System.currentTimeMillis() <= 50 && InvisibilityDuration.get(player) > System.currentTimeMillis()) {
                        Bukkit.getOnlinePlayers().forEach(player1 -> player1.showPlayer(player));
                        new playerManager().addEffect(player, PotionEffectType.REGENERATION, 160, 0, false);
                    }
                }
            }.runTaskLater(Main.getPlugin(Main.class), 6 * 20L);
        }
    }
    private boolean isInvisible(Player player){
        return InvisibilityDuration.containsKey(player) && InvisibilityDuration.get(player) > System.currentTimeMillis();
    }
    public void Display(Player player){
        ActionbarManager actionbarManager = new ActionbarManager();
        if (InvisibilityCD.containsKey(player) && InvisibilityCD.get(player) > System.currentTimeMillis()){
            String s = (InvisibilityCD.get(player) - System.currentTimeMillis()) / 1000 + "秒";
            actionbarManager.sendActionbar(player,ChatColor.GRAY + s , ChatColor.DARK_RED + ChatColor.BOLD.toString() + "隐匿身形 ");
        }else {
            actionbarManager.sendActionbar(player,ChatColor.GREEN + ChatColor.BOLD.toString() + "✔" , ChatColor.DARK_RED + ChatColor.BOLD.toString() + "隐匿身形 ");
        }
    }
}
