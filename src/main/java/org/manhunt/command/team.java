package org.manhunt.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.manhunt.player.team.MhTeam;

public class team implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player sender = (Player) commandSender;
        MhTeam mhTeam = new MhTeam();
        if (strings[0].equals("get") && strings.length==1){
            if (mhTeam.getMhTeam(sender).equals("RUNNER")) {
                sender.sendMessage(ChatColor.AQUA + "你当前的阵营是" + ChatColor.GREEN + "勇者");
            }else if (mhTeam.getMhTeam(sender).equals("HUNTER")) {
                sender.sendMessage(ChatColor.AQUA + "你当前的阵营是" + ChatColor.RED + "猎人");
            }else if (mhTeam.getMhTeam(sender).equals("SPE")){
                sender.sendMessage(ChatColor.AQUA + "你当前没有加入任何阵营!");
            }
        }else if (strings[0].equals("set") && strings.length==3) {
            Player player = Bukkit.getPlayer(strings[1]);
            if (strings[2].equals("RUNNER")) {
                mhTeam.setPlayerTeam(player,"RUNNER");
                player.sendMessage(ChatColor.GREEN + "你加入了勇者阵营!");
            }else if (strings[2].equals("HUNTER")) {
                mhTeam.setPlayerTeam(player,"HUNTER");
                player.sendMessage(ChatColor.GREEN + "你加入了猎人阵营!");
            }else if (strings[2].equals("SPE")){
                mhTeam.setPlayerTeam(player,"SPE");
                player.sendMessage(ChatColor.GREEN + "你离开了所有阵营!");
            }
        }
        return false;
    }
}
