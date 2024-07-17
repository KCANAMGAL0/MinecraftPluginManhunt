package org.manhunt.MhClass;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.manhunt.player.actionbar.ActionbarManager;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;

import java.util.HashMap;

public class Paladin extends MhClassManager {
    public static HashMap<Player,Long> Skill1CD;
    static {
        Skill1CD = new HashMap<>();
    }
    public void Skill1(Player player){
        if (Skill1CD.containsKey(player) && Skill1CD.get(player) > System.currentTimeMillis()){
            return;
        }else {
            MhTeam mhTeam = new MhTeam();
            playerManager pM = new playerManager();
            for (Player player1 : mhTeam.getRunnerPlayers()) {
                pM.addEffect(player1, PotionEffectType.ABSORPTION, 300, 1, false);
                pM.addEffect(player1, PotionEffectType.DAMAGE_RESISTANCE, 300, 1, false);
                player1.getWorld().playSound(player1.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);
                if (!player1.equals(player)) {
                    player1.sendMessage(ChatColor.GREEN + "你受到了" + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + "的圣光庇护效果");
                }
            }
            player.sendMessage(ChatColor.GREEN + "你的圣光庇护技能已激活!");
            Skill1CD.put(player,System.currentTimeMillis() + 50 * 1000L);
        }
    }

    public void SkillDisplay(Player player){
        ActionbarManager a = new ActionbarManager();
        if (Skill1CD.containsKey(player) && Skill1CD.get(player) > System.currentTimeMillis()){
            String b = String.valueOf((Skill1CD.get(player) - System.currentTimeMillis()) / 1000) + "秒";
            a.sendActionbar(player,"",ChatColor.YELLOW + ChatColor.BOLD.toString() + "圣光庇护 " + ChatColor.GRAY + b);
        }else {
            a.sendActionbar(player,"",ChatColor.YELLOW + ChatColor.BOLD.toString() + "圣光庇护 " + ChatColor.GREEN + ChatColor.BOLD.toString() + "✔");
        }
    }
}
