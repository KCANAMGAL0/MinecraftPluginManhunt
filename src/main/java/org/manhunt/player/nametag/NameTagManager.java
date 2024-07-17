package org.manhunt.player.nametag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.manhunt.MhClass.MhClassManager;
import org.manhunt.player.team.MhTeam;
import org.bukkit.scoreboard.*;


public class NameTagManager {
    public static void setAllNameTag(){
        MhTeam mhTeam = new MhTeam();
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                registerPlayerTeam(player,player1);
            }
        }
        final String RUNNER1 = ChatColor.GREEN + "(勇者)";
        final String RUNNER2 = ChatColor.RED + "(勇者)";
        final String HUNTER1 = ChatColor.GREEN + "(猎人)";
        final String HUNTER2 = ChatColor.RED + "(猎人)";

        MhClassManager mhClassManager = new MhClassManager();

        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (mhTeam.getMhTeam(player).equals("RUNNER")){
                    player.setDisplayName(ChatColor.GREEN + "(勇者)" + player.getName());
                    if (mhTeam.getMhTeam(player1).equals("RUNNER")){
                        player.getScoreboard().getTeam(RUNNER1 + mhClassManager.getPlayerClass(player1)).addEntry(player1.getName());
                        player1.getScoreboard().getTeam(RUNNER1 + mhClassManager.getPlayerClass(player)).addEntry(player.getName());
                    }else if (mhTeam.getMhTeam(player1).equals("HUNTER")) {
                        player.getScoreboard().getTeam(HUNTER2 + mhClassManager.getPlayerClass(player1)).addEntry(player1.getName());
                        player1.getScoreboard().getTeam(RUNNER2 + mhClassManager.getPlayerClass(player)).addEntry(player.getName());
                    }
                }else if (mhTeam.getMhTeam(player).equals("HUNTER")){
                    player.setDisplayName(ChatColor.RED + "(猎人)" + player.getName());
                    if (mhTeam.getMhTeam(player1).equals("HUNTER")){
                        player.getScoreboard().getTeam(HUNTER1 + mhClassManager.getPlayerClass(player1)).addEntry(player1.getName());
                        player1.getScoreboard().getTeam(HUNTER1 + mhClassManager.getPlayerClass(player)).addEntry(player.getName());
                    }else if (mhTeam.getMhTeam(player1).equals("RUNNER")) {
                        player.getScoreboard().getTeam(RUNNER2 + mhClassManager.getPlayerClass(player1)).addEntry(player1.getName());
                        player1.getScoreboard().getTeam(HUNTER2 + mhClassManager.getPlayerClass(player)).addEntry(player.getName());
                    }
                }
            }
        }

        /*
        if (mhTeam.getMhTeam(player).equals("RUNNER")){
            team.setPrefix(ChatColor.GREEN + "(勇者)");
        }else team.setPrefix(ChatColor.RED + "(猎人)");
        team.addEntry(player.getName());
        */
    }

    public static void newNameTag(Player player) {
        /*
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
    }
    public static void registerPlayerTeam(Player player,Player player1){
        Scoreboard scoreboard = player.getPlayer().getScoreboard();
        for (String className : getClassNames()){
            for (String name : getTeamNames()) {
                Team team = scoreboard.getTeam(name + className);
                if (team == null) {
                    scoreboard.registerNewTeam(name + className);
                    scoreboard.getTeam(name + className).setPrefix(name);
                    scoreboard.getTeam(name + className).setSuffix(new MhClassManager().getClassStringName(className));
                }
            }
        }
    }
    public static String[] getTeamNames(){
        final String RUNNER1 = ChatColor.GREEN + "(勇者)";
        final String RUNNER2 = ChatColor.RED + "(勇者)";
        final String HUNTER1 = ChatColor.GREEN + "(猎人)";
        final String HUNTER2 = ChatColor.RED + "(猎人)";
        return new String[]{RUNNER1,RUNNER2,HUNTER1,HUNTER2};
    }
    public static String[] getClassNames(){
        return new MhClassManager().getMhClasses();
    }
}
