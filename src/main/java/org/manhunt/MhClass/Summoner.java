package org.manhunt.MhClass;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.manhunt.Utils.MathUtil;
import org.manhunt.player.team.MhTeam;

import java.util.HashMap;

public class Summoner extends MhClassManager {
    public static HashMap<Zombie,String> specialZombie;
    static {
        specialZombie = new HashMap<>();
    }
    public void SummonZombie(Player player){
        for (int i = 0;i < 3 ; i++){
            Zombie zombie = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
            zombie.setMaxHealth(20);
            zombie.setHealth(zombie.getMaxHealth());
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,10 * 20,4));
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,60 * 20,1));
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,60 * 20,0));
            Player targetPlayer = player;
            for (Player player1 : new MhTeam().getRunnerPlayers()){
                if (player1.getWorld().equals(player.getWorld()) && player1.getLocation().distance(player.getLocation()) <= 10) {
                    targetPlayer = player1;
                    break;
                }
            }
            EntityTargetEvent event = new EntityTargetEvent(zombie,targetPlayer, EntityTargetEvent.TargetReason.REINFORCEMENT_TARGET);
            Bukkit.getPluginManager().callEvent(event);
        }
    }


}
