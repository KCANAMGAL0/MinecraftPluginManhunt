package org.manhunt.game;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.manhunt.MhClass.MhClassManager;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;
import org.manhunt.player.team.RunnerMaxHealth;
import org.manhunt.sidebar.Sidebar;
import org.manhunt.Main;

import java.util.ArrayList;
import java.util.List;

public class prepareStart implements CommandExecutor {
    public static int timer;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        timer = 15;  // 15
        gameManager gM = new gameManager();
        playerManager pM = new playerManager();
        gM.setGamePhase("prepare");
        MhTeam mhTeam = new MhTeam();
        Sidebar sidebar = new Sidebar();
        new RunnerMaxHealth().setRunnerMaxHealth();
        resetClass();
        for (Player player : Bukkit.getOnlinePlayers()){
            player.setHealth(player.getMaxHealth());
            sidebar.buildPrepareSideBar(player);
            if (mhTeam.isRunner(player)) {
                player.setDisplayName(ChatColor.GREEN + "(勇者)" + player.getName());
            }else if (mhTeam.isHunter(player)) {
                player.setDisplayName(ChatColor.RED + "(猎人)" + player.getName());
                player.setMaxHealth(20);
            }else {
                player.setGameMode(GameMode.SPECTATOR);
            }
        }
        gM.sendMessageToAllPlayers(ChatColor.GREEN + "准备阶段");
        new placeWaitingArea().create();
        new BukkitRunnable(){
            @Override
            public void run(){
                if (timer == 0) {
                    sendTips();

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle(ChatColor.RED + "游戏开始", null);
                        player.setHealth(player.getMaxHealth());
                        player.setFoodLevel(19);
                        player.setSaturation(20);
                        resetClass();
                    }
                    new startLocation().tpToStartLocation();
                    pM.SoundForAllPlayers(Sound.ENDERDRAGON_GROWL,1.0F,1.0F);
                    new GrowingPhase().startGrowingPhase();
                    playerStartItem();
                    this.cancel();
                }else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        updateSidebar(player);
                        player.setHealth(player.getMaxHealth());
                    }

                    if (timer <= 5) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendTitle(ChatColor.RED + String.valueOf(timer), null);
                            player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
                        }
                    }
                    timer--;
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class),20,20L);
        return true;
    }
    public void updateSidebar(Player player) {
        player.getScoreboard().getTeam("prepareTimeLeft").setPrefix("§a" + getPrepareTimeLeft());
        player.getScoreboard().getTeam("runnerCount").setPrefix("§a" + new MhTeam().getRunnerCount());
        player.getScoreboard().getTeam("hunterCount").setPrefix("§a" + new MhTeam().getHunterCount());
    }

    public int getPrepareTimeLeft() {
        return timer;
    }

    private void sendTips(){
        List<String> tips = new ArrayList<>();
        tips.add(ChatColor.WHITE + ChatColor.BOLD.toString() +"============================================");
        tips.add(ChatColor.RED + ChatColor.BOLD.toString() + "                                   猎人游戏                                   ");
        tips.add("");
        tips.add(ChatColor.YELLOW +ChatColor.BOLD.toString() + "在有限的区域内上演一出'追杀'大戏,猎人们需要利用你的脑洞和技术");
        tips.add(ChatColor.YELLOW +ChatColor.BOLD.toString() + "杀死所有懦弱的勇者或者阻止他们屠龙，而屠龙勇士们需要尽快发展壮");
        tips.add(ChatColor.YELLOW +ChatColor.BOLD.toString() + "大并最终杀死邪恶的末影龙，注意抱团行动并挫败那像老鼠一样四处出");
        tips.add(ChatColor.YELLOW +ChatColor.BOLD.toString() + "击阻挠行动的猎人，合理利用职业技能和技巧达成团队的目标赢得比赛");
        tips.add("");
        tips.add(ChatColor.WHITE + ChatColor.BOLD.toString() +"============================================");
        for (Player player : Bukkit.getOnlinePlayers()){
            for (String s : tips){
                player.sendMessage(s);
            }
        }
    }
    public void playerStartItem() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (PotionEffect potionEffect : player.getActivePotionEffects()){
                player.removePotionEffect(potionEffect.getType());
            }
            player.getInventory().clear();
            new playerManager().addEffect(player,PotionEffectType.FIRE_RESISTANCE,3 * 60 * 20,0,false);
            new playerManager().addEffect(player,PotionEffectType.NIGHT_VISION,9999999,0,false);
        }
        MhTeam mhTeam = new MhTeam();
        ItemStack[] tools = new ItemStack[]{new ItemStack(Material.STONE_PICKAXE),new ItemStack(Material.STONE_AXE),new ItemStack(Material.STONE_SPADE)};
        /*
        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        ItemStack stonePickaxe = new ItemStack(Material.STONE_PICKAXE);
        ItemStack stoneAxe = new ItemStack(Material.STONE_AXE);
        ItemStack stoneShovel = new ItemStack(Material.STONE_SPADE);
         */
        for (ItemStack itemStack : tools){
            ItemMeta efficiency1 = itemStack.getItemMeta();
            efficiency1.addEnchant(Enchantment.DIG_SPEED,1,true);
            itemStack.setItemMeta(efficiency1);
        }
        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        ItemMeta unbreaking1 = stoneSword.getItemMeta();
        unbreaking1.addEnchant(Enchantment.DURABILITY,1,true);
        stoneSword.setItemMeta(unbreaking1);
        ItemStack ironPickaxe = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta efficiency1 = ironPickaxe.getItemMeta();
        efficiency1.addEnchant(Enchantment.DIG_SPEED,1,true);
        ironPickaxe.setItemMeta(efficiency1);
        ItemStack bow = new ItemStack(Material.BOW);
        ItemStack arrow = new ItemStack(Material.ARROW,64);
        /*
        ItemStack EnchantingBook = new ItemStack(Material.ENCHANTED_BOOK,1);

        EnchantmentStorageMeta unbreaking3 = (EnchantmentStorageMeta) EnchantingBook.getItemMeta();
        unbreaking3.addStoredEnchant(Enchantment.DURABILITY,3,true);
        EnchantingBook.setItemMeta(unbreaking3);

         */

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().setItem(0,stoneSword);
            if (!new MhClassManager().getPlayerClass(player).equals("Miner")) {
                for (int i = 1; i <= tools.length; i++) {
                    player.getInventory().setItem(i, tools[i - 1]);
                }
            }else {
                for (int i = 2; i <= tools.length; i++) {
                    player.getInventory().setItem(i, tools[i - 1]);
                }
                player.getInventory().setItem(1,ironPickaxe);
            }
            player.getInventory().setItem(4,new ItemStack(Material.WORKBENCH,1));
            player.getInventory().setItem(8,new ItemStack(Material.COMPASS,1));

            if (mhTeam.getMhTeam(player).equals("RUNNER")){
                player.getInventory().setItem(5,new ItemStack(Material.APPLE,8));
                player.getInventory().setItem(6,new ItemStack(Material.BREAD,24));
                player.getInventory().setItem(7,new ItemStack(Material.GOLDEN_APPLE,mhTeam.getRunnerCount() + 1));
                player.getInventory().setItem(9,bow);
                player.getInventory().setItem(11,arrow);
            }else if (mhTeam.getMhTeam(player).equals("HUNTER")) {
                player.getInventory().setItem(5,new ItemStack(Material.APPLE,16));
                player.getInventory().setItem(6,new ItemStack(Material.COOKED_BEEF,mhTeam.getHunterCount() * 2));
                player.getInventory().setItem(9,bow);
                player.getInventory().setItem(10,arrow);
            }
        }
        List<Player> Hunters = mhTeam.getHunterPlayers();
        int c = mhTeam.getHunterCount();
        boolean EnchantingTableForHunter = false;
        for (Player player : mhTeam.getHunterPlayers()) {
            if (!EnchantingTableForHunter) {
                int a = (int) (Math.random() * 100 + 1);
                if (a <= 50 || c == 1) {
                    player.getInventory().setItem(7,new ItemStack(Material.ENCHANTMENT_TABLE,1));
                    EnchantingTableForHunter = true;
                }
                c--;
            }
        }
        boolean EnchantingTableForRunner = false;
        List<Player> Runners= mhTeam.getRunnerPlayers();
        int b = mhTeam.getRunnerCount();
        for (Player player : mhTeam.getRunnerPlayers()) {
            new playerManager().addEffect(player, PotionEffectType.ABSORPTION,300 * 20,4,true);
            new playerManager().addEffect(player, PotionEffectType.SPEED,20 * 60,0,true);
            if (!EnchantingTableForRunner) {
                int a = (int) (Math.random() * 100 + 1);
                if (a <= 50 || b == 1) {
                    player.getInventory().setItem(10, new ItemStack(Material.ENCHANTMENT_TABLE, 1));
                    EnchantingTableForRunner = true;
                }
                c--;
            }
        }
    }

    public void resetClass(){
        MhTeam mhTeam = new MhTeam();
        MhClassManager mhClassManager = new MhClassManager();
        for (Player player : mhTeam.getHunterPlayers()){
            if (mhClassManager.getPlayerClass(player).equals("unknown")) {
                mhClassManager.setPlayerClassInfo(player, "Doc");
            }
        }
        for (Player player : mhTeam.getRunnerPlayers()){
            if (mhClassManager.getPlayerClass(player).equals("unknown")) {
                mhClassManager.setPlayerClassInfo(player, "Miner");
            }
        }
    }
    public prepareStart() {

    }


}
