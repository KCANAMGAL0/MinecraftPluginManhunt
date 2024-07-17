package org.manhunt.player.compass;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.manhunt.player.team.MhTeam;


public class rightClickOnCompass implements Listener {
    @EventHandler
    public void playerRClickOnCompass(PlayerInteractEvent e){
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            MhTeam mhTeam = new MhTeam();
            if (player.getItemInHand().getType().equals(Material.COMPASS)) {
                if (mhTeam.getMhTeam(player).equals("HUNTER")) {
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
                    new menu().createMenuHunterPage1(player);
                }else if (mhTeam.getMhTeam(player).equals("RUNNER")) {
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
                    new menu().createMenuRunnerPage1(player);
                }
            }
        }
    }
}
