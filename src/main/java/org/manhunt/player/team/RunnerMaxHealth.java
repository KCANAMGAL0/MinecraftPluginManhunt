package org.manhunt.player.team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class RunnerMaxHealth extends MhTeam{
    public int getRunnerMaxHealth(){
        int a = getRunnerCount();
        int b = getHunterCount();
        int c = 20;
        switch (a){
            case 1:
                switch (b){
                    case 1:
                        c = 20;
                        break;
                    case 2:
                        c = 36;
                        break;
                    case 3:
                        c = 60;
                        break;
                    case 4:
                        c = 80;
                        break;
                    case 5:
                        c = 80;
                        break;
                    case 6:
                        c = 80;
                        break;
                    case 7:
                        c = 80;
                        break;
                    case 8:
                        c = 80;
                        break;
                }
            break;
            case 2:
                switch (b){
                    case 1:
                        c = 20;
                        break;
                    case 2:
                        c = 20;
                        break;
                    case 3:
                        c = 26;
                        break;
                    case 4:
                        c = 40;
                        break;
                    case 5:
                        c = 50;
                        break;
                    case 6:
                        c = 60;
                        break;
                    case 7:
                        c = 70;
                        break;
                    case 8:
                        c = 80;
                        break;
                }
            break;
            case 3:
                switch (b){
                    case 1:
                        c = 20;
                        break;
                    case 2:
                        c = 20;
                        break;
                    case 3:
                        c = 20;
                        break;
                    case 4:
                        c = 22;
                        break;
                    case 5:
                        c = 30;
                        break;
                    case 6:
                        c = 40;
                        break;
                    case 7:
                        c = 46;
                        break;
                    case 8:
                        c = 60;
                        break;
                }
            break;
            case 4:
                switch (b){
                    case 1:
                        c = 20;
                        break;
                    case 2:
                        c = 20;
                        break;
                    case 3:
                        c = 20;
                        break;
                    case 4:
                        c = 20;
                        break;
                    case 5:
                        c = 20;
                        break;
                    case 6:
                        c = 28;
                        break;
                    case 7:
                        c = 36;
                        break;
                    case 8:
                        c = 44;
                        break;
                }
            break;
        }
        return c;
    }
    public void setRunnerMaxHealth(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (getMhTeam(player).equals("RUNNER")){
                player.setMaxHealth(getRunnerMaxHealth());
            }
        }
    }
}
