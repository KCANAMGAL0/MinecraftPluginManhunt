package org.manhunt.player;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.manhunt.Main;
import org.manhunt.MhClass.MachineMaster;
import org.manhunt.MhClass.MhClassManager;
import org.manhunt.MhClass.MhClasses;
import org.manhunt.Utils.MathUtil;
import org.manhunt.player.team.MhTeam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LaunchProjectile implements Listener {
    @EventHandler
    public void a(ProjectileLaunchEvent e){
        if (e.getEntity() instanceof Arrow && e.getEntity().getShooter() instanceof Player){
            Arrow arrow = (Arrow) e.getEntity();
            Player player = (Player) arrow.getShooter();
            arrow.setKnockbackStrength(Math.min(arrow.getKnockbackStrength(), 1));
            if (new MhClassManager().getPlayerClass(player).equals(MhClasses.Sac.getName()) && arrow.isCritical()){
                arrow.setFireTicks(Integer.MAX_VALUE);
            }
            if (new MhClassManager().getPlayerClass(player).equals(MhClasses.MM.getName()) && arrow.isCritical() && player.isSneaking()){
                new MachineMaster().AimAssist(arrow,player);
            }
        }
    }

}
