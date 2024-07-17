package org.manhunt.player.actionbar;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionbarManager {
    public void sendActionbar(Player player,String s,String color) {
        PacketPlayOutChat p = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + color + s + "\"}"), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(p);
    }
}
