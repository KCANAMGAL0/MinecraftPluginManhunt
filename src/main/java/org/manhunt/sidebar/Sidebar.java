package org.manhunt.sidebar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.manhunt.Utils.DateUtil;
import org.manhunt.game.*;
import org.manhunt.player.nametag.NameTagManager;
import org.manhunt.player.team.MhTeam;



public class Sidebar {
    public void buildWaitingSideBar(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        HealthDisplay();
        Objective obj = scoreboard.registerNewObjective("manhunt","dummy");
        obj.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "猎人游戏");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score setDate = obj.getScore(ChatColor.GRAY + DateUtil.getDate() + ChatColor.DARK_GRAY + "          v0.1");
        setDate.setScore(7);

        Score blank3 = obj.getScore("  ");
        blank3.setScore(6);
        int playerCount = 0;
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            playerCount++;
        }
        Score showPlayers = obj.getScore(ChatColor.WHITE + "玩家: " + ChatColor.GREEN + playerCount + "/12");
        showPlayers.setScore(5);

        Score blank2 = obj.getScore(" ");
        blank2.setScore(4);

        Score waiting = obj.getScore(ChatColor.WHITE + "等待中...");
        waiting.setScore(3);

        Score blank1 = obj.getScore("");
        blank1.setScore(2);

        Score website = obj.getScore(ChatColor.RED + "虫豸版");
        website.setScore(1);
        player.setScoreboard(scoreboard);
    }
    public void buildPrepareSideBar(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        HealthDisplay();
        /*
        Team team1 = scoreboard.registerNewTeam(NameTags.RUNNER1.name());
        team1.setPrefix(NameTags.RUNNER1.getDisplay());
        Team team2 = scoreboard.registerNewTeam(NameTags.RUNNER2.name());
        team2.setPrefix(NameTags.RUNNER2.getDisplay());
        Team team3 = scoreboard.registerNewTeam(NameTags.HUNTER1.name());
        team3.setPrefix(NameTags.HUNTER1.getDisplay());
        Team team4 = scoreboard.registerNewTeam(NameTags.HUNTER2.name());
        team4.setPrefix(NameTags.HUNTER2.getDisplay());
        NameTags nameTags;
        MhTeam mhTeam = new MhTeam();

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if ((mhTeam.getMhTeam(player).equals("HUNTER")) && (mhTeam.getMhTeam(player1).equals("HUNTER"))) {
                nameTags = NameTags.HUNTER1;
                player.getPlayer().getScoreboard().getTeam(nameTags.name()).addEntry(player1.getName());
            } else if ((mhTeam.getMhTeam(player).equals("HUNTER")) && (mhTeam.getMhTeam(player1).equals("RUNNER"))) {
                nameTags = NameTags.HUNTER2;
                player.getPlayer().getScoreboard().getTeam(NameTags.RUNNER2.name()).addEntry(player1.getName());
            } else if ((mhTeam.getMhTeam(player).equals("RUNNER")) && (mhTeam.getMhTeam(player1).equals("HUNTER"))) {
                nameTags = NameTags.RUNNER2;
                player.getPlayer().getScoreboard().getTeam(NameTags.HUNTER2.name()).addEntry(player1.getName());
            } else if ((mhTeam.getMhTeam(player).equals("RUNNER")) && (mhTeam.getMhTeam(player1).equals("RUNNER"))) {
                nameTags = NameTags.RUNNER1;
                player.getPlayer().getScoreboard().getTeam(nameTags.name()).addEntry(player1.getName());
            } else return;
            player1.getPlayer().getScoreboard().getTeam(nameTags.name()).addEntry(player.getName());
        }

         */
        Objective obj = scoreboard.registerNewObjective("manhunt","dummy");
        obj.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "猎人游戏");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score setDate = obj.getScore(ChatColor.GRAY + DateUtil.getDate() + ChatColor.DARK_GRAY + "          v0.1");
        setDate.setScore(12);

        Score blank3 = obj.getScore("  ");
        blank3.setScore(11);

        Score prepareTimeLeftTitle = obj.getScore(ChatColor.WHITE + "游戏开始: ");
        prepareTimeLeftTitle.setScore(10);
        Team prepareTimeLeft = scoreboard.registerNewTeam("prepareTimeLeft");
        prepareTimeLeft.addEntry(ChatColor.GRAY.toString());
        prepareTimeLeft.setPrefix("§a" + new prepareStart().getPrepareTimeLeft());
        prepareTimeLeft.setSuffix(ChatColor.GREEN + " 秒");
        obj.getScore(ChatColor.GRAY.toString()).setScore(9);

        Score blank2 = obj.getScore(" ");
        blank2.setScore(8);

        Score runnerCount1 = obj.getScore(ChatColor.WHITE + "存活勇者: ");
        runnerCount1.setScore(7);
        Team runnerCount = scoreboard.registerNewTeam("runnerCount");
        runnerCount.addEntry(ChatColor.AQUA.toString());
        runnerCount.setPrefix("§a" + new MhTeam().getRunnerCount());
        runnerCount.setSuffix(ChatColor.GREEN + " 人");
        obj.getScore(ChatColor.AQUA.toString()).setScore(6);

        Score blank4 = obj.getScore("   ");
        blank4.setScore(5);

        Score hunterCount1 = obj.getScore(ChatColor.WHITE + "猎人数量: ");
        hunterCount1.setScore(4);
        Team hunterCount = scoreboard.registerNewTeam("hunterCount");
        hunterCount.addEntry(ChatColor.YELLOW.toString());
        hunterCount.setPrefix("§a" + new MhTeam().getHunterCount());
        hunterCount.setSuffix(ChatColor.GREEN + " 人");
        obj.getScore(ChatColor.YELLOW.toString()).setScore(3);

        Score blank1 = obj.getScore("");
        blank1.setScore(2);

        Score website = obj.getScore(ChatColor.RED + "虫豸版");
        website.setScore(1);

        player.setScoreboard(scoreboard);

        NameTagManager.setAllNameTag();

    }
    public void buildGrowingSidebar(Player player){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        HealthDisplay();
        Objective obj = scoreboard.registerNewObjective("manhunt","dummy");
        obj.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "猎人游戏");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score setDate = obj.getScore(ChatColor.GRAY + DateUtil.getDate() + ChatColor.DARK_GRAY + "          v0.1");
        setDate.setScore(12);

        Score blank3 = obj.getScore("  ");
        blank3.setScore(11);

        Score prepareTimeLeftTitle = obj.getScore(ChatColor.WHITE + "追踪器启用: ");
        prepareTimeLeftTitle.setScore(10);
        Team growingTimeLeft = scoreboard.registerNewTeam("growingTimeLeft");
        growingTimeLeft.addEntry(ChatColor.GRAY.toString());
        int growingTime = new GrowingPhase().getGrowingTimeLeft();
        if (growingTime > 60) {
            growingTime /= 60;
            growingTimeLeft.setPrefix("§a" + growingTime);
            if (new GrowingPhase().getGrowingTimeLeft() % 60 <= 9) {
                growingTimeLeft.setSuffix(ChatColor.GREEN + ":0" + new GrowingPhase().getGrowingTimeLeft() % 60);
            }else growingTimeLeft.setSuffix(ChatColor.GREEN + ":" + new GrowingPhase().getGrowingTimeLeft() % 60);
        }else {
            if (growingTime <= 9) {
                growingTimeLeft.setPrefix("§a0:0" + growingTime);
            }else growingTimeLeft.setPrefix("§a0:" + growingTime);
            growingTimeLeft.setSuffix("");
        }
        obj.getScore(ChatColor.GRAY.toString()).setScore(9);

        Score blank2 = obj.getScore(" ");
        blank2.setScore(8);

        Score runnerCount1 = obj.getScore(ChatColor.WHITE + "存活勇者: ");
        runnerCount1.setScore(7);
        Team runnerCount = scoreboard.registerNewTeam("runnerCount");
        runnerCount.addEntry(ChatColor.AQUA.toString());
        runnerCount.setPrefix("§a" + new MhTeam().getRunnerCount());
        runnerCount.setSuffix(ChatColor.GREEN + " 人");
        obj.getScore(ChatColor.AQUA.toString()).setScore(6);

        Score blank4 = obj.getScore("   ");
        blank4.setScore(5);

        Score hunterCount1 = obj.getScore(ChatColor.WHITE + "猎人数量: ");
        hunterCount1.setScore(4);
        Team hunterCount = scoreboard.registerNewTeam("hunterCount");
        hunterCount.addEntry(ChatColor.YELLOW.toString());
        hunterCount.setPrefix("§a" + new MhTeam().getHunterCount());
        hunterCount.setSuffix(ChatColor.GREEN + " 人");
        obj.getScore(ChatColor.YELLOW.toString()).setScore(3);

        Score blank1 = obj.getScore("");
        blank1.setScore(2);

        Score website = obj.getScore(ChatColor.RED + "虫豸版");
        website.setScore(1);

        player.setScoreboard(scoreboard);
        NameTagManager.setAllNameTag();
    }

    public void updateGrowingSidebar(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        int growingTime = new GrowingPhase().getGrowingTimeLeft();
        if (growingTime > 60) {
            growingTime /= 60;
            scoreboard.getTeam("growingTimeLeft").setPrefix("§a" + growingTime);
            if (new GrowingPhase().getGrowingTimeLeft() % 60 <= 9) {
                scoreboard.getTeam("growingTimeLeft").setSuffix(ChatColor.GREEN + ":0" + new GrowingPhase().getGrowingTimeLeft() % 60);
            }else scoreboard.getTeam("growingTimeLeft").setSuffix(ChatColor.GREEN + ":" + new GrowingPhase().getGrowingTimeLeft() % 60);
        }else {
            if (growingTime <= 9) {
                scoreboard.getTeam("growingTimeLeft").setPrefix("§a0:0" + growingTime);
            }else scoreboard.getTeam("growingTimeLeft").setPrefix("§a0:" + growingTime);
            scoreboard.getTeam("growingTimeLeft").setSuffix("");
        }

        scoreboard.getTeam("runnerCount").setPrefix("§a" + new MhTeam().getRunnerCount());
        scoreboard.getTeam("hunterCount").setPrefix("§a" + new MhTeam().getHunterCount());
    }
    public void buildHuntingSidebar(Player player){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        HealthDisplay();
        Objective obj = scoreboard.registerNewObjective("manhunt","dummy");
        obj.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "猎人游戏");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score setDate = obj.getScore(ChatColor.GRAY + DateUtil.getDate() + ChatColor.DARK_GRAY + "          v0.1");
        setDate.setScore(12);

        Score blank3 = obj.getScore("  ");
        blank3.setScore(11);

        Score prepareTimeLeftTitle = obj.getScore(ChatColor.WHITE + "末地开启: ");
        prepareTimeLeftTitle.setScore(10);
        Team huntingTimeLeft = scoreboard.registerNewTeam("huntingTimeLeft");
        huntingTimeLeft.addEntry(ChatColor.GRAY.toString());
        int huntingTime = new HuntingPhase().getHuntingTimeLeft();
        if (huntingTime > 60) {
            huntingTime /= 60;
            huntingTimeLeft.setPrefix("§a" + huntingTime);
            huntingTimeLeft.setSuffix(ChatColor.GREEN + ":" + new HuntingPhase().getHuntingTimeLeft() % 60);
        }else {
            huntingTimeLeft.setPrefix("§a0:" + huntingTime);
            huntingTimeLeft.setSuffix("");
        }
        obj.getScore(ChatColor.GRAY.toString()).setScore(9);

        Score blank2 = obj.getScore(" ");
        blank2.setScore(8);

        Score runnerCount1 = obj.getScore(ChatColor.WHITE + "存活勇者: ");
        runnerCount1.setScore(7);
        Team runnerCount = scoreboard.registerNewTeam("runnerCount");
        runnerCount.addEntry(ChatColor.AQUA.toString());
        runnerCount.setPrefix("§a" + new MhTeam().getRunnerCount());
        runnerCount.setSuffix(ChatColor.GREEN + " 人");
        obj.getScore(ChatColor.AQUA.toString()).setScore(6);

        Score blank4 = obj.getScore("   ");
        blank4.setScore(5);

        Score hunterCount1 = obj.getScore(ChatColor.WHITE + "猎人数量: ");
        hunterCount1.setScore(4);
        Team hunterCount = scoreboard.registerNewTeam("hunterCount");
        hunterCount.addEntry(ChatColor.YELLOW.toString());
        hunterCount.setPrefix("§a" + new MhTeam().getHunterCount());
        hunterCount.setSuffix(ChatColor.GREEN + " 人");
        obj.getScore(ChatColor.YELLOW.toString()).setScore(3);

        Score blank1 = obj.getScore("");
        blank1.setScore(2);

        Score website = obj.getScore(ChatColor.RED + "虫豸版");
        website.setScore(1);

        player.setScoreboard(scoreboard);
        NameTagManager.setAllNameTag();
    }
    public void updateHuntingSidebar(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        int huntingTime = new HuntingPhase().getHuntingTimeLeft();
        if (huntingTime > 60) {
            huntingTime /= 60;
            scoreboard.getTeam("huntingTimeLeft").setPrefix("§a" + huntingTime);
            scoreboard.getTeam("huntingTimeLeft").setSuffix(ChatColor.GREEN + ":" + new HuntingPhase().getHuntingTimeLeft() % 60);
        }else {
            scoreboard.getTeam("huntingTimeLeft").setPrefix("§a0:" + huntingTime);
            scoreboard.getTeam("huntingTimeLeft").setSuffix("");
        }

        scoreboard.getTeam("runnerCount").setPrefix("§a" + new MhTeam().getRunnerCount());
        scoreboard.getTeam("hunterCount").setPrefix("§a" + new MhTeam().getHunterCount());
    }
    public void buildEnd_OpenSidebar(Player player){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = scoreboard.registerNewObjective("manhunt","dummy");
        obj.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "猎人游戏");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score setDate = obj.getScore(ChatColor.GRAY + DateUtil.getDate() + ChatColor.DARK_GRAY + "          v0.1");
        setDate.setScore(12);

        Score blank3 = obj.getScore("  ");
        blank3.setScore(11);

        Score prepareTimeLeftTitle = obj.getScore(ChatColor.WHITE + "游戏结束: ");
        prepareTimeLeftTitle.setScore(10);
        Team gameTimeLeft = scoreboard.registerNewTeam("GameTimeLeft");
        gameTimeLeft.addEntry(ChatColor.GRAY.toString());
        int gameTimer = new end_Open().getGameTimer();
        if (gameTimer > 60) {
            gameTimer /= 60;
            gameTimeLeft.setPrefix("§a" + gameTimer);
            gameTimeLeft.setSuffix(ChatColor.GREEN + ":" + new end_Open().getGameTimer() % 60);
        }else {
            gameTimeLeft.setPrefix("§a0:" + gameTimer);
            gameTimeLeft.setSuffix("");
        }
        obj.getScore(ChatColor.GRAY.toString()).setScore(9);

        Score blank2 = obj.getScore(" ");
        blank2.setScore(8);

        Score runnerCount1 = obj.getScore(ChatColor.WHITE + "存活勇者: ");
        runnerCount1.setScore(7);
        Team runnerCount = scoreboard.registerNewTeam("runnerCount");
        runnerCount.addEntry(ChatColor.AQUA.toString());
        runnerCount.setPrefix("§a" + new MhTeam().getRunnerCount());
        runnerCount.setSuffix(ChatColor.GREEN + " 人");
        obj.getScore(ChatColor.AQUA.toString()).setScore(6);

        Score blank4 = obj.getScore("   ");
        blank4.setScore(5);

        Score hunterCount1 = obj.getScore(ChatColor.WHITE + "猎人数量: ");
        hunterCount1.setScore(4);
        Team hunterCount = scoreboard.registerNewTeam("hunterCount");
        hunterCount.addEntry(ChatColor.YELLOW.toString());
        hunterCount.setPrefix("§a" + new MhTeam().getHunterCount());
        hunterCount.setSuffix(ChatColor.GREEN + " 人");
        obj.getScore(ChatColor.YELLOW.toString()).setScore(3);

        Score blank1 = obj.getScore("");
        blank1.setScore(2);

        Score website = obj.getScore(ChatColor.RED + "虫豸版");
        website.setScore(1);

        player.setScoreboard(scoreboard);
        NameTagManager.setAllNameTag();
    }

    public void updateEnd_OpenSidebar(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        int gameTimer = new end_Open().getGameTimer();
        if (gameTimer > 60) {
            gameTimer /= 60;
            scoreboard.getTeam("GameTimeLeft").setPrefix("§a" + gameTimer);
            scoreboard.getTeam("GameTimeLeft").setSuffix(ChatColor.GREEN + ":" + new end_Open().getGameTimer() % 60);
        }else {
            scoreboard.getTeam("GameTimeLeft").setPrefix("§a0:" + gameTimer);
            scoreboard.getTeam("GameTimeLeft").setSuffix("");
        }
    }
    public void HealthDisplay(){

    }
}
