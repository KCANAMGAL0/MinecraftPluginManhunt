package org.manhunt.debug;

import io.netty.channel.*;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketListener {
    public static void start(Player player){
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler(){
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext,Object packet) throws Exception {
                super.channelRead(channelHandlerContext,packet);
            }
            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
                super.write(channelHandlerContext,packet,channelPromise);
            }
        };
        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
        pipeline.addBefore("packet_handler",player.getName(),channelDuplexHandler);
    }

    public static void end(Player player){
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(player.getName());
            return null;
        });
    }
}
