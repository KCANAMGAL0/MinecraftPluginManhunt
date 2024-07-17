package org.manhunt.command;

import com.mojang.authlib.BaseUserAuthentication;
import javafx.print.PageLayout;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.manhunt.MhClass.MhClassManager;

import javax.print.Doc;

public class setclass implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        MhClassManager mhClassManager = new MhClassManager();
        if (!strings[0].isEmpty() && !strings[1].isEmpty()) {
            Player player = Bukkit.getPlayer(strings[0]);
            for (String mhclass : mhClassManager.getMhClasses()){
                if (strings[1].equals(mhclass)){
                    mhClassManager.setPlayerClassInfo(player,mhclass);
                }
            }
            player.sendMessage("你当前的职业为 " + mhClassManager.getClassStringName(mhClassManager.getPlayerClass(player)));
        }

        return false;
    }
}
