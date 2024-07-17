package org.manhunt.game;

import org.bukkit.Bukkit;
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


public class HuntingPhase {  //Trackable
    public static int HuntingTimer;
    public void startTrackablePhase(){
        HuntingTimer = 1440; // 1440ticks = 720secs = 12min
        compassManager cM = new compassManager();
        MhTeam mhTeam = new MhTeam();
        cM.resetAllPlayerTrack();
        MhClassManager mhClassManager = new MhClassManager();
        playerManager pM = new playerManager();
        Sidebar sidebar = new Sidebar();
        for (Player player : Bukkit.getOnlinePlayers()){
            sidebar.buildHuntingSidebar(player);
        }
        new gameManager().setGamePhase("Trackable");
        new BukkitRunnable(){
            @Override
            public void run () {
                if (HuntingTimer == 0){
                    this.cancel();
                    new end_Open().startEnd_OpenPhase();
                    new playerManager().SoundForAllPlayers(Sound.ENDERMAN_TELEPORT,1.0F,1.0F);

                }else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        cM.trackPlayer(player);

                        if (!mhTeam.getMhTeam(player).equals("SPE")) {
                            if (mhClassManager.getPlayerClass(player).equals("Miner")){
                                pM.addEffect(player, PotionEffectType.FAST_DIGGING, 100000000, 1, false);
                            }else {
                                pM.addEffect(player, PotionEffectType.FAST_DIGGING, 100000000, 0, false);
                            }
                        }
                        if (HuntingTimer % 2 == 0) {
                            sidebar.updateHuntingSidebar(player);
                        }
                    }
                    HuntingTimer--;
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class),20,10);
    }
    public int getHuntingTimeLeft() {
        return HuntingTimer / 2;
    }
}
