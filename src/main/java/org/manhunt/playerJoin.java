package org.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.manhunt.MhClass.MhClassManager;
import org.manhunt.debug.PacketListener;
import org.manhunt.game.gameManager;
import org.manhunt.game.tablist.TabListManager;
import org.manhunt.player.actionbar.ActionbarManager;
import org.manhunt.player.compass.compassManager;
import org.manhunt.player.nametag.NameTagManager;
import org.manhunt.player.team.MhTeam;
import org.manhunt.player.team.attackOnPlayer;
import org.manhunt.sidebar.Sidebar;

import static org.manhunt.command.chat.chat.ChatType;

public class playerJoin implements Listener {
    @EventHandler
    public void playerJoined(PlayerJoinEvent e){
        Player player = e.getPlayer();
        PacketListener.start(player);
        ChatType.put(player,"all");
        e.setJoinMessage(ChatColor.YELLOW + player.getName() + "加入了服务器");
        TabListManager t = new TabListManager();
        //for (Player player1 : Bukkit.getOnlinePlayers()){
            //new Sidebar().buildWaitingSideBar(player1);
        //}
        if (new gameManager().getGamePhase().equals("waiting")){
            new Sidebar().buildWaitingSideBar(player);
        }
        new MhTeam().setPlayerTeam(player,"SPE");
        new compassManager().resetPlayerTrack(player);
        new attackOnPlayer().resetPlayerLastDamager(player);
        new ActionbarManager().sendActionbar(player,"你加入了服务器" ,ChatColor.YELLOW.toString());
        new MhClassManager().setPlayerClassInfo(player,"unknown");
        t.sendTablist(player, ChatColor.GREEN + "你正在"+ ChatColor.RED + Bukkit.getServer().getIp() + ChatColor.GREEN + "游玩" + ChatColor.RED + "猎人游戏[虫豸版]" + "\n","\n" + ChatColor.RED + "/shout" + ChatColor.GREEN + "可发送全体消息");
    }
}
