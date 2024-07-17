package org.manhunt.Utils;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.manhunt.Main;

import java.util.HashMap;

public class BossbarUtil {
    public static HashMap<Player, EntityWither> playerEntityWitherHashMap;
    public static HashMap<Player, EntityEnderDragon> playerEntityEnderDragonHashMap;
    static {
        playerEntityWitherHashMap = new HashMap<>();
        playerEntityEnderDragonHashMap = new HashMap<>();
    }
    public void addBossbar(Player player,String text){
        Location location = player.getLocation(),currentLocation;
        EntityWither entityWither = new EntityWither(((CraftWorld) player.getWorld()).getHandle());
        entityWither.setCustomName(text);
        entityWither.setCustomNameVisible(true);
        entityWither.setInvisible(true);
        NBTTagCompound compound = new NBTTagCompound();
        //entityWither.setInvisible(true);
        compound.setInt("NoAI", 1);
        compound.setInt("Invulnerable", 1);
        compound.setInt("Silent", 1);
        entityWither.f(compound);
        Vector vector = location.getDirection();
        if (vector.getY() > 0.8) currentLocation = location.clone().add(location.getDirection().clone().multiply(20));
        else if (vector.getY() < -0.6) currentLocation = location.clone().add(location.getDirection().clone().multiply(20));
        else currentLocation = location.clone().add(location.getDirection().clone().multiply(20));
        entityWither.setLocation(currentLocation.getX(),currentLocation.getY(),currentLocation.getZ(),0,0);
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(entityWither);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        playerEntityWitherHashMap.put(player,entityWither);

    }
    public void updateBossbar(Player player,String text) throws Exception {
        if (playerEntityWitherHashMap.containsKey(player)) {
            EntityWither entityWither = playerEntityWitherHashMap.get(player);
            Location location = player.getLocation();
            Vector vector = location.getDirection();
            Location currentLocation;
            entityWither.setCustomName(text);
            if (vector.getY() > 0.8) currentLocation = location.clone().add(location.getDirection().clone().multiply(20));
            else if (vector.getY() < -0.6) currentLocation = location.clone().add(location.getDirection().clone().multiply(20));
            else currentLocation = location.clone().add(location.getDirection().clone().multiply(20));
            currentLocation.setX(currentLocation.getX() - 10);
            entityWither.setLocation(currentLocation.getX(),currentLocation.getY(),currentLocation.getZ(),0,0);
            PacketPlayOutSpawnEntityLiving packet1 = new PacketPlayOutSpawnEntityLiving(entityWither);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet1);
        }else throw new Exception("Entity Wither Not Found");
    }
    public void removeBossbar(Player player){
        if (playerEntityWitherHashMap.containsKey(player)) {
            playerEntityWitherHashMap.get(player).setLocation(player.getLocation().getX(),512 ,player.getLocation().getZ(),0,0);
            PacketPlayOutSpawnEntityLiving packet1 = new PacketPlayOutSpawnEntityLiving( playerEntityWitherHashMap.get(player));
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet1);
            playerEntityWitherHashMap.remove(player);
        }
    }


}
