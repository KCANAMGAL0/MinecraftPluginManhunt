package org.manhunt;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.manhunt.Brewing.BrewingManager;
import org.manhunt.MhClass.*;
import org.manhunt.buliding.protect;
import org.manhunt.command.chat.chat;
import org.manhunt.command.chat.shout;
import org.manhunt.debug.itemDetect;
import org.manhunt.entity.CustomZombie;
import org.manhunt.entity.EntityManager;
import org.manhunt.entity.enderDragon;
import org.manhunt.food.*;
import org.manhunt.mining.*;
import org.manhunt.command.*;
import org.manhunt.game.*;
import org.manhunt.player.LaunchProjectile;
import org.manhunt.player.SelectClass.ClassMenuManager;
import org.manhunt.player.compass.clickOnMenu;
import org.manhunt.player.compass.rightClickOnCompass;
import org.manhunt.player.damage.DamageManager;
import org.manhunt.player.PlayerDeath;
import org.manhunt.player.damage.KnockBackEditor;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.attackOnPlayer;
import org.manhunt.teamchat.teamChat;

import java.util.ArrayList;
import java.util.List;


public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("猎人游戏插件已加载");
        List<Listener> eventListener = new ArrayList<>();
        eventListener.add(new stoneBreak());
        eventListener.add(new oreBreak());
        eventListener.add(new eatFood());
        eventListener.add(new playerJoin());
        eventListener.add(new attackOnPlayer());
        eventListener.add(new PlayerDeath());
        eventListener.add(new rightClickOnCompass());
        eventListener.add(new clickOnMenu());
        //eventListener.add(new itemDetect());
        eventListener.add(new digOnWood());
        eventListener.add(new DamageManager());
        eventListener.add(new teamChat());
        eventListener.add(new enderDragon());
        eventListener.add(new end_Open());
        eventListener.add(new breakLeaves());
        eventListener.add(new protect());
        eventListener.add(new DoubleShift());
        eventListener.add(new ClassMenuManager());
        eventListener.add(new playerLeave());
        eventListener.add(new RightClickSword());
        eventListener.add(new ServerList());
        eventListener.add(new KnockBackEditor());
        eventListener.add(new MhClassManager());
        eventListener.add(new LaunchProjectile());
        eventListener.add(new BrewingManager());
        eventListener.add(new TheNether());
        eventListener.add(new AntiRunEat());
        eventListener.add(new EntityManager());
        eventRegister(eventListener);

        getCommand("prepare").setExecutor(new prepareStart());
        getCommand("team").setExecutor(new team());
        getCommand("getLastDamageFrom").setExecutor(new getLastDamageFrom());
        getCommand("shout").setExecutor(new shout());
        getCommand("s").setExecutor(new shout());
        getCommand("setclass").setExecutor(new setclass());
        getCommand("getclass").setExecutor(new getclass());
        getCommand("randomTeam").setExecutor(new randomTeam());
        getCommand("chat").setExecutor(new chat());
        getCommand("reset").setExecutor(new reset());
        playerManager pM = new playerManager();
        new gameManager().resetAllPlayersData(false);
        ItemStack itemStack = new ItemStack(Material.NETHER_STAR,1);
        for (Player player : Bukkit.getOnlinePlayers()) {
            Bukkit.getOnlinePlayers().forEach(player1 -> player.showPlayer(player1));
            new DoubleShift().resetShift(player);
            new MhClassManager().resetPlayerClass(player);
            new Vampire().resetBloodingSkill(player);
            new Doctor().resetRegenSkill(player);
            DoubleShift.ToggleSneak.put(player,0);
            LightningGod.AttackTime.put(player,0);
            Robber.aCD.put(player,0L);
            player.setItemInHand(itemStack);
            chat.ChatType.put(player,"team");
            //((CraftPlayer) player).getHandle().getAttributeInstance(Attribute).setValue(0);
        }
        Bukkit.getServer().setSpawnRadius(0);
        new end_Open().setIsEndOpened(false);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        new gameManager().setGamePhase("waiting");
        //Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "prepare");
        new BukkitRunnable() {
            @Override
            public void run() {  //Main Runnable
                new MhClassManager().SkillDisplay();

            }
        }.runTaskTimerAsynchronously(this,0,1L);
        setGameRules();
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("猎人游戏插件已卸载");
    }
    private void eventRegister(List<Listener> listener){
        for (Listener listener1 : listener) {
            getServer().getPluginManager().registerEvents(listener1, this);
        }
    }
    private void setGameRules(){
        World world = Bukkit.getWorld("world");
        World worldNether = Bukkit.getWorld("world_nether");
        World theEnd = Bukkit.getWorld("world_the_end");
        world.setGameRuleValue("mobGriefing","true");
        worldNether.setGameRuleValue("mobGriefing","false");
        theEnd.setGameRuleValue("mobGriefing","true");
    }

    private void gameStart(){
        int a = Bukkit.getOnlinePlayers().size();
        if (a >= 3){
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "prepare");
        }
    }
}

