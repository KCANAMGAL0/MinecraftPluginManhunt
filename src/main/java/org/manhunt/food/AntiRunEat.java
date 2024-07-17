package org.manhunt.food;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.manhunt.Utils.ItemStackUtil;

import java.util.HashMap;

public class AntiRunEat implements Listener {
    public static HashMap<Player,Long> EatCD;
    static {
        EatCD = new HashMap<>();
    }
    @EventHandler
    public void a(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if (isHeldFood(e.getItem().getType()) && !ItemStackUtil.isEmpty(e.getItem())){
                Player player = e.getPlayer();
                if (EatCD.containsKey(player) && EatCD.get(player) > System.currentTimeMillis()){
                    e.setCancelled(true);
                }
            }

        }
    }
    @EventHandler
    public void b(PlayerDropItemEvent e){
        if (!e.isCancelled()){
            Player player = e.getPlayer();
            if (isHeldFood(e.getItemDrop().getItemStack().getType())){
                EatCD.put(player, System.currentTimeMillis() + 500L);

            }
        }
    }

    private boolean isHeldFood(Material material){
        for (Material material1 : new foodList().getFoodList()){
            return material.equals(material1);
        }
        return false;
    }
}

