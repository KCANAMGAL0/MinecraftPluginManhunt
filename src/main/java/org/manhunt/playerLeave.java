package org.manhunt;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.manhunt.debug.PacketListener;
import org.manhunt.game.gameManager;
import org.manhunt.player.PlayerDeath;
import org.manhunt.player.team.MhTeam;

public class playerLeave implements Listener {
    @EventHandler
    public void playerOnLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        PacketListener.end(player);
        MhTeam mhTeam = new MhTeam();
        gameManager gM = new gameManager();
        if (!mhTeam.getMhTeam(player).equals("SPE")) {
            player.damage(32767);
            /*
            for (int i = 0;i <= 35 ; i++){
                if (!pD.isEmpty(player.getInventory(),i)){
                    pD.setItemDrop(player.getWorld(),player.getLocation(),player.getInventory().getItem(i).getType(),player.getInventory().getItem(i).getAmount());
                    player.getInventory().clear();
                }
            }
             */
            gM.resetPlayersData(player);
            e.setQuitMessage(player.getName() + "离开了服务器");
        }
    }
}
