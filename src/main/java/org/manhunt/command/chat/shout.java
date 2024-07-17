package org.manhunt.command.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.manhunt.player.team.MhTeam;

import java.util.Arrays;

public class shout implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            MhTeam mhTeam = new MhTeam();
            String[] strings1 = new String[strings.length + 1];
            for (int i = 1 ; i < strings.length + 1 ; i++) strings1[i] = ChatColor.WHITE + strings[i - 1] + " ";
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (mhTeam.getMhTeam(sender).equals("RUNNER")) {
                    strings1[0] = ChatColor.GOLD + "[全体] " + ChatColor.GREEN + sender.getName() + ChatColor.WHITE + ": ";
                }else if (mhTeam.getMhTeam(sender).equals("HUNTER")) {
                    strings1[0] = ChatColor.GOLD + "[全体] " + ChatColor.RED + sender.getName() + ChatColor.WHITE + ": ";
                }else {
                    strings1[0] = ChatColor.GOLD + "[全体] " + ChatColor.GRAY + sender.getName() + ChatColor.WHITE + ": ";
                }
                player.sendMessage(toStringMessage(strings1));
            }
        }
        return false;
    }
    private String toStringMessage(String[] s){
        StringBuilder stringBuffer = new StringBuilder();
        for (String string : s) stringBuffer.append(string);
        return stringBuffer.toString();
    }
}
