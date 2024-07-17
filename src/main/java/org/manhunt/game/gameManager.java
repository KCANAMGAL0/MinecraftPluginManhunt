package org.manhunt.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.manhunt.Main;
import org.manhunt.MhClass.MhClassManager;
import org.manhunt.player.compass.compassManager;
import org.manhunt.player.team.MhTeam;
import org.manhunt.player.team.attackOnPlayer;

public class gameManager {
    public static String gamePhase; // "waiting","prepare","growing","trackable","end_open"
    public String getGamePhase(){
        return gamePhase;
    }
    public void setGamePhase(String setPhase){
        gamePhase = setPhase;
    }
    public static boolean RunnerEnterNether = false;
    public boolean isPlayerInGame(Player player) {
        if (new MhTeam().getMhTeam(player).equals("RUNNER") || new MhTeam().getMhTeam(player).equals("HUNTER")) {
            return true;
        }else {
            return false;
        }
    }
    public void resetAllPlayersData(boolean isChangeGameMode){
        compassManager cM = new compassManager();
        MhTeam mhTeam = new MhTeam();
        cM.resetAllPlayerTrack();
        mhTeam.resetAllPlayersTeam();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isChangeGameMode) player.setGameMode(GameMode.SPECTATOR);
            new attackOnPlayer().resetPlayerLastDamager(player);
            player.setDisplayName(player.getName());
        }
    }
    public void resetPlayersData(Player player){
        compassManager cM = new compassManager();
        MhTeam mhTeam = new MhTeam();
        MhClassManager mhClassManager = new MhClassManager();
        cM.resetAllPlayerTrack();
        mhTeam.resetAllPlayersTeam();
        mhClassManager.setPlayerClassInfo(player,"unknown");
        player.setGameMode(GameMode.SPECTATOR);
        player.setDisplayName(player.getName());
    }
    public void sendMessageToAllPlayers(String msg) {
        for (Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(msg);
        }
    }
    public void gameOver(Boolean isHunterWin){
        if (isHunterWin) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (new MhTeam().getMhTeam(player).equals("RUNNER")) {
                    player.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "失败"," ");
                    player.getWorld().strikeLightningEffect(player.getLocation());
                }else if (new MhTeam().getMhTeam(player).equals("HUNTER")) {
                    player.sendTitle(ChatColor.GOLD + ChatColor.BOLD.toString() + "胜利"," ");
                }else if (new MhTeam().getMhTeam(player).equals("SPE")){
                    player.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "游戏结束"," ");
                }
            }
        }else{
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (new MhTeam().getMhTeam(player).equals("RUNNER")) {
                    player.sendTitle(ChatColor.GOLD + ChatColor.BOLD.toString() + "胜利"," ");
                }else if (new MhTeam().getMhTeam(player).equals("HUNTER")) {
                    player.getWorld().strikeLightningEffect(player.getLocation());
                    player.sendTitle(ChatColor.RED + ChatColor.BOLD.toString()+ "失败"," ");
                }else if (new MhTeam().getMhTeam(player).equals("SPE")){
                    player.sendTitle(ChatColor.RED + ChatColor.BOLD.toString()+ "游戏结束"," ");
                }
            }
        }
        resetAllPlayersData(true);
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {
            @Override
            public void run() {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "reset");
            }
        },5 * 20L);

    }
}
