package org.manhunt.MhClass;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.manhunt.player.actionbar.ActionbarManager;
import org.manhunt.player.playerManager;

import java.util.HashMap;

public class Doctor extends MhClassManager {
    public static HashMap<Player,Double> DamageGet;
    public static HashMap<Player,Long> DocCoolDown,HealArrowCD;
    public static HashMap<Player,Integer> ParticleTimer;
    static {
        DamageGet = new HashMap<>();
        DocCoolDown = new HashMap<>();
        HealArrowCD = new HashMap<>();
        ParticleTimer = new HashMap<>();
    }
    public void RegenSkillDisplay(Player player){
        ActionbarManager actionbarManager = new ActionbarManager();
        if (DocCoolDown.containsKey(player) && DocCoolDown.get(player) > System.currentTimeMillis()){
            int a = (int) ((DocCoolDown.get(player) -System.currentTimeMillis()) / 1000);
            String b = String.valueOf(a);
            if (HealArrowCD.containsKey(player) && HealArrowCD.get(player) > System.currentTimeMillis()) {
                int c = (int) ((HealArrowCD.get(player) -System.currentTimeMillis()) / 1000);
                String d = String.valueOf(c);
                actionbarManager.sendActionbar(player, ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "自愈 " + ChatColor.GRAY + b + "秒", ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "治疗箭矢 " + ChatColor.GRAY + d + "秒 ");
            }else {
                actionbarManager.sendActionbar(player, ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "自愈 " + ChatColor.GRAY + b + "秒", ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "治疗箭矢 " + ChatColor.GREEN + ChatColor.BOLD + "✔ ");
            }
        }else {
            if (HealArrowCD.containsKey(player) && HealArrowCD.get(player) > System.currentTimeMillis()) {
                int c = (int) ((HealArrowCD.get(player) -System.currentTimeMillis()) / 1000);
                String d = String.valueOf(c);
                actionbarManager.sendActionbar(player, ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "自愈 " + ChatColor.GRAY + Keep1DecimalPlace(DamageGet.get(player)) + ChatColor.GREEN + "/10.0", ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "治疗箭矢 " + ChatColor.GRAY + d + "秒 ");
            }else {
                actionbarManager.sendActionbar(player, ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "自愈 " + ChatColor.GRAY + Keep1DecimalPlace(DamageGet.get(player)) + ChatColor.GREEN + "/10.0", ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "治疗箭矢 " + ChatColor.GREEN + ChatColor.BOLD + "✔ ");
            }
        }
    }
    public double Keep1DecimalPlace(double a) {
        int b = (int) (a*10);
        return (double) b/10;
    }

    public void RegenerationSkill(Player player, EntityDamageByEntityEvent e,Player victim){
        playerManager pM = new playerManager();
        double a = 1;
        if (pM.hasEffect(victim,PotionEffectType.INCREASE_DAMAGE,0)){
            a -= 0.4348;
        }
        e.setDamage(e.getDamage() * a);
        if (DocCoolDown.containsKey(player) && DocCoolDown.get(player) > System.currentTimeMillis()) {
            return;
        }else {
            if (DamageGet.get(player) + e.getFinalDamage() >= 10) {
                pM.addEffect(player, PotionEffectType.REGENERATION, 60, 2, false);
                DocCoolDown.put(player, System.currentTimeMillis() + 12 * 1000L);
                player.playSound(player.getLocation(), Sound.ORB_PICKUP,1.0F,1.0F);
                DamageGet.put(player,0d);
                player.getWorld().playEffect(new Location(player.getWorld(),player.getLocation().getX(),player.getLocation().getY() + 2,player.getLocation().getZ()), Effect.HEART,0);
            } else {
                DamageGet.put(player, DamageGet.get(player) + e.getFinalDamage());
            }
        }
    }
    public void resetRegenSkill(Player player){
        DamageGet.put(player,0d);
    }
    public void addDamage(Player player,double a){
        if (DocCoolDown.containsKey(player) && DocCoolDown.get(player) > System.currentTimeMillis()){
            return;
        }else {
            if (DamageGet.containsKey(player)) {
                DamageGet.put(player, DamageGet.get(player) + a);
                if (DamageGet.get(player) >= 10) {
                    new playerManager().addEffect(player, PotionEffectType.REGENERATION, 60, 2, false);
                    DocCoolDown.put(player, System.currentTimeMillis() + 12 * 1000L);
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                    DamageGet.put(player, 0d);
                    player.getWorld().playEffect(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 2, player.getLocation().getZ()), Effect.HEART, 0);
                }
            } else {
                DamageGet.put(player, 0d);
            }
        }
    }

    public void HealArrowSkill(Player attacker,Player victim){
        playerManager pM = new playerManager();
        if (HealArrowCD.containsKey(attacker) && HealArrowCD.get(attacker) > System.currentTimeMillis()){
            return;
        }else {
            pM.Heal(victim, 4.0d);
            victim.sendMessage(ChatColor.GREEN + "你被" + attacker.getName() + "的治疗箭矢恢复了4.0生命值!");
            victim.playSound(victim.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            attacker.sendMessage(ChatColor.GREEN + "你的治疗箭矢为" + victim.getName() + "恢复了4.0生命值!");
            attacker.playSound(attacker.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            HealArrowCD.put(attacker,System.currentTimeMillis() + 3000L);
        }
    }
}
