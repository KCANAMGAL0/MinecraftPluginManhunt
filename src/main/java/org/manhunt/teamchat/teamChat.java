package org.manhunt.teamchat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.manhunt.command.chat.chat;
import org.manhunt.player.team.MhTeam;

public class teamChat implements Listener {
    @EventHandler
    public void PlayerSendChat(PlayerChatEvent e) {
        Player player = e.getPlayer();
        MhTeam mhTeam = new MhTeam();
        if (chat.ChatType.get(player).equals("team")) {
            if (mhTeam.getMhTeam(player).equals("RUNNER")) {
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    if (mhTeam.isSameTeam(player, player1)) {
                        player1.sendMessage(ChatColor.GREEN + "(勇者)" + player.getName() + ChatColor.WHITE + ": " + e.getMessage());
                    }
                }
            } else if (mhTeam.getMhTeam(player).equals("HUNTER")) {
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    if (mhTeam.isSameTeam(player, player1)) {
                        player1.sendMessage(ChatColor.GREEN + "(猎人) " + player.getName() + ChatColor.WHITE + ": " + e.getMessage());
                    }
                }
            } else if (mhTeam.getMhTeam(player).equals("SPE")) {
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    player1.sendMessage(ChatColor.GRAY + "(旁观者) " + player.getName() + ": " + e.getMessage());
                }
            }
        }else {
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (mhTeam.getMhTeam(player).equals("RUNNER")) {
                    player1.sendMessage(ChatColor.GOLD + "[全体] " + ChatColor.GREEN + player.getName() + ChatColor.WHITE + ": " + ChatColor.WHITE + e.getMessage());
                }else if (mhTeam.getMhTeam(player).equals("HUNTER")) {
                    player1.sendMessage(ChatColor.GOLD + "[全体] " +ChatColor.RED + player.getName() + ChatColor.WHITE + ": " + ChatColor.WHITE + e.getMessage());
                }else {
                    player1.sendMessage(ChatColor.GOLD + "[全体] " + ChatColor.GRAY + player.getName() + ChatColor.WHITE + ": " + e.getMessage());
                }
            }
        }
        e.setCancelled(true);
    }
}
