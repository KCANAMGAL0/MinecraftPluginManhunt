package org.manhunt.game;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.manhunt.Main;
import org.manhunt.MhClass.MhClassManager;
import org.manhunt.buliding.SpawnLocation;
import org.manhunt.entity.enderDragon;
import org.manhunt.player.compass.compassManager;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;
import org.manhunt.sidebar.Sidebar;

import java.util.ArrayList;
import java.util.List;

public class end_Open implements Listener {
    public static int GameTimer;
    public static Boolean isEndOpened;
    public void startEnd_OpenPhase(){
        GameTimer = 5400; // 45min
        isEndOpened = false;
        compassManager cM = new compassManager();
        MhTeam mhTeam = new MhTeam();
        MhClassManager mhClassManager = new MhClassManager();
        Sidebar sidebar = new Sidebar();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "末地传送门开启",ChatColor.GRAY + "勇者现在可以前往末地了");
            sidebar.buildEnd_OpenSidebar(player);
        }
        playerManager pM = new playerManager();
        new gameManager().setGamePhase("End_open");

            new BukkitRunnable(){
                @Override
                public void run () {
                    if (GameTimer == 0){
                        this.cancel();
                        new gameManager().gameOver(true);
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
                            if (GameTimer % 2 == 0) {
                                sidebar.updateEnd_OpenSidebar(player);
                            }
                            if (GameTimer <= 1800) {
                                if (mhTeam.getMhTeam(player).equals("RUNNER") && player.getWorld().getName().equals("world")) {
                                    runnerInWorld(player);
                                }
                            }
                            if (GameTimer == 4200){
                                if (!new TheNether().WitherSpawned){
                                    World theNether = Bukkit.getWorld("world_nether");
                                    Location MiddleLocation = TheNether.MiddleLocation;
                                    for (Player player1 : Bukkit.getOnlinePlayers()){
                                        player1.playSound(player1.getLocation(),Sound.WITHER_SPAWN,1.0F,1.0F);
                                        player1.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "凋灵已在地狱中心生成!击败以获取补给和增益!");
                                        if (mhTeam.isHunter(player1)) {
                                            player1.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "凋灵已在地狱中心生成", ChatColor.GRAY + ChatColor.BOLD.toString() + "杀死凋灵可增强末影水晶的恢复效果");
                                        }else if (mhTeam.isRunner(player1)) player1.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "凋灵已在地狱中心生成", ChatColor.GRAY + ChatColor.BOLD.toString() + "杀死凋灵可减少末影龙的血量");
                                    }
                                    theNether.loadChunk((int) MiddleLocation.getX(), (int) MiddleLocation.getZ());
                                    new TheNether().WitherSpawned = true;
                                    Location MiddleLocation0 = new Location(theNether, MiddleLocation.getX() + 0.5, MiddleLocation.getY() + 0.5, MiddleLocation.getZ() + 0.5);
                                    Wither wither = (Wither) theNether.spawnEntity(MiddleLocation0, EntityType.WITHER);
                                    wither.setMaxHealth(wither.getHealth() * 1.5);
                                    wither.setHealth(wither.getMaxHealth());
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            if (!wither.isDead()) wither.teleport(MiddleLocation0);
                                            else this.cancel();
                                        }
                                    }.runTaskTimer(Main.getPlugin(Main.class), 0L, 1L);
                                }
                            }
                        }
                        GameTimer--;
                    }
                }
            }.runTaskTimer(Main.getPlugin(Main.class),20,10);
        }
    public int getGameTimer() {
            return GameTimer / 2;
        }
    public void runnerInWorld(Player player) {
        playerManager pM = new playerManager();
        if (GameTimer % 2 == 0){
            pM.addEffect(player, PotionEffectType.WITHER, 60, 0, false);
            pM.addEffect(player, PotionEffectType.HUNGER, 60, 9, false);
            player.sendMessage( ChatColor.RED + ChatColor.BOLD.toString() + "你正在受到凋零和饥饿效果!请尽快进入末地");
        }
    }
    private void sendHUNTERTips(Player player){
        List<String> tips = new ArrayList<>();
        tips.add(ChatColor.WHITE + ChatColor.BOLD.toString() +"============================================");
        tips.add(ChatColor.RED + ChatColor.BOLD.toString() + "                                   猎人游戏                                   ");
        tips.add("");
        tips.add(ChatColor.YELLOW +ChatColor.BOLD.toString() + "你是否在担心无法消灭躲在高塔上的傻逼勇者？为此，我们为末影龙");
        tips.add(ChatColor.YELLOW +ChatColor.BOLD.toString() + "增加了龙息弹技能，只要他们攻击末影龙就有可能遭到龙息弹的反击");
        tips.add("");
        tips.add(ChatColor.WHITE + ChatColor.BOLD.toString() +"============================================");
        for (String s : tips){
            player.sendMessage(s);
        }
    }

    private void sendRunnerTips(Player player){
        List<String> tips = new ArrayList<>();
        tips.add(ChatColor.WHITE + ChatColor.BOLD.toString() +"============================================");
        tips.add(ChatColor.RED + ChatColor.BOLD.toString() + "                                   猎人游戏                                   ");
        tips.add("");
        tips.add(ChatColor.YELLOW +ChatColor.BOLD.toString() + "当你还在为站在高塔上攻击末影龙并轻而易举地击退猎人们的进攻而");
        tips.add(ChatColor.YELLOW +ChatColor.BOLD.toString() + "沾沾自喜的时候，末影龙的龙息弹可能就会朝你飞来。攻击末影龙时");
        tips.add(ChatColor.YELLOW +ChatColor.BOLD.toString() + "可能遭到龙息弹的反击，当然也可能获取到宝贵的补给(箭,食物等)");
        tips.add("");
        tips.add(ChatColor.WHITE + ChatColor.BOLD.toString() +"============================================");
        for (String s : tips){
            player.sendMessage(s);
        }
    }
    @EventHandler
    public void playerTravelToTheEnd(PlayerChangedWorldEvent e){
        Player player = e.getPlayer();
        SpawnLocation s = new SpawnLocation();
        playerManager pM = new playerManager();
        MhTeam mhTeam = new MhTeam();
        final World the_end = Bukkit.getWorld("world_the_end");

        if (e.getFrom().getName().equals("world") && player.getWorld().getName().equals("world_the_end")){
            if (mhTeam.getMhTeam(player).equals("RUNNER")){
                player.teleport(new Location(the_end,s.getRunnerSpawnTheEndLocation()[0],s.getRunnerSpawnTheEndLocation()[1],s.getRunnerSpawnTheEndLocation()[2]), PlayerTeleportEvent.TeleportCause.END_PORTAL);
            }else if(mhTeam.getMhTeam(player).equals("HUNTER")){
                player.teleport(new Location(the_end,s.getHunterSpawnTheEndLocation()[0],s.getHunterSpawnTheEndLocation()[1],s.getHunterSpawnTheEndLocation()[2]), PlayerTeleportEvent.TeleportCause.END_PORTAL);
            }else {
                player.teleport(new Location(the_end,0,90,0), PlayerTeleportEvent.TeleportCause.END_PORTAL);
            }
            if (!pM.hasEffect(player,PotionEffectType.DAMAGE_RESISTANCE,0)) {
                new playerManager().addEffect(player, PotionEffectType.DAMAGE_RESISTANCE,200,4,false);
                if (mhTeam.getMhTeam(player).equals("RUNNER")){
                    pM.addEffect(player,PotionEffectType.ABSORPTION,6000,2,true);
                }
            }

            if (mhTeam.getMhTeam(player).equals("RUNNER") && !isEndOpened) {
                isEndOpened = true;
                new enderDragon().Summon(new Location(Bukkit.getWorld("world_the_end"),30,90,0));
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    player1.playSound(player1.getLocation(), Sound.ENDERMAN_TELEPORT,1.0F,1.0F);
                    if (mhTeam.getMhTeam(player1).equals("RUNNER")) {
                        player1.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "勇者已前往末地", ChatColor.GRAY + "请尽快杀死末影龙");
                        sendRunnerTips(player1);
                    } else if (mhTeam.getMhTeam(player1).equals("HUNTER")) {
                        player1.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "勇者已前往末地", ChatColor.GRAY + "请尽快前往末地");
                        sendHUNTERTips(player1);
                    } else player1.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "勇者已前往末地",null);
                }
            }
        }
    }
    @EventHandler
    public void PlayerEnterPortal(PlayerPortalEvent e){
        Player player = e.getPlayer();
        MhTeam mhTeam = new MhTeam();
        if (e.getTo().getWorld().getName().equals("world_the_end")){
            if (mhTeam.getMhTeam(player).equals("HUNTER")) {
                if (!isEndOpened) {
                    e.setCancelled(true);
                }
            }else if (mhTeam.getMhTeam(player).equals("RUNNER")){
                if (!new gameManager().getGamePhase().equals("End_open")){
                    e.setCancelled(true);
                }
            }
        }else if (e.getTo().getWorld().getName().equals("world_the_nether")) {
            e.setCancelled(true);
        }
    }
    public boolean getIsEndOpened(){
        return isEndOpened;
    }
    public void setIsEndOpened(boolean b){
        isEndOpened = b;
    }
}
