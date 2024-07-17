package org.manhunt.MhClass;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.manhunt.player.actionbar.ActionbarManager;
import org.manhunt.player.playerManager;

import java.util.HashMap;

public class EvilScorcher extends MhClassManager {
    public static HashMap<Player,Long> LavaBucketCD;
    static {
        LavaBucketCD = new HashMap<>();
    }
    public void BurnSkill(Player attacker,EntityDamageByEntityEvent e,Player victim){
        double a = 1;
        playerManager pM = new playerManager();
        if (pM.hasEffect(attacker, PotionEffectType.INCREASE_DAMAGE,0)) a -= 0.4348;
        if (victim.getFireTicks() > 0) a+= 0.15;
        e.setDamage(e.getDamage() * a);
    }

    public void getLavaBucket(Player player){
        if (LavaBucketCD.containsKey(player) && LavaBucketCD.get(player) > System.currentTimeMillis()) {
            return;
        }else {
            ItemStack lava_Bucket = new ItemStack(Material.LAVA_BUCKET, 1);
            player.getInventory().addItem(lava_Bucket);
            player.sendMessage(ChatColor.GREEN + "你的岩浆桶技能已激活!");
            player.playSound(player.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
            LavaBucketCD.put(player,System.currentTimeMillis() + (60 * 1000L));
        }
    }
    public void LavaBucketSkillDisplay(Player player){
        ActionbarManager actionbarManager = new ActionbarManager();
        if (LavaBucketCD.containsKey(player) && LavaBucketCD.get(player) > System.currentTimeMillis()) {
            String a = String.valueOf((int) (LavaBucketCD.get(player) - System.currentTimeMillis()) / 1000);
            actionbarManager.sendActionbar(player,ChatColor.GRAY + a + "秒",ChatColor.RED + ChatColor.BOLD.toString() + "岩浆桶 ");
        }else {
            actionbarManager.sendActionbar(player,ChatColor.GREEN + ChatColor.BOLD.toString() + "✔",ChatColor.RED + ChatColor.BOLD.toString() + "岩浆桶 ");
        }

    }
}
