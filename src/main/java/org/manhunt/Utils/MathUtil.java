package org.manhunt.Utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.manhunt.player.team.MhTeam;

import java.util.*;

public class MathUtil{
    public static Location getLocationFromPos(double[] Pos, World world){
        return new Location(world,Pos[0],Pos[1],Pos[2]);
    }

    public static List<Location> getCircle(double Radius, Location MidLocation, double p){
        final double pi = Math.PI;
        Location location;
        double height = MidLocation.getY(),t0 = p*Radius;
        int t = 0;
        List<Location> locations = new ArrayList<>();
        for (double x = 0; x <= (2*pi); x += (pi/t0)){
            t++;
            location = new Location(MidLocation.getWorld(),MidLocation.getX() + Radius * Math.cos(x),height,MidLocation.getZ() + Radius * Math.sin(x));
            locations.add(location);
        }
        return locations;
    }

    public static double getPlaneDistance(Location location1,Location location2){
        if (location1.getWorld().equals(location2.getWorld())){
            return new Location(location1.getWorld(),location1.getX(),0,location1.getZ()).distance(new Location(location2.getWorld(),location2.getX(),0,location2.getZ()));
        }else {
            return 0;
        }
    }
    public static double Keep1DecimalPlace(double a) {
        int b = (int) (a*10);
        return (double) b/10;
    }

    public static double getCosine(double a,double b,double c){
        return (a*a + b*b - c*c) / (2*a*b);
    }

    public static List<Player> getNearByPlayer(Location location,double r){
        List<Player> list = new ArrayList<>();
        for (Player player : location.getWorld().getPlayers()){
            if (player.getEyeLocation().distance(location) <= r) list.add(player);
        }
        return list;
    }

    public static Player getNearestPlayer(Location location){
        World world = location.getWorld();
        HashMap<Double,Player> doublePlayerHashMap = new HashMap<>();
        world.getPlayers().forEach(player -> doublePlayerHashMap.put(player.getEyeLocation().distance(location),player));
        List<Double> doubles = new ArrayList<>(doublePlayerHashMap.keySet());
        return doublePlayerHashMap.get(getMaxValue(doubles));
    }
    public static Player getNearestRunner(Location location){
        World world = location.getWorld();
        HashMap<Double,Player> doublePlayerHashMap = new HashMap<>();
        for (Player player : world.getPlayers())
            if (new MhTeam().isRunner(player)) doublePlayerHashMap.put(player.getEyeLocation().distance(location), player);
        List<Double> doubles = new ArrayList<>(doublePlayerHashMap.keySet());
        return doublePlayerHashMap.get(getMaxValue(doubles));
    }
    public static Player getNearestRunner(Location location,double r){
        World world = location.getWorld();
        HashMap<Double,Player> doublePlayerHashMap = new HashMap<>();
        for (Player player : world.getPlayers())
            if (new MhTeam().isRunner(player) && player.getEyeLocation().distance(location) <= r) doublePlayerHashMap.put(player.getEyeLocation().distance(location), player);
        List<Double> doubles = new ArrayList<>(doublePlayerHashMap.keySet());
        return doublePlayerHashMap.get(getMinValue(doubles));
    }
    public static Player getNearestPlayer(Location location,double r){
        World world = location.getWorld();
        HashMap<Double,Player> doublePlayerHashMap = new HashMap<>();
        for (Player player : world.getPlayers())
            if (player.getLocation().distance(location) <= r) doublePlayerHashMap.put(player.getLocation().distance(location), player);
        List<Double> doubles = new ArrayList<>(doublePlayerHashMap.keySet());
        return doublePlayerHashMap.get(getMinValue(doubles));
    }
    public static Player getNearestHunter(Location location,double r){
        World world = location.getWorld();
        HashMap<Double,Player> doublePlayerHashMap = new HashMap<>();
        for (Player player : world.getPlayers())
            if (player.getLocation().distance(location) <= r && new MhTeam().isHunter(player)) doublePlayerHashMap.put(player.getLocation().distance(location), player);
        List<Double> doubles = new ArrayList<>(doublePlayerHashMap.keySet());
        return doublePlayerHashMap.get(getMinValue(doubles));
    }
    public static Player getHighestHPHunter(Location location,double r){
        World world = location.getWorld();
        HashMap<Double,Player> doublePlayerHashMap = new HashMap<>();
        for (Player player : world.getPlayers())
            if (player.getLocation().distance(location) <= r && new MhTeam().isHunter(player)) doublePlayerHashMap.put(player.getHealth(), player);
        List<Double> doubles = new ArrayList<>(doublePlayerHashMap.keySet());
        if (!doubles.isEmpty())
            return doublePlayerHashMap.get(getMaxValue(doubles));
        return null;
    }

    public static double getMaxValue(List<Double> doubles){
        if (!doubles.isEmpty()) {
            double result = doubles.get(0);
            for (Double aDouble : doubles) {
                if (aDouble >= result) result = aDouble;
            }
            return result;
        }
        return 0;
    }
    public static double getMinValue(List<Double> doubles){
        if (!doubles.isEmpty()) {
            double result = doubles.get(0);
            for (Double aDouble : doubles) {
                if (aDouble <= result) {
                    result = aDouble;
                }
            }
            return result;
        }
        return 0;
    }
    public static Player getRandomPlayer(List<Player> players){
        return players.get(new Random().nextInt(players.size()));
    }
    public static Player getRandomHunter(List<Player> players,Location location,double r){
        players.removeIf(player -> player.getWorld() != location.getWorld() || player.getLocation().distance(location) > r);
        return players.get(new Random().nextInt(players.size()));
    }
}
