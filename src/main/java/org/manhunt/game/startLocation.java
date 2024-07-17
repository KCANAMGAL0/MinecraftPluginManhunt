package org.manhunt.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.manhunt.Main;
import org.manhunt.player.team.MhTeam;

import java.util.List;

public class startLocation {
    Plugin plugin = Main.getPlugin(Main.class);

    public void tpToStartLocation(){
        MhTeam mhTeam = new MhTeam();

        List<Integer> runnerStartLocation = plugin.getConfig().getIntegerList("runnerStartLocation");
        List<Integer> hunterStartLocation = plugin.getConfig().getIntegerList("HunterSpawn");
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (mhTeam.isRunner(player)) {
                Location location = new Location(Bukkit.getWorld("world"),runnerStartLocation.get(0),runnerStartLocation.get(1),runnerStartLocation.get(2));
                player.teleport(location);
            } else if (mhTeam.isHunter(player)) {
                Location location = new Location(Bukkit.getWorld("world"),hunterStartLocation.get(0),hunterStartLocation.get(1),hunterStartLocation.get(2));
                player.teleport(location);
            }else {
                Location location = new Location(Bukkit.getWorld("world"),hunterStartLocation.get(0),hunterStartLocation.get(1),hunterStartLocation.get(2));
                player.teleport(location);
            }
        }
    }
}
