package org.manhunt.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.manhunt.Main;
import org.manhunt.MhClass.MhClassManager;
import org.manhunt.player.compass.compassManager;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;
import org.manhunt.sidebar.Sidebar;

public class GrowingPhase {
    public static int growingTimer;
    public void startGrowingPhase(){
        MhTeam mhTeam = new MhTeam();
        playerManager pM = new playerManager();
        Sidebar sidebar = new Sidebar();
        gameManager gM = new gameManager();
        gM.setGamePhase("Growing");
        MhClassManager mhClassManager = new MhClassManager();
        for (Player player : Bukkit.getOnlinePlayers()){
            sidebar.buildGrowingSidebar(player);
        }
        growingTimer = 180; //180
        new BukkitRunnable(){
            @Override
            public void run() {
                if (growingTimer == 0){
                    this.cancel();
                    for (Player player : Bukkit.getOnlinePlayers()){
                        new compassManager().trackPlayer(player);
                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT,1.0F,1.0F);
                        if (mhTeam.getMhTeam(player).equals("HUNTER")) {
                            player.sendTitle("§c§l追踪器启用", ChatColor.GRAY + "§7你现在可以追踪勇者了");
                        } else if (mhTeam.getMhTeam(player).equals("RUNNER")) {
                            player.sendTitle("§c§l追踪器启用", ChatColor.GRAY + "§7猎人现在可以追踪勇者了");
                        }
                    }
                    gM.setGamePhase("Trackable");
                    new HuntingPhase().startTrackablePhase();

                }else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        sidebar.updateGrowingSidebar(player);
                        new compassManager().trackPlayer(player);
                        if (!mhTeam.getMhTeam(player).equals("SPE")) {
                            if (mhClassManager.getPlayerClass(player).equals("Miner")){
                                pM.addEffect(player, PotionEffectType.FAST_DIGGING, 100000000, 1, false);
                            }else {
                                pM.addEffect(player, PotionEffectType.FAST_DIGGING, 100000000, 0, false);
                            }
                        }
                    }
                    if (growingTimer <= 5) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(ChatColor.YELLOW + "追踪器将在" + ChatColor.RED + growingTimer + ChatColor.YELLOW + "秒后开启");
                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
                        }
                    }
                    growingTimer--;
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class),20,20);
    }
    public int getGrowingTimeLeft(){
        return growingTimer;
    }
    public GrowingPhase(){

    }
}
