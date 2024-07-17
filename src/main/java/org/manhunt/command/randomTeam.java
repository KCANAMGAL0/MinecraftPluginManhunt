package org.manhunt.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.manhunt.player.team.MhTeam;

public class randomTeam implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        MhTeam mhTeam = new MhTeam();
        Player sender = (Player) commandSender;
        mhTeam.resetAllPlayersTeam();
        int a,b=0;
        for (Player p : Bukkit.getOnlinePlayers()){
            b++;
        }
        int[] c = getTeamPlayerCount(b);
        for (Player player : Bukkit.getOnlinePlayers()){
            a = (int) (Math.random() * 100 + 1);
            if (a > 50){
                if (mhTeam.getRunnerCount() < c[0]){
                    sender.sendMessage(player.getName() + "加入了勇者阵营!调试代码A2");
                    mhTeam.setPlayerTeam(player,"RUNNER");
                }else if (mhTeam.getHunterCount() < c[1]){
                    mhTeam.setPlayerTeam(player,"HUNTER");
                    sender.sendMessage(player.getName() + "加入了猎人阵营!调试代码A1");
                }
            }else {
                if (mhTeam.getHunterCount() < c[1]){
                    sender.sendMessage(player.getName() + "加入了猎人阵营!调试代码B0");
                    mhTeam.setPlayerTeam(player,"HUNTER");
                }else if (mhTeam.getRunnerCount() < c[0]){
                    mhTeam.setPlayerTeam(player,"RUNNER");
                    sender.sendMessage(player.getName() + "加入了勇者阵营!调试代码B1");
                }
            }
        }
        sender.sendMessage("完成!");
        return false;
    }
    public int[] getTeamPlayerCount(int allPlayerCount){
        int[] a = new int[]{0,0};
        if (allPlayerCount < 3){
            return a;
        }else {
            switch (allPlayerCount){
                case 3:
                    a = new int[]{1,2};
                    break;
                case 4:
                    a = new int[]{1,3};
                    break;
                case 5:
                    a = new int[]{1,4};
                    break;
                case 6:
                    a = new int[]{2,4};
                    break;
                case 7:
                    a = new int[]{2,5};
                    break;
                case 8:
                    a = new int[]{2,6};
                    break;
                case 9:
                    a = new int[]{3,6};
                    break;
                case 10:
                    a = new int[]{3,7};
                    break;
                case 11:
                    a = new int[]{3,8};
                    break;
                case 12:
                    a = new int[]{4,8};
                    break;
            }
        }
        return a;
    }
}
