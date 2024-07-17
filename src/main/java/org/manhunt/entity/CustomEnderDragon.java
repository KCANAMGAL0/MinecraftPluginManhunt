package org.manhunt.entity;

import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderDragon;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CustomEnderDragon extends EntityEnderDragon {
    public CustomEnderDragon(World world) {
        super(((CraftWorld) world).getHandle());
        Player player = Bukkit.getPlayer("a");
        ((CraftLivingEntity) player).getHandle();
    }
}
