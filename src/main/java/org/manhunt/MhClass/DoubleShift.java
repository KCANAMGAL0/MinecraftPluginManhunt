package org.manhunt.MhClass;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.manhunt.Utils.MathUtil;
import org.manhunt.entity.CustomEntityTypes;
import org.manhunt.entity.CustomZombie;
import org.manhunt.player.team.MhTeam;

import java.util.HashMap;

public class DoubleShift implements Listener {
    public static HashMap<Player, Long> DoubleShift;
    public static HashMap<Player,Integer> ToggleSneak;
    static {
        DoubleShift = new HashMap<>();
        ToggleSneak = new HashMap<>();
    }
    @EventHandler
    public void PressShift(PlayerToggleSneakEvent e){
        Player player = e.getPlayer();
        if (player.isSneaking()){
            if (ToggleSneak.containsKey(player)) {
                if (DoubleShift.containsKey(player) && DoubleShift.get(player) > System.currentTimeMillis()) {
                    if (ToggleSneak.get(player) == 0) {
                        ToggleSneak.put(player, 1);
                        DoubleShift.put(player, System.currentTimeMillis() + 500L);
                        //player.sendMessage("a0");
                    } else if (ToggleSneak.get(player) == 1) {
                        ToggleSneak.put(player, 0);
                        activateSkills(player);
                        //player.playSound(player.getLocation(),Sound.ORB_PICKUP,1.0F,1.0F);
                        //player.sendMessage("a1");
                        DoubleShift.put(player, System.currentTimeMillis() + 500L);
                    }
                } else {
                    if (ToggleSneak.get(player) == 1) {
                        DoubleShift.put(player, System.currentTimeMillis() + 500L);
                        //player.sendMessage("b1");

                    } else {
                        ToggleSneak.put(player, 1);
                        DoubleShift.put(player, System.currentTimeMillis() + 500L);
                        //player.sendMessage("b0");
                    }

                }
            }else {
                ToggleSneak.put(player,1);
            }
        }
    }
    public void resetShift(Player player){
        ToggleSneak.put(player,0);
    }
    public void activateSkills(Player player){
        MhClassManager mhClassManager = new MhClassManager();
        /*
        if ((new gameManager().getGamePhase().equals("waiting") || new gameManager().getGamePhase().equals("prepare"))) {
            return;
        }else {
         */
            if (mhClassManager.getPlayerClass(player).equals(MhClasses.Esc.getName())) {
                new EvilScorcher().getLavaBucket(player);
            } else if (mhClassManager.getPlayerClass(player).equals(MhClasses.Vam.getName())) {
                new Vampire().BloodingSkill0(player);
            } else if (mhClassManager.getPlayerClass(player).equals(MhClasses.Pal.getName())) {
                new Paladin().Skill1(player);
            } else if (mhClassManager.getPlayerClass(player).equals(MhClasses.Sac.getName())){
                new Sacrifice().SummonEnderCrystal(player);
            } else if (mhClassManager.getPlayerClass(player).equals(MhClasses.Irg.getName())){
                new IronGolem().IronPunch(player);
            } else if (mhClassManager.getPlayerClass(player).equals(MhClasses.Ass.getName())){
                new Assassin().Invisibility(player);
            } else if (mhClassManager.getPlayerClass(player).equals(MhClasses.Gua.getName())){
                new Guardian().GuardRay(player);
            } else if (mhClassManager.getPlayerClass(player).equals(MhClasses.Rob.getName())) {
                new Robber().Arrow1(player);
            } else if (mhClassManager.getPlayerClass(player).equals(MhClasses.MM.getName())){
                new MachineMaster().Cannon(player);
            } else {
                /*
                Zombie = CustomEntityTypes.spawnEntity(new CustomZombie(player.getWorld()), player.getLocation());
                zombie.setBaby(false);
                EntityTargetEvent event = new EntityTargetEvent(zombie,MathUtil.getNearestRunner(player.getEyeLocation(),16), EntityTargetEvent.TargetReason.CUSTOM);
                Bukkit.getPluginManager().callEvent(event);

                 */
            }
    }

    @EventHandler
    public void b(PlayerInteractEvent e){
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
            if (e.getPlayer().getItemInHand().getType() == Material.BOW) {
                new Robber().a(e.getPlayer());
                new Guardian().GuardRay(e.getPlayer());
            }
        }
    }

}
