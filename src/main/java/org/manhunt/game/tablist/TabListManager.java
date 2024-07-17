package org.manhunt.game.tablist;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.*;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class TabListManager {

    public void sendTablist(Player p, String Title, String subTitle) {
        if(Title == null) Title = "";
        if(subTitle == null) subTitle = "";

        IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\":\"" + Title+ "\"}");
        IChatBaseComponent tabSubTitle = ChatSerializer.a("{\"text\":\"" + subTitle + "\"}");

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabTitle);

        try {
            Field field = packet.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(packet, tabSubTitle);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        }
        }
}
