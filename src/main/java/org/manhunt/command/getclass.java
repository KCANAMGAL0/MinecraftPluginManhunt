package org.manhunt.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.manhunt.MhClass.MhClassManager;

public class getclass implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        MhClassManager mhClassManager = new MhClassManager();
        player.sendMessage(mhClassManager.getPlayerClass(player));
        return false;
    }
}
