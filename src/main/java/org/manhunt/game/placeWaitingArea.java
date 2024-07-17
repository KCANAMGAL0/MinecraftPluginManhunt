package org.manhunt.game;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

public class placeWaitingArea {
    public void create(){
        World world = Bukkit.createWorld(new WorldCreator("WaitingArea"));
        world.setPVP(false);
        for (Player player : Bukkit.getOnlinePlayers()){
            player.teleport(world.getSpawnLocation());
        }
    }


}
