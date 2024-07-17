package org.manhunt.player.compass;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.manhunt.buliding.endTower;
import org.manhunt.player.actionbar.ActionbarManager;

import javax.sound.midi.Track;
import java.util.HashMap;

public class compassManager {
    public static HashMap<Player,Player> PlayerTrackInfo;
    static {
        PlayerTrackInfo = new HashMap<>();
    }

    public static HashMap<Player,String> TrackEndTower;
    static {
        TrackEndTower = new HashMap<>();
    }
    public void resetPlayerTrack(Player p){
        PlayerTrackInfo.put(p,p);
        TrackEndTower.put(p,"N/A");
    }
    public void resetAllPlayerTrack(){
        for (Player p : Bukkit.getOnlinePlayers()) {
            resetPlayerTrack(p);
        }
    }
    public Player getPlayerTrack(Player player){
        return PlayerTrackInfo.get(player);
    }
    public void setPlayerTrack(Player player,Player tracker){
        PlayerTrackInfo.put(player,tracker);
    }

    public void setEndTowerTracked(Player player,String s){
        TrackEndTower.put(player,s);
    }
    public String getEndTowerTracked(Player player){
        return TrackEndTower.get(player);
    }
    public void trackPlayer(Player player){
        endTower e = new endTower();
        Player isTracked = PlayerTrackInfo.get(player);
        ActionbarManager a = new ActionbarManager();
        final String g = ChatColor.GRAY.toString();
        final String r = ChatColor.RED.toString();
        if (!isTracked.equals(player) && getEndTowerTracked(player).equals("N/A")) {
            if (player.getWorld().equals(isTracked.getWorld())) {
                player.setCompassTarget(isTracked.getLocation());
            }

            if ((player.getItemInHand().getType().equals(Material.COMPASS))) {
                if (player.getWorld().equals(isTracked.getWorld())) {
                    a.sendActionbar(player, g + isTracked.getDisplayName() + "   " + g + "距离: " + r + getDistance(player.getLocation(), isTracked.getLocation()) + "m", g + "目标: ");
                } else {
                    if (isTracked.getWorld().getName().equals("world")) {
                        a.sendActionbar(player, g + isTracked.getDisplayName() + "   " + g + "目标维度: " + r + "主世界", g + "目标: ");
                    } else if (isTracked.getWorld().getName().equals("world_the_end")) {
                        a.sendActionbar(player, g + isTracked.getDisplayName() + "   " + g + "目标维度: " + r + "末地", g + "目标: ");
                    } else if (isTracked.getWorld().getName().equals("world_nether")){
                        a.sendActionbar(player, g + isTracked.getDisplayName() + "   " + g + "目标维度: " + r + "地狱", g + "目标: ");
                    }
                }
            }
        }else if (!getEndTowerTracked(player).equals("N/A") && player.getWorld().getName().equals("world")) {
            if (getEndTowerTracked(player).equals("1")){
                player.setCompassTarget(e.getEndTowerLocation(e.getEndTower1Center()));
                if (player.getItemInHand().getType().equals(Material.COMPASS)){
                    a.sendActionbar(player, g + r + "末地塔#1" + "   " + g + "距离: " + r + getDistance(player.getLocation(),e.getEndTowerLocation(e.getEndTower1Center())) + "m", g + "目标: ");
                }
            }else if (getEndTowerTracked(player).equals("2")){
                player.setCompassTarget(e.getEndTowerLocation(e.getEndTower2Center()));
                if (player.getItemInHand().getType().equals(Material.COMPASS)){
                    a.sendActionbar(player, g + r + "末地塔#2" + "   " + g + "距离: " + r + getDistance(player.getLocation(),e.getEndTowerLocation(e.getEndTower2Center())) + "m", g + "目标: ");
                }
            }
        }
    }
    public double getDistance(Location loc1,Location loc2) {
        double[] player1Pos = new double[]{loc1.getX(),loc1.getY(),loc1.getZ()};
        double[] player2Pos = new double[]{loc2.getX(),loc2.getY(),loc2.getZ()};
        double[] a = new double[]{0,0,0};
        for (int i = 0;i <= 2; i++){
            a[i] = (player1Pos[i] - player2Pos[i])*(player1Pos[i] - player2Pos[i]);
        }
        int b = (int) ((Math.sqrt(a[0] + a[1] + a[2])) * 10);
        return (double) b/10;
    }


    public compassManager(){
    }
}
