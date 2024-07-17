package org.manhunt.player.team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MhTeam {
    public static HashMap<Player,String> PlayerInfo;
    static {
        PlayerInfo = new HashMap<>();
    }
    public String getMhTeam(Player player){
        return PlayerInfo.get(player);
    }
    public void addAllPlayers(){
        PlayerInfo.clear();
        for (Player p : Bukkit.getOnlinePlayers()){
            PlayerInfo.put(p,"SPE");
        }
    }
    public void removePlayerTeam(Player player){
        PlayerInfo.put(player,"SPE");
    }
    public boolean isSameTeam(Player player1,Player player2){
        boolean a = false;
        if (getMhTeam(player1).equals(getMhTeam(player2))){
            if (!getMhTeam(player1).equals("SPE") && !getMhTeam(player2).equals("SPE")) a = true;
        }
        return a;
    }
    public HashMap<Player, String> getPlayerInfo() {
        return PlayerInfo;
    }
    public void setPlayerTeam(Player player,String team) {
        PlayerInfo.put(player,team);
    }
    public int getRunnerCount(){
        int count = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (getMhTeam(player).equals("RUNNER")){
                count++;
            }
        }
        return count;
    }
    public int getHunterCount() {
        int count = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (getMhTeam(player).equals("HUNTER")) {
                count++;
            }
        }
        return count;
    }
    public void resetAllPlayersTeam(){
        for (Player player : Bukkit.getOnlinePlayers()){
            setPlayerTeam(player,"SPE");
        }
    }

    public List<Player> getRunnerPlayers(){
        List<Player> Runners = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (getMhTeam(player).equals("RUNNER")) {
                Runners.add(player);
            }
        }
        return Runners;
    }
    public List<Player> getHunterPlayers(){
        List<Player> Hunters = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (getMhTeam(player).equals("HUNTER")) {
                Hunters.add(player);
            }
        }
        return Hunters;
    }

    public boolean isHunter(Player player){
        if (getMhTeam(player).equals("HUNTER")){
            return true;
        }else {
            return false;
        }
    }

    public boolean isRunner(Player player){
        if (getMhTeam(player).equals("RUNNER")){
            return true;
        }else {
            return false;
        }
    }
    public boolean isSpe(Player player){
        if (getMhTeam(player).equals("SPE")){
            return true;
        }else {
            return false;
        }
    }
    public MhTeam(){
    }
}
