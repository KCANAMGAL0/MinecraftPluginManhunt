package org.manhunt.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.manhunt.player.team.MhTeam;
import org.manhunt.sidebar.Sidebar;

import java.util.*;

public class playerManager {
    public void Heal(Player player, double amount){
        double MaxHeath = player.getMaxHealth();
        double health = player.getHealth();
        if (health + amount >= MaxHeath){
            player.setHealth(MaxHeath);
        }else{
            player.setHealth(health + amount);
        }
    }

    public void TrueDamage(Player player,double amount){
        if (player.getHealth() <= amount){
            player.damage(32767);
        }else {
            player.setHealth(player.getHealth() - amount);
        }
    }
    public void addEffect(Player player,PotionEffectType potionEffectType,int newDuration,int newAmplifier,boolean replace){
        boolean hasSameEffect = false;
        for (PotionEffect potionEffect : player.getActivePotionEffects()){
            if (potionEffect.getType().equals(potionEffectType)){
                if (potionEffect.getType().equals(PotionEffectType.ABSORPTION)) {
                    if (((CraftPlayer) player).getHandle().getAbsorptionHearts() == 0) {
                        replace = true;
                    }
                }
                if (potionEffect.getAmplifier() < newAmplifier) {
                    player.removePotionEffect(potionEffect.getType());
                    player.addPotionEffect(new PotionEffect(potionEffectType, newDuration, newAmplifier));
                }else if (replace){
                    player.removePotionEffect(potionEffect.getType());
                    player.addPotionEffect(new PotionEffect(potionEffectType, newDuration, newAmplifier));
                }else if (potionEffect.getAmplifier() == newAmplifier){
                    if (potionEffect.getDuration() <= newDuration){
                        player.removePotionEffect(potionEffect.getType());
                        player.addPotionEffect(new PotionEffect(potionEffectType, newDuration, newAmplifier));
                    }
                }
                hasSameEffect = true;
            }
        }
        if (!hasSameEffect){
            player.addPotionEffect(new PotionEffect(potionEffectType,newDuration,newAmplifier));
        }
    }
    public boolean hasEffect(Player player,PotionEffectType potionEffectType,int Amplifier) {
        boolean hasEffect = false;
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            if (potionEffect.getType().equals(potionEffectType) && potionEffect.getAmplifier() == Amplifier) {
                hasEffect = true;
            }
        }
        return hasEffect;
    }
    public boolean hasEffect(Player player,PotionEffectType potionEffectType) {
        boolean hasEffect = false;
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            if (potionEffect.getType().equals(potionEffectType)) {
                hasEffect = true;
            }
        }
        return hasEffect;
    }
    public void removeEffect(Player player,PotionEffectType potionEffectType){
        if (hasEffect(player,potionEffectType)){
            player.removePotionEffect(potionEffectType);
        }
    }
    public void showPlayerSidebar(){
        Sidebar sidebar = new Sidebar();
        for (Player player : Bukkit.getOnlinePlayers()){
            sidebar.buildPrepareSideBar(player);
        }
    }
    public void setPlayerDisplayName(Player player){
        if (new MhTeam().getMhTeam(player).equals("RUNNER")) player.setDisplayName(ChatColor.GREEN + "(勇者)" + player.getName());
        if (new MhTeam().getMhTeam(player).equals("HUNTER")) player.setDisplayName(ChatColor.GREEN + "(勇者)" + player.getName());
    }
    public void SoundForAllPlayers(Sound sound,float v,float v1) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(),sound,v,v1);
        }
    }
    public playerManager(){
    }
}
