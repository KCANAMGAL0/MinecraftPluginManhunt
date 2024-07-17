package org.manhunt.food;

import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateHealth;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;

import org.bukkit.scheduler.BukkitRunnable;
import org.manhunt.Main;
import org.manhunt.MhClass.MhClassManager;
import org.manhunt.MhClass.Paladin;
import org.manhunt.player.playerManager;

import java.util.HashMap;
import java.util.List;

public class eatFood implements Listener {
    public static HashMap<Player, BukkitRunnable> EatRunnable;
    static {
        EatRunnable = new HashMap<>();
    }

    @EventHandler
    public void eatFoodEffect(PlayerItemConsumeEvent e) {
        List<Material> cookedFood;
        cookedFood = new foodList().getCookedFood();
        List<Material> cooklessFood;
        cooklessFood = new foodList().getCooklessFood();
        playerManager pM = new playerManager();
        Player player = e.getPlayer();
        Material item = e.getItem().getType();
        if (!e.isCancelled()) {
            if (item.equals(Material.GOLDEN_APPLE)) {
                pM.addEffect(player, PotionEffectType.REGENERATION, 100, 1, false);
                pM.addEffect(player, PotionEffectType.ABSORPTION, 2400, 1, false);
                player.setSaturation(20);
                EatRunnable(player,3.5F);
            } else if (item.equals(Material.BREAD)) {
                EatRunnable(player,1.5F);
                player.setSaturation(20);

            } else if (item.equals(Material.APPLE)) {
                EatRunnable(player,2F);
                player.setSaturation(20);
            }
            for (Material cooklessfood : cooklessFood) {
                if (item.equals(cooklessfood)) {
                    EatRunnable(player,2F);
                    player.setSaturation(20);
                }
            }
            for (Material cookedfood : cookedFood) {
                if (item.equals(cookedfood)) {
                    EatRunnable(player,3.5F);
                    player.setSaturation(20);
                }
            }
        }
    }
    /*
    @EventHandler
    public void foodRightClick(PlayerInteractEvent e) {
        List<Material> foodList = new ArrayList<>();
        foodList = new foodList().getFoodList();
        Player player = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (player.getFoodLevel() > 19) {
                for (Material food : foodList) {
                    if (player.getItemInHand().getType().equals(food)) {
                        player.setFoodLevel(19);
                    }
                }
            }
        }
    }

     */
    private void scheduleSetFoodLevel(Player player,int value,long scheduleTime) {
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {
            @Override
            public void run() {
                if (player.getFoodLevel() > value) {
                    player.setFoodLevel(value);
                }
            }
        },scheduleTime);
    }

    private void EatRunnable(Player player,float durationSec){
        if (EatRunnable.containsKey(player)) {
            EatRunnable.get(player).cancel();
        }
        new BukkitRunnable() {
            int t = (int) (durationSec * 2);
            @Override
            public void run() {
                EatRunnable.put(player, this);

                if (t == 0){
                    player.setFoodLevel(19);
                    this.cancel();
                }else {
                    new playerManager().Heal(player,1d);
                    PacketPlayOutUpdateHealth packet = new PacketPlayOutUpdateHealth((float) player.getHealth(),player.getFoodLevel(),player.getSaturation());
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                    t--;
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class),1L,10L);
    }
}
