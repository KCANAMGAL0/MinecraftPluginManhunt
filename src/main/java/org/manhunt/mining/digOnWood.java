package org.manhunt.mining;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;
import org.manhunt.player.playerManager;
import javax.swing.plaf.PanelUI;

public class digOnWood implements Listener {
    @EventHandler
    public void DigOnWood(PlayerInteractEvent e){
        playerManager pM = new playerManager();
       // e.getPlayer().sendMessage("0");
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            //e.getPlayer().sendMessage("1");
            //e.getPlayer().sendMessage(e.getClickedBlock().toString());
            if (e.getClickedBlock().getType().equals(Material.LOG) && e.getClickedBlock().getY() >= 52){
                //e.getPlayer().sendMessage("2");
                pM.addEffect(e.getPlayer(), PotionEffectType.FAST_DIGGING,40,2,false);
            }else if (e.getClickedBlock().getType().equals(Material.SAND) && e.getClickedBlock().getY() >= 52) {
                pM.addEffect(e.getPlayer(), PotionEffectType.FAST_DIGGING,40,2,false);
            }else if (e.getClickedBlock().getType().equals(Material.LOG_2) && e.getClickedBlock().getY() >= 52){
                pM.addEffect(e.getPlayer(), PotionEffectType.FAST_DIGGING,40,2,false);
            }
        }
    }
}
