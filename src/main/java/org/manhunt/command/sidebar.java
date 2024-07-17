package org.manhunt.command;

import org.manhunt.game.*;
import org.manhunt.player.team.MhTeam;
import org.manhunt.sidebar.Sidebar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sidebar implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        Sidebar sidebar = new Sidebar();
        if (strings[0].equalsIgnoreCase("waiting")){
            sidebar.buildWaitingSideBar(player);
        }else if (strings[0].equalsIgnoreCase("growing")) {
            sidebar.buildGrowingSidebar(player);
        }else if (strings[0].equalsIgnoreCase("prepare")) {
            sidebar.buildPrepareSideBar(player);
            player.sendMessage(String.valueOf(new MhTeam().getRunnerCount()));
        }

        return false;
    }
}
