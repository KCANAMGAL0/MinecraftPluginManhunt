package org.manhunt.player.compass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.manhunt.Main;
import org.manhunt.game.gameManager;

import java.util.HashMap;
import java.util.List;

public class clickOnMenu implements Listener {
    public static HashMap<Player,Integer> Timer;
    public static HashMap<Player,BukkitRunnable> Runnable;
    static{
        Timer = new HashMap<>();
        Runnable = new HashMap<>();
    }
    @EventHandler
    public void playerClickOnMenu(InventoryClickEvent e) {
        gameManager gM = new gameManager();
        Player player = (Player) e.getWhoClicked();
        final String compassMenuHunterPage1 = ChatColor.RED + "追踪器#1";
        final String compassMenuHunterPage2 = ChatColor.RED + "追踪器#2";
        final String compassMenuRunnerPage1 = ChatColor.GREEN + "追踪器";
        if (e.getClickedInventory().getTitle().equalsIgnoreCase(compassMenuHunterPage1)){
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "追踪队友")){
                player.playSound(player.getLocation(), Sound.NOTE_PLING,1.0F,1.0F);
                new menu().createMenuHunterPage2(player);
            }else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "返回出生点")){
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.NOTE_PLING,1.0F,1.0F);
                Location location0 = player.getLocation();
                List<Integer> a = Main.getPlugin(Main.class).getConfig().getIntegerList("HunterSpawn");
                Timer.put(player,10);
                if (Runnable.containsKey(player)) {
                    Runnable.get(player).cancel();
                }
                BukkitRunnable b = (BukkitRunnable) new BukkitRunnable(){
                    @Override
                    public void run() {
                        Runnable.put(player,this);
                        if (player.getLocation().distance(location0) > 1.5){
                            player.sendMessage(ChatColor.RED + "你取消了传送");
                            this.cancel();
                            return;
                        }
                        if (Timer.get(player) > 0){
                            player.playSound(player.getLocation(),Sound.CLICK,1.0F,1.0F);
                            player.sendMessage(ChatColor.GRAY + "你将在" + Timer.get(player) + "秒后返回出生点");
                            Timer.put(player,Timer.get(player) - 1);
                        }else if (Timer.get(player) == 0){
                            player.teleport(new Location(Bukkit.getWorld("world"),a.get(0),a.get(1),a.get(2)));

                            this.cancel();

                        }
                    }
                }.runTaskTimer(Main.getPlugin(Main.class),1L,20L);

            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(p.getDisplayName())) {
                        e.setCancelled(true);
                        if (gM.getGamePhase().equals("Trackable") || gM.getGamePhase().equals("End_open")) {
                            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                            player.sendMessage(ChatColor.GRAY + "你正在追踪 " + ChatColor.GREEN + p.getDisplayName());
                            new compassManager().setPlayerTrack(player, p);
                            new compassManager().setEndTowerTracked(player,"N/A");
                            player.setCompassTarget(p.getLocation());
                        } else {
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                            player.sendMessage(ChatColor.RED + "你现在还不能追踪勇者!");
                            new compassManager().setEndTowerTracked(player,"N/A");
                        }
                        player.closeInventory();
                    }
                }
            }
        }else if (e.getClickedInventory().getTitle().equalsIgnoreCase(compassMenuHunterPage2)){
            e.setCancelled(true);
            final String returnToPage1 = ChatColor.GREEN + "返回";
            final String endTower1 = ChatColor.GREEN + "末地塔#1";
            final String endTower2 = ChatColor.GREEN + "末地塔#2";
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(returnToPage1)){
                player.playSound(player.getLocation(), Sound.NOTE_PLING,1.0F,1.0F);
                new menu().createMenuHunterPage1(player);
            }else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(endTower1)){
                player.playSound(player.getLocation(), Sound.NOTE_PLING,1.0F,1.0F);
                player.sendMessage(ChatColor.GRAY + "你正在追踪 " + ChatColor.RED + "末地塔#1");
                new compassManager().setEndTowerTracked(player,"1");
                new compassManager().setPlayerTrack(player,player);
                new compassManager().trackPlayer(player);
                player.closeInventory();
            }else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(endTower2)){
                player.playSound(player.getLocation(), Sound.NOTE_PLING,1.0F,1.0F);
                player.sendMessage(ChatColor.GRAY + "你正在追踪 " + ChatColor.RED + "末地塔#2");
                new compassManager().setEndTowerTracked(player,"2");
                new compassManager().setPlayerTrack(player,player);
                new compassManager().trackPlayer(player);
                player.closeInventory();
            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(p.getDisplayName())) {
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                        player.sendMessage(ChatColor.GRAY + "你正在追踪 " + ChatColor.RED + p.getDisplayName());
                        new compassManager().setPlayerTrack(player, p);
                        new compassManager().setEndTowerTracked(player,"N/A");
                        player.setCompassTarget(p.getLocation());
                        player.closeInventory();
                    }
                }
            }
        }else if (e.getClickedInventory().getTitle().equalsIgnoreCase(compassMenuRunnerPage1)) {
            e.setCancelled(true);
            final String endTower1 = ChatColor.GREEN + "末地塔#1";
            final String endTower2 = ChatColor.GREEN + "末地塔#2";
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(endTower1)){
                player.playSound(player.getLocation(), Sound.NOTE_PLING,1.0F,1.0F);
                player.sendMessage(ChatColor.GRAY + "你正在追踪 " + ChatColor.RED + "末地塔#1");
                new compassManager().setEndTowerTracked(player,"1");
                new compassManager().setPlayerTrack(player,player);
                new compassManager().trackPlayer(player);
                player.closeInventory();
            }else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(endTower2)) {
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
                player.sendMessage(ChatColor.GRAY + "你正在追踪 " + ChatColor.RED + "末地塔#2");
                new compassManager().setEndTowerTracked(player, "2");
                new compassManager().setPlayerTrack(player, player);
                new compassManager().trackPlayer(player);
                player.closeInventory();
            }else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(p.getDisplayName())) {
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                        player.sendMessage(ChatColor.GRAY + "你正在追踪 " + ChatColor.GREEN + p.getDisplayName());
                        new compassManager().setPlayerTrack(player, p);
                        new compassManager().setEndTowerTracked(player, "N/A");
                        player.setCompassTarget(p.getLocation());
                        player.closeInventory();
                    }
                }
            }
        }
    }
}
