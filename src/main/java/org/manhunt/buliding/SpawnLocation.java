package org.manhunt.buliding;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.manhunt.Main;

import java.util.List;

public class SpawnLocation {

    public static double[] getHunterSpawnLocation(){

        double[] HunterSpawnLocation = new double[]{0,0,0};
        List<Integer> a = Main.getPlugin(Main.class).getConfig().getIntegerList("HunterSpawn");
        for (int i = 0;i <= a.size() - 1; i++){
            HunterSpawnLocation[i] = a.get(i);
        }
        return HunterSpawnLocation;
    }
    public static double[] getHunterSpawnTheEndLocation(){

        double[] HunterSpawnTheEndLocation = new double[]{0,0,0};
        List<Integer> a = Main.getPlugin(Main.class).getConfig().getIntegerList("HunterSpawnTheEnd");
        for (int i = 0;i <= a.size() - 1; i++){
            HunterSpawnTheEndLocation[i] = a.get(i);
        }
        return HunterSpawnTheEndLocation;
    }
    public static double[] getRunnerSpawnTheEndLocation(){

        double[] RunnerSpawnTheEndLocation = new double[]{0,0,0};
        List<Integer> a = Main.getPlugin(Main.class).getConfig().getIntegerList("RunnerSpawnTheEnd");
        for (int i = 0;i <= a.size() - 1; i++){
            RunnerSpawnTheEndLocation[i] = a.get(i);
        }
        return RunnerSpawnTheEndLocation;
    }
    public static Location ArraytoLocation(double[] a){
        return new Location(Bukkit.getWorld("world"),a[0],a[1],a[2]);
    }
}
