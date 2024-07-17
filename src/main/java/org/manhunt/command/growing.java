package org.manhunt.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.manhunt.game.GrowingPhase;

public class growing implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        new GrowingPhase().startGrowingPhase();
        return false;
    }
}
