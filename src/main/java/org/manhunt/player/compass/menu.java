package org.manhunt.player.compass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.manhunt.player.team.MhTeam;

import java.util.ArrayList;
import java.util.List;

public class menu {
    public void createMenuHunterPage1(Player player) {
        MhTeam mhTeam = new MhTeam();
        List<Player> runnerList = new ArrayList<>();
        Inventory compassMenuHunterPage1 = Bukkit.createInventory(player, 27, ChatColor.RED + "追踪器#1");
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (mhTeam.getMhTeam(player1).equals("RUNNER") && !player1.getName().equals(player.getName())) {
                runnerList.add(player1);
            }
        }
        if (mhTeam.getRunnerCount() >= 1) {
            ItemStack runnerHead1 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo1 = (SkullMeta) runnerHead1.getItemMeta();
            skullInfo1.setDisplayName(runnerList.get(0).getDisplayName());
            skullInfo1.setOwner(ChatColor.GREEN + runnerList.get(0).getName());
            runnerHead1.setItemMeta(skullInfo1);
            compassMenuHunterPage1.setItem(10, runnerHead1);
        }
        if (mhTeam.getRunnerCount() >= 2) {
            ItemStack runnerHead2 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo2 = (SkullMeta) runnerHead2.getItemMeta();
            skullInfo2.setDisplayName(runnerList.get(1).getDisplayName());
            skullInfo2.setOwner(ChatColor.GREEN + runnerList.get(1).getName());
            runnerHead2.setItemMeta(skullInfo2);
            compassMenuHunterPage1.setItem(11, runnerHead2);
        }
        if (mhTeam.getRunnerCount() >= 3 ) {
            ItemStack runnerHead3 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo3 = (SkullMeta) runnerHead3.getItemMeta();
            skullInfo3.setDisplayName(runnerList.get(2).getDisplayName());
            skullInfo3.setOwner(runnerList.get(2).getName());
            runnerHead3.setItemMeta(skullInfo3);
            compassMenuHunterPage1.setItem(12, runnerHead3);
        }
        if (mhTeam.getRunnerCount() == 4) {
            ItemStack runnerHead4 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo4 = (SkullMeta) runnerHead4.getItemMeta();
            skullInfo4.setDisplayName(runnerList.get(3).getDisplayName());
            skullInfo4.setOwner(ChatColor.GREEN + runnerList.get(3).getName());
            runnerHead4.setItemMeta(skullInfo4);
            compassMenuHunterPage1.setItem(13, runnerHead4);
        }

        ItemStack menuHunterPage2 = new ItemStack(Material.ARROW);
        ItemMeta setMenuHunterPage2 = menuHunterPage2.getItemMeta();
        setMenuHunterPage2.setDisplayName(ChatColor.GREEN + "追踪队友");
        menuHunterPage2.setItemMeta(setMenuHunterPage2);
        compassMenuHunterPage1.setItem(26,menuHunterPage2);

        ItemStack returnToSpawn = new ItemStack(Material.ENDER_PEARL);
        ItemMeta returnToSpawn1 = returnToSpawn.getItemMeta();
        returnToSpawn1.setDisplayName(ChatColor.GREEN + "返回出生点");
        returnToSpawn.setItemMeta(returnToSpawn1);
        compassMenuHunterPage1.setItem(4,returnToSpawn);
        player.openInventory(compassMenuHunterPage1);
    }
    public void createMenuRunnerPage1(Player player) {
        MhTeam mhTeam = new MhTeam();
        List<Player> runnerList = new ArrayList<>();
        Inventory compassMenuRunnerPage1 = Bukkit.createInventory(player, 27, ChatColor.GREEN + "追踪器");
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (mhTeam.getMhTeam(player1).equals("RUNNER") && !player1.getName().equals(player.getName())) {
                runnerList.add(player1);
            }
        }
        if (mhTeam.getRunnerCount() >= 2) {
            ItemStack runnerHead1 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo1 = (SkullMeta) runnerHead1.getItemMeta();
            skullInfo1.setDisplayName(runnerList.get(0).getDisplayName());
            skullInfo1.setOwner(ChatColor.GREEN + runnerList.get(0).getName());
            runnerHead1.setItemMeta(skullInfo1);
            compassMenuRunnerPage1.setItem(10, runnerHead1);
        }
        if (mhTeam.getRunnerCount() >= 3) {
            ItemStack runnerHead2 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo2 = (SkullMeta) runnerHead2.getItemMeta();
            skullInfo2.setDisplayName(runnerList.get(1).getDisplayName());
            skullInfo2.setOwner(ChatColor.GREEN + runnerList.get(1).getName());
            runnerHead2.setItemMeta(skullInfo2);
            compassMenuRunnerPage1.setItem(11, runnerHead2);
        }
        if (mhTeam.getRunnerCount() >= 4 ) {
            ItemStack runnerHead3 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo3 = (SkullMeta) runnerHead3.getItemMeta();
            skullInfo3.setDisplayName(runnerList.get(2).getDisplayName());
            skullInfo3.setOwner(runnerList.get(2).getName());
            runnerHead3.setItemMeta(skullInfo3);
            compassMenuRunnerPage1.setItem(12, runnerHead3);
        }
        ItemStack endTower1 = new ItemStack(Material.ENDER_PORTAL_FRAME);
        ItemMeta EndTower1 = endTower1.getItemMeta();
        EndTower1.setDisplayName(ChatColor.GREEN + "末地塔#1");
        endTower1.setItemMeta(EndTower1);
        compassMenuRunnerPage1.setItem(3,endTower1);

        ItemStack endTower2 = new ItemStack(Material.ENDER_PORTAL_FRAME);
        ItemMeta EndTower2 = endTower2.getItemMeta();
        EndTower2.setDisplayName(ChatColor.GREEN + "末地塔#2");
        endTower2.setItemMeta(EndTower2);
        compassMenuRunnerPage1.setItem(5,endTower2);


        player.openInventory(compassMenuRunnerPage1);
    }
    public void createMenuHunterPage2(Player player) {
        MhTeam mhTeam = new MhTeam();
        List<Player> hunterList = new ArrayList<>();
        Inventory compassMenuHunterPage2 = Bukkit.createInventory(player, 27, ChatColor.RED + "追踪器#2");
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (mhTeam.getMhTeam(player1).equals("HUNTER") && !player1.getName().equals(player.getName())) {
                hunterList.add(player1);
            }
        }
        if (mhTeam.getHunterCount() >= 2) {
            ItemStack hunterHead1 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo1 = (SkullMeta) hunterHead1.getItemMeta();
            skullInfo1.setDisplayName(hunterList.get(0).getDisplayName());
            skullInfo1.setOwner(ChatColor.RED + hunterList.get(0).getName());
            hunterHead1.setItemMeta(skullInfo1);
            compassMenuHunterPage2.setItem(10, hunterHead1);
        }
        if (mhTeam.getHunterCount() >= 3) {
            ItemStack hunterHead2 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo2 = (SkullMeta) hunterHead2.getItemMeta();
            skullInfo2.setDisplayName(hunterList.get(1).getDisplayName());
            skullInfo2.setOwner(ChatColor.RED + hunterList.get(1).getName());
            hunterHead2.setItemMeta(skullInfo2);
            compassMenuHunterPage2.setItem(11, hunterHead2);
        }
        if (mhTeam.getHunterCount() >= 4 ) {
            ItemStack hunterHead3 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo3 = (SkullMeta) hunterHead3.getItemMeta();
            skullInfo3.setDisplayName(hunterList.get(2).getDisplayName());
            skullInfo3.setOwner(ChatColor.RED + hunterList.get(2).getName());
            hunterHead3.setItemMeta(skullInfo3);
            compassMenuHunterPage2.setItem(12, hunterHead3);
        }
        if (mhTeam.getHunterCount() >= 5) {
            ItemStack hunterHead4 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo4 = (SkullMeta) hunterHead4.getItemMeta();
            skullInfo4.setDisplayName(hunterList.get(3).getDisplayName());
            skullInfo4.setOwner(ChatColor.RED + hunterList.get(3).getName());
            hunterHead4.setItemMeta(skullInfo4);
            compassMenuHunterPage2.setItem(13, hunterHead4);
        }
        if (mhTeam.getHunterCount() >= 6 ) {
            ItemStack hunterHead5 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo5 = (SkullMeta) hunterHead5.getItemMeta();
            skullInfo5.setDisplayName(hunterList.get(4).getDisplayName());
            skullInfo5.setOwner(ChatColor.RED + hunterList.get(4).getName());
            hunterHead5.setItemMeta(skullInfo5);
            compassMenuHunterPage2.setItem(14, hunterHead5);
        }
        if (mhTeam.getHunterCount() >= 7 ) {
            ItemStack hunterHead6 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo6 = (SkullMeta) hunterHead6.getItemMeta();
            skullInfo6.setDisplayName(hunterList.get(5).getDisplayName());
            skullInfo6.setOwner(ChatColor.RED + hunterList.get(5).getName());
            hunterHead6.setItemMeta(skullInfo6);
            compassMenuHunterPage2.setItem(15, hunterHead6);
        }
        if (mhTeam.getHunterCount() >= 8 ) {
            ItemStack hunterHead7 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullInfo7 = (SkullMeta) hunterHead7.getItemMeta();
            skullInfo7.setDisplayName(hunterList.get(5).getDisplayName());
            skullInfo7.setOwner(ChatColor.RED + hunterList.get(5).getName());
            hunterHead7.setItemMeta(skullInfo7);
            compassMenuHunterPage2.setItem(16, hunterHead7);
        }
        ItemStack menuHunterPage1 = new ItemStack(Material.ARROW);
        ItemMeta setMenuHunterPage1 = menuHunterPage1.getItemMeta();
        setMenuHunterPage1.setDisplayName(ChatColor.GREEN + "返回");
        menuHunterPage1.setItemMeta(setMenuHunterPage1);
        compassMenuHunterPage2.setItem(26,menuHunterPage1);

        ItemStack endTower1 = new ItemStack(Material.ENDER_PORTAL_FRAME);
        ItemMeta EndTower1 = endTower1.getItemMeta();
        EndTower1.setDisplayName(ChatColor.GREEN + "末地塔#1");
        endTower1.setItemMeta(EndTower1);
        compassMenuHunterPage2.setItem(3,endTower1);

        ItemStack endTower2 = new ItemStack(Material.ENDER_PORTAL_FRAME);
        ItemMeta EndTower2 = endTower2.getItemMeta();
        EndTower2.setDisplayName(ChatColor.GREEN + "末地塔#2");
        endTower2.setItemMeta(EndTower2);
        compassMenuHunterPage2.setItem(5,endTower2);
        player.openInventory(compassMenuHunterPage2);
    }
    public menu(){

    }
}
