package org.manhunt.MhClass;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RightClickSword implements Listener {
    @EventHandler
    public void rightClickOnSword(PlayerInteractEvent e){
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Player player = e.getPlayer();
                Material material = player.getItemInHand().getType();
                if (material.equals(Material.DIAMOND_SWORD) || material.equals(Material.IRON_SWORD) || material.equals(Material.STONE_SWORD)) {
                    new Cactus().ForwardSkill(player);
                }
            }
    }

}
