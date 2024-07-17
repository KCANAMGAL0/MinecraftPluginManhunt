package org.manhunt.MhClass;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.manhunt.player.playerManager;

public class Archer extends MhClassManager {

    public void SuperArrowSkill(Player attacker, Player victim, EntityDamageByEntityEvent e){
        Arrow arrow = (Arrow) e.getDamager();
        double a = 1;
        if (arrow.isCritical()) {
            a += 0.2;
            if (super.getPlayerClass(victim).equals(MhClasses.Cac.getName())){
                if (Cactus.GetMoreDamage.containsKey(victim) && Cactus.GetMoreDamage.get(victim) > System.currentTimeMillis()){
                    a += 0.3;
                }
            }
            e.setDamage(e.getDamage() * a);
            new playerManager().addEffect(victim, PotionEffectType.SLOW,60,0,false);
            attacker.sendMessage(ChatColor.GREEN + "你的强化箭矢命中了" + ChatColor.AQUA + victim.getName());
            ItemStack returnArrow = new ItemStack(Material.ARROW,1);
            attacker.getInventory().addItem(returnArrow);
        }
    }
}
