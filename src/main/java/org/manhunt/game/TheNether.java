package org.manhunt.game;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftWither;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.manhunt.Main;
import org.manhunt.Utils.BossbarUtil;
import org.manhunt.Utils.MathUtil;
import org.manhunt.buliding.SpawnLocation;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;

import java.util.HashMap;
import java.util.Random;

public class TheNether implements Listener {
    static World theNether = Bukkit.getWorld("world_nether");
    public boolean EnderCrystalBoost = false;
    public boolean ReduceEnderDragonHP = false;
    public int RunnableTimer = 0;
    public int a = 6;
    public boolean WitherSpawned = false;
    private static final Location HspawnLocation = new Location(theNether,24,42,64);
    private static final Location RspawnLocation = new Location(theNether,52,41,-39);
    static Plugin plugin = Main.getPlugin(Main.class);
    public static final Location MiddleLocation = new Location(theNether,0,36,0);
    private static final Location[] Spawners = new Location[]{new Location(theNether,30,34,-5),new Location(theNether,38,41,26)};
    private static final Location GhastSpawner = new Location(theNether,80,64,0);
    private static final Location MainWorldTeleportLocation = new Location(Bukkit.getWorld("world"), plugin.getConfig().getIntegerList("NetherToWorldLocation").get(0),plugin.getConfig().getIntegerList("NetherToWorldLocation").get(1),plugin.getConfig().getIntegerList("NetherToWorldLocation").get(2));
    public static Location getMainWorldTeleportLocation(){
        return MainWorldTeleportLocation;
    }

    public boolean RunnerEnterNether;
    public static Location getHspawnLocation(){
        return HspawnLocation;
    }
    public static Location getRspawnLocation(){
        return RspawnLocation;
    }

    public static HashMap<Block,Player> playerBlockHashMap;
    static {
        playerBlockHashMap = new HashMap<>();
    }
    @EventHandler
    public void a(PlayerChangedWorldEvent e){
        theNether.getWorldBorder().setCenter(0,0);
        theNether.getWorldBorder().setSize(149);
        Player player = e.getPlayer();
        MhTeam mhTeam = new MhTeam();
        World To = player.getWorld();
        World From = e.getFrom();
        playerManager pM = new playerManager();
        if (From.getName().equals("world") && To.equals(theNether)){
            player.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "你进入了地狱",null);
            pM.addEffect(player, PotionEffectType.FIRE_RESISTANCE,10 * 20,0,false);
            if (mhTeam.isHunter(player)) {
                player.teleport(HspawnLocation, PlayerTeleportEvent.TeleportCause.NETHER_PORTAL);
            } else if (mhTeam.isRunner(player)){
                player.teleport(RspawnLocation, PlayerTeleportEvent.TeleportCause.NETHER_PORTAL);
                if (!RunnerEnterNether) {
                    RunnerEnterNether = true;
                    Bukkit.getWorld("world").dropItem(new SpawnLocation().ArraytoLocation(new SpawnLocation().getHunterSpawnLocation()),new ItemStack(Material.OBSIDIAN,10));
                    for (Player player1 : Bukkit.getOnlinePlayers()){
                        showWitherSpawnBossbar();
                        player1.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "勇者进入了地狱,凋灵将在1分钟后生成!");
                        if (mhTeam.isHunter(player1)){
                            player1.sendMessage(ChatColor.RED + "10块黑曜石已掉落在出生点!");
                        }
                        player1.playSound(player1.getLocation(),Sound.ENDERMAN_TELEPORT,1.0F,1.0F);
                        player1.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "勇者进入了地狱",ChatColor.GRAY + ChatColor.BOLD.toString()+  "凋灵将在1分钟后生成");
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (RunnableTimer == 60) {
                                if (!WitherSpawned) {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.playSound(player1.getLocation(), Sound.WITHER_SPAWN, 1.0F, 1.0F);
                                        player1.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "凋灵已在地狱中心生成!击败以获取补给和增益!");
                                        if (mhTeam.isHunter(player1)) {
                                            player1.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "凋灵已在地狱中心生成", ChatColor.GRAY + ChatColor.BOLD.toString() + "杀死凋灵可增强末影水晶的恢复效果");
                                        } else if (mhTeam.isRunner(player1))
                                            player1.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "凋灵已在地狱中心生成", ChatColor.GRAY + ChatColor.BOLD.toString() + "杀死凋灵可减少末影龙的血量");
                                    }
                                    theNether.loadChunk((int) MiddleLocation.getX(), (int) MiddleLocation.getZ());

                                    Location MiddleLocation0 = new Location(theNether, MiddleLocation.getX() + 0.5, MiddleLocation.getY() + 0.5, MiddleLocation.getZ() + 0.5);
                                    WitherSpawned = true;
                                    Wither wither = (Wither) theNether.spawnEntity(MiddleLocation0, EntityType.WITHER);
                                    wither.setMaxHealth(wither.getHealth() * 1.5);
                                    wither.setHealth(wither.getMaxHealth());
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            if (!wither.isDead()) wither.teleport(MiddleLocation0);
                                            else this.cancel();
                                        }
                                    }.runTaskTimer(plugin, 0L, 1L);
                                }
                            }else if (RunnableTimer == 31 || RunnableTimer == 51){
                                Bukkit.getOnlinePlayers().forEach(player1 -> player1.sendTitle(getWitherSpawnMessage(),null));
                            }
                            if (RunnableTimer % 60 == 0) theNether.spawnEntity(GhastSpawner, EntityType.GHAST);
                            RunnableTimer++;
                        }
                    }.runTaskTimer(plugin, 0L, 20L);
                }
            } else {
                player.teleport(RspawnLocation);
            }
        }else if (From.equals(theNether) && To.getName().equals("world")){
            player.teleport(MainWorldTeleportLocation, PlayerTeleportEvent.TeleportCause.NETHER_PORTAL);

            pM.addEffect(player, PotionEffectType.FIRE_RESISTANCE,10 * 20,0,false);
        }
    }
    @EventHandler
    public void b(BlockBreakEvent e){
        if (!e.isCancelled()){
            if (e.getBlock().getWorld().equals(theNether)){
                Block block = e.getBlock();
                if (block.getLocation().distance(MiddleLocation) < 15){
                    if (playerBlockHashMap.containsKey(block)){
                        return;
                    }else {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void c(BlockPlaceEvent e){
        if (!e.isCancelled()){
            if (e.getBlock().getWorld().equals(theNether)){
                Block block = e.getBlock();
                Location location = block.getLocation();
                if (block.getLocation().distance(MiddleLocation) < 15) {
                    if (block.getLocation().distance(MiddleLocation) >= 5) {
                        playerBlockHashMap.put(block, e.getPlayer());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Block block1 = location.getBlock();
                                if (playerBlockHashMap.containsKey(block) && playerBlockHashMap.get(block).equals(e.getPlayer())) {
                                    if (block1.getType() == block.getType()) {
                                        block.setType(Material.AIR);
                                    }
                                }
                            }
                        }.runTaskLater(plugin, 15 * 20L);
                    } else {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void d(EntityDamageByEntityEvent e){
        if (!e.isCancelled() && e.getEntity() instanceof Player){
            playerManager pM = new playerManager();
            Player player = (Player) e.getEntity();
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION){
                if (e.getDamager() instanceof WitherSkull){
                    pM.addEffect(player,PotionEffectType.WITHER,3 * 20,2,false);
                }
            }else if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
                if (e.getDamager() instanceof WitherSkull){
                    pM.addEffect(player,PotionEffectType.WITHER,3 * 20,2,false);
                }
            }
        }
    }
    @EventHandler
    public void e(EntityDeathEvent e){
        if (e.getEntity() instanceof Wither){
            MhTeam mhTeam = new MhTeam();
            Player player = e.getEntity().getKiller();

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (a == 0){
                        ItemStack itemStack = new ItemStack(Material.BOW,1);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.addEnchant(Enchantment.ARROW_INFINITE,1,true);
                        itemMeta.addEnchant(Enchantment.ARROW_DAMAGE,3,true);
                        itemStack.setItemMeta(itemMeta);
                        theNether.dropItem(new Location(theNether,MiddleLocation.getX(),MiddleLocation.getY() + 3,MiddleLocation.getZ()),itemStack);
                        this.cancel();
                    }else {
                        Random random = new Random();
                        theNether.dropItem(new Location(theNether,MiddleLocation.getX(),MiddleLocation.getY() + 3,MiddleLocation.getZ()),new ItemStack(Material.GOLDEN_APPLE,1));
                        theNether.dropItem(new Location(theNether,MiddleLocation.getX(),MiddleLocation.getY() + 3,MiddleLocation.getZ()),new ItemStack(Material.POTION,1,(short) 16));
                        ExperienceOrb entity = (ExperienceOrb) theNether.spawnEntity(new Location(theNether,MiddleLocation.getX(),MiddleLocation.getY() + 3,MiddleLocation.getZ()),EntityType.EXPERIENCE_ORB);
                        entity.setExperience(5 + random.nextInt(5));
                        if (random.nextInt(2) == 1){
                            theNether.dropItem(new Location(theNether,MiddleLocation.getX(),MiddleLocation.getY() + 3,MiddleLocation.getZ()),new ItemStack(Material.ARROW,5));
                        }
                        a--;
                    }
                }
            }.runTaskTimer(plugin,0L,20L);
            if (mhTeam.isHunter(player)) {
                EnderCrystalBoost = true;
                for (Player player1 : Bukkit.getOnlinePlayers()){
                    player1.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "猎人给予了凋灵致命一击!",ChatColor.GRAY + "本局末影水晶的治疗效果增强25%");
                    player1.sendMessage(ChatColor.RED + "猎人" + ChatColor.YELLOW + "给予了凋灵致命一击!他们将获得" + ChatColor.RED + "20秒生命恢复II,速度II" + ChatColor.YELLOW + ChatColor.BOLD + "本局末影水晶的治疗效果增强25%");
                }
                for (Player player2 : mhTeam.getHunterPlayers()){
                    new playerManager().addEffect(player2,PotionEffectType.SPEED,20 * 20,1,false);
                    new playerManager().addEffect(player2,PotionEffectType.REGENERATION,20 * 20,1,false);
                }
            }else if (mhTeam.isRunner(player)){
                EnderCrystalBoost = false;
                ReduceEnderDragonHP = true;
                for (Player player1 : Bukkit.getOnlinePlayers()){
                    player1.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "猎人给予了凋灵致命一击!",ChatColor.GRAY + "本局末影龙最大生命值减少10%");
                    player1.sendMessage(ChatColor.GREEN + "勇者" + ChatColor.YELLOW + "给予了凋灵致命一击!他们将获得" + ChatColor.RED + "60秒生命恢复I,伤害吸收V" + ChatColor.YELLOW + "(10❤)" + ChatColor.YELLOW + ChatColor.BOLD + "本局末影龙最大生命值减少10%");
                }
                for (Player player2 : mhTeam.getRunnerPlayers()){
                    new playerManager().addEffect(player2,PotionEffectType.REGENERATION,60 * 20,0,false);
                    new playerManager().addEffect(player2,PotionEffectType.ABSORPTION,60 * 20,4,false);
                }
            }
        }else if (e.getEntity() instanceof Ghast){
            e.getEntity().getKiller().getInventory().addItem(new ItemStack(Material.GHAST_TEAR,1));
            e.getEntity().getKiller().giveExp(5);
            e.getEntity().getKiller().playSound(e.getEntity().getKiller().getLocation(),Sound.ORB_PICKUP,1.0F,1.0F);
        }else if (e.getEntity() instanceof Blaze){
            if (new Random().nextInt(2) == 1)
                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.SLIME_BALL));
        }
    }
    @EventHandler
    public void f(EntityDamageByEntityEvent e){
        if (!e.isCancelled()){
            if (e.getDamager() instanceof Player){
                Player player = (Player) e.getDamager();
                if (e.getEntity() instanceof Wither || e.getEntity() instanceof Blaze){
                    if (new playerManager().hasEffect(player,PotionEffectType.INCREASE_DAMAGE,0)){
                        e.setDamage(e.getDamage() * 0.5652);
                    }
                }
            }
        }
    }
    private void showWitherSpawnBossbar(){
        BossbarUtil bossbarUtil = new BossbarUtil();
        for (Player player : Bukkit.getOnlinePlayers()){
            bossbarUtil.removeBossbar(player);
            bossbarUtil.addBossbar(player,getWitherSpawnMessage());
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        if (RunnableTimer <= 60) {
                            bossbarUtil.updateBossbar(player, getWitherSpawnMessage());
                        }else {
                            bossbarUtil.removeBossbar(player);
                            this.cancel();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }.runTaskTimer(plugin,0L,1L);
        }
    }
    private String getWitherSpawnMessage(){
        return ChatColor.RED + ChatColor.BOLD.toString() + "凋灵将在" + (61 - RunnableTimer) + "秒后生成";
    }
    @EventHandler
    public void f(BlockBreakEvent e){
        if (e.getBlock().getType() == Material.MOB_SPAWNER && !e.isCancelled()){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void e(PlayerInteractEvent e){
        if (e.getPlayer().getItemInHand().getType() == Material.LAVA_BUCKET && e.getAction() == Action.RIGHT_CLICK_BLOCK && e .getPlayer().getWorld().equals(theNether)){
            Block block = e.getClickedBlock();
            if (block.getLocation().distance(MiddleLocation) <= 16)
                e.setCancelled(true);
        }
    }
}
