package org.manhunt.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.manhunt.player.team.attackOnPlayer;

public class getLastDamageFrom implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player sender = (Player) commandSender;
        attackOnPlayer a = new attackOnPlayer();
        if (a.hasDamagedFromPlayer(sender)) {
            sender.sendMessage("你最后一次受到的玩家伤害来源于 "+ a.getPlayerLastDamager(sender).getName());
        }else {
            sender.sendMessage("你没有受到任何来自玩家的伤害");
        }
        return false;
    }
}
