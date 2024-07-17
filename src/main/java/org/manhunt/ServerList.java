package org.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.manhunt.player.team.MhTeam;

public class ServerList implements Listener {
    @EventHandler
    public void onPing(ServerListPingEvent e){
        MhTeam mhTeam = new MhTeam();
        int a = 0;
        for (Player player : Bukkit.getOnlinePlayers()){
            a++;
        }
        e.setMotd(ChatColor.RED + "火速进入," + "目前(" + a + "/12)");
    }
}
