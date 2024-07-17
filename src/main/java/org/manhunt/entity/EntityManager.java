package org.manhunt.entity;

import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.manhunt.player.team.MhTeam;

public class EntityManager implements Listener {
    @EventHandler
    public void a(EntityTargetEvent e){
        if (e.getEntity() instanceof Zombie){
            if (e.getTarget() instanceof Player){
                Zombie zombie = (Zombie) e.getEntity();
                Player player = (Player) e.getTarget();
                if (new MhTeam().isHunter(player) && CustomEntityTypes.isSpecial(zombie)) e.setCancelled(true);
            }else e.setCancelled(true);
        }
    }
}
