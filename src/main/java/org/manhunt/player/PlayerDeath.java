package org.manhunt.player;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.manhunt.buliding.SpawnLocation;
import org.manhunt.game.end_Open;
import org.manhunt.game.gameManager;
import org.manhunt.Main;
import org.manhunt.player.team.MhTeam;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.manhunt.player.team.RunnerMaxHealth;
import org.manhunt.player.team.attackOnPlayer;

import java.util.ArrayList;
import java.util.List;

public class PlayerDeath implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        playerManager pM = new playerManager();
        attackOnPlayer a = new attackOnPlayer();

        Player player = e.getEntity();
        player.setVelocity(new Vector());
        if (!a.getPlayerLastDamager(player).equals(player)) {
            a.getPlayerLastDamager(player).playSound(a.getPlayerLastDamager(player).getLocation(), Sound.ORB_PICKUP,1.0F,1.0F);
            e.setDeathMessage(player.getDisplayName() + ChatColor.GRAY + " 被 " + ChatColor.RESET + a.getPlayerLastDamager(player).getDisplayName() + ChatColor.GRAY + " 杀死了");
        }else {
            e.setDeathMessage(player.getDisplayName() + ChatColor.GRAY + " 死了");
            a.resetPlayerLastDamager(player);
        }
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), () -> {
            player.spigot().respawn();
            player.teleport(new Location(Bukkit.getWorld("world"),new SpawnLocation().getHunterSpawnLocation()[0],new SpawnLocation().getHunterSpawnLocation()[1],new SpawnLocation().getHunterSpawnLocation()[2]), PlayerTeleportEvent.TeleportCause.COMMAND);
            pM.addEffect(player,PotionEffectType.NIGHT_VISION,10000000,0,false);
            player.setSaturation(20);
            player.setFoodLevel(19);
            if (end_Open.isEndOpened) new playerManager().addEffect(player, PotionEffectType.SPEED, 600, 1, false);
        },1L);

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if ((a.getPlayerLastDamager(player1).equals(player)) && (player1.equals(player))) {
                a.resetPlayerLastDamager(player1);
            }
        }
        MhTeam mhTeam = new MhTeam();

        if (mhTeam.getMhTeam(player).equals("RUNNER")) {

            mhTeam.setPlayerTeam(player, "SPE");
            player.setGameMode(GameMode.SPECTATOR);
            for (Player player1: Bukkit.getOnlinePlayers()) {
                if (mhTeam.getMhTeam(player1).equals("RUNNER")) {
                    pM.addEffect(player1,PotionEffectType.INCREASE_DAMAGE,200,0,false);
                    pM.addEffect(player1,PotionEffectType.DAMAGE_RESISTANCE,200,0,false);
                    player.setSaturation(20);
                    player.setFoodLevel(19);
                }
            }
            player.getWorld().strikeLightningEffect(player.getLocation());
            if (mhTeam.getRunnerCount() == 0) {

                new gameManager().gameOver(true);
            }else {
                new RunnerMaxHealth().setRunnerMaxHealth();
                for (Player player1 :Bukkit.getOnlinePlayers()) {
                    player1.playSound(player1.getLocation(),Sound.ARROW_HIT,1.0F,1.0F);
                    player1.sendMessage(ChatColor.GRAY + "由于勇者死亡，所有勇者最大生命值提升至" + ChatColor.RED + new RunnerMaxHealth().getRunnerMaxHealth() + ChatColor.GRAY +"点");
                    player1.sendMessage(ChatColor.GRAY + "由于勇者死亡，所有勇者最大生命值提升至" + ChatColor.RED + new RunnerMaxHealth().getRunnerMaxHealth() + ChatColor.GRAY +"点");
                    player1.sendMessage(ChatColor.GRAY + "由于勇者死亡，所有勇者最大生命值提升至" + ChatColor.RED + new RunnerMaxHealth().getRunnerMaxHealth() + ChatColor.GRAY +"点");
                }
            }
        } else if (mhTeam.getMhTeam(player).equals("HUNTER")) {
            e.setKeepInventory(true);
            new playerManager().addEffect(player, PotionEffectType.FAST_DIGGING,999999,0,false);
            if (player.getWorld().getName().equals("world")) {
                ExperienceOrb experienceOrb = (ExperienceOrb) player.getWorld().spawnEntity(player.getLocation(), EntityType.EXPERIENCE_ORB);
                experienceOrb.setExperience(player.getTotalExperience() / 2);
                e.setNewLevel(player.getLevel() / 2);
                List<Material> dropItemList = getDropItemList();
                Inventory inventory = player.getInventory();
                for (int i = 0; i <= 35; i++) {
                    if (!isEmpty(inventory, i)) {
                        dropItemNotEmpty(i, player.getInventory(), player);
                    }
                }
            }
        }
    }
    public void dropItemNotEmpty(int i,Inventory inventory,Player player){
        int a = 0,b = 0,c = 0;
        ItemStack itemStack = inventory.getItem(i);
        Material itemType = itemStack.getType();
        List<Material> dropItemList = getDropItemList();
        ItemStack AIR = new ItemStack(Material.AIR);
        for (Material dropItem : dropItemList) {
            if (itemType.equals(dropItem)) {
                c = itemStack.getAmount();
                if (c == 1) {
                    int isDrop = (int) (Math.random() * 100 + 1);
                    if (isDrop > 50) {
                        ItemStack itemStack1 = itemStack.clone();
                        itemStack1.setAmount(1);
                        player.getWorld().dropItem(player.getLocation(),itemStack1);
                        player.getInventory().setItem(i, AIR);
                    }
                } else if (c >= 2) {
                    a = c / 2;
                    b = c - a;
                    ItemStack itemStack1 = itemStack.clone();
                    itemStack1.setAmount(a);
                    player.getWorld().dropItem(player.getLocation(),itemStack1);
                    player.getInventory().getItem(i).setAmount(b);
                }
            }
        }
    }
    public boolean isEmpty(Inventory inventory,int i){
        boolean b = false;
        try {
            ItemStack itemStack = inventory.getItem(i);
            Material itemType = itemStack.getType();
        } catch (NullPointerException exception){
            b = true;
        } finally {
            return b;
        }
    }

    public void setItemDrop(World world, Location dropLoc, Material dropItem, int count){
        int count0 = 0;
        if (count %2 == 0){
            count0 = count;
        }else {
            count0 = count-1;
        }
        ItemStack itemDrop = new ItemStack(dropItem,count0 / 2);

        world.dropItem(dropLoc,itemDrop);
    }
    public List<Material> getDropItemList(){
        List<Material> dropItemList = new ArrayList<>();
        dropItemList.add(Material.STONE);
        dropItemList.add(Material.GRASS);
        dropItemList.add(Material.GRAVEL);
        dropItemList.add(Material.SAND);
        dropItemList.add(Material.APPLE);
        dropItemList.add(Material.BREAD);
        dropItemList.add(Material.GOLDEN_APPLE);
        dropItemList.add(Material.LOG);
        dropItemList.add(Material.DIRT);
        dropItemList.add(Material.WATER_BUCKET);
        dropItemList.add(Material.COBBLESTONE);
        dropItemList.add(Material.IRON_INGOT);
        dropItemList.add(Material.GOLD_INGOT);
        dropItemList.add(Material.DIAMOND);
        dropItemList.add(Material.REDSTONE);
        dropItemList.add(Material.WOOL);
        dropItemList.add(Material.APPLE);
        dropItemList.add(Material.SNOW_BALL);
        dropItemList.add(Material.RAW_BEEF);
        dropItemList.add(Material.GRILLED_PORK);
        dropItemList.add(Material.BREAD);
        dropItemList.add(Material.WATER_BUCKET);
        dropItemList.add(Material.LAVA_BUCKET);
        dropItemList.add(Material.MUTTON);
        dropItemList.add(Material.COOKED_MUTTON);
        dropItemList.add(Material.BOOK);
        dropItemList.add(Material.LEAVES);
        dropItemList.add(Material.BUCKET);
        dropItemList.add(Material.COOKED_BEEF);
        dropItemList.add(Material.GLOWSTONE_DUST);
        dropItemList.add(Material.INK_SACK);
        dropItemList.add(Material.BLAZE_POWDER);
        dropItemList.add(Material.BLAZE_ROD);
        dropItemList.add(Material.SUGAR_CANE);
        dropItemList.add(Material.IRON_BLOCK);
        dropItemList.add(Material.MAGMA_CREAM);
        return dropItemList;
    }
}
