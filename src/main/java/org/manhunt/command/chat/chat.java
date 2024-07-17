package org.manhunt.command.chat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class chat implements CommandExecutor {
    public static HashMap<Player,String> ChatType;
    static {
        ChatType = new HashMap<>();
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings[0].equals("all") || strings[0].equals("a")) {
                ChatType.put(player,"all");
                player.sendMessage(ChatColor.RED + "你当前处于全体聊天频道");
            }else if (strings[0].equals("team") || strings[0].equals("t")){
                ChatType.put(player,"team");
                player.sendMessage(ChatColor.AQUA + "你当前处于队伍聊天频道");
            }
        }
        return false;
    }
}
