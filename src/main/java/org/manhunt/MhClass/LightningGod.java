package org.manhunt.MhClass;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.manhunt.player.actionbar.ActionbarManager;
import org.manhunt.player.playerManager;

import java.util.HashMap;

public class LightningGod extends MhClassManager {
    public static HashMap<Player,Integer> AttackTime;
    public double dmg = 3;
    static {
        AttackTime = new HashMap<>();
    }
    public void Skill0(Player player, EntityDamageByEntityEvent e, Player victim){
        playerManager pM = new playerManager();
        double a = 1;
        if (pM.hasEffect(player,PotionEffectType.INCREASE_DAMAGE,0)) a -= 0.4348;
        if (AttackTime.containsKey(player) && AttackTime.get(player) == 5) { //技能
            victim.damage((dmg + e.getFinalDamage()) * a);
            e.setDamage(0);
            player.sendMessage(ChatColor.GREEN + "你的雷击技能已激活!");
            victim.getWorld().strikeLightningEffect(victim.getLocation());
            AttackTime.put(player,0);
        }else {
            e.setDamage(e.getDamage() * a);
            if (AttackTime.containsKey(player)) {
                AttackTime.put(player,AttackTime.get(player) + 1);
            }else {
                AttackTime.put(player,0);
            }
        }
    }

    public void SkillDisplay(Player player){
        ActionbarManager actionbarManager = new ActionbarManager();
        if (AttackTime.containsKey(player)) {
            if (AttackTime.get(player) <= 4) {
                String a = String.valueOf(AttackTime.get(player));
                actionbarManager.sendActionbar(player, "", ChatColor.AQUA + ChatColor.BOLD.toString() + "雷击 " + ChatColor.GRAY + a + ChatColor.GREEN + "/5");
            }else {
                actionbarManager.sendActionbar(player, "", ChatColor.AQUA + ChatColor.BOLD.toString() + "雷击 " + ChatColor.GREEN + ChatColor.BOLD + "✔");
            }
        }
    }
}
