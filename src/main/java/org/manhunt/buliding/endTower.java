package org.manhunt.buliding;

import javafx.print.PageLayout;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.manhunt.Main;

import java.util.List;
import java.util.ListResourceBundle;

public class endTower {
    static Location location;
    static Plugin plugin = Main.getPlugin(Main.class);

    public static Location getLocation() {
        return location;
    }

    public static double[] getEndTower1Center(){
        double[] endTower1Center = new double[]{0,0,0};
        List<Integer> endTower1 = Main.getPlugin(Main.class).getConfig().getIntegerList("endTower1Center");
        for (int i = 0;i <= endTower1.size() - 1; i++){
            endTower1Center[i] = endTower1.get(i);
        }
        return endTower1Center;
    }

    public static double[] getEndTower2Center(){
        double[] endTower2Center = new double[]{0,0,0};
        List<Integer> endTower2 = Main.getPlugin(Main.class).getConfig().getIntegerList("endTower2Center");
        for (int i = 0;i <= endTower2.size() - 1; i++){
            endTower2Center[i] = endTower2.get(i);
        }
        return endTower2Center;
    }
    public static Location getEndTower(int a){
        World world = Bukkit.getWorld("world");
        if (a == 1){
            return new Location(world,plugin.getConfig().getIntegerList("endTower1Center").get(0),plugin.getConfig().getIntegerList("endTower1Center").get(1),plugin.getConfig().getIntegerList("endTower1Center").get(2));
        }else if (a== 2){
            return new Location(world,plugin.getConfig().getIntegerList("endTower2Center").get(0),plugin.getConfig().getIntegerList("endTower2Center").get(1),plugin.getConfig().getIntegerList("endTower2Center").get(2));
        }
        return null;
    }

    public static Location getEndTowerLocation(double[] a){
        Location location = new Location(Bukkit.getWorld("world"),a[0],a[1],a[2]);
        return location;
    }
}
