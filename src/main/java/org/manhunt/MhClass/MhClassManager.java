package org.manhunt.MhClass;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.manhunt.Main;
import org.manhunt.Utils.ItemStackUtil;
import org.manhunt.player.actionbar.ActionbarManager;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;

import java.util.HashMap;
import java.util.Objects;

public class MhClassManager implements Listener {
    public static HashMap<Player,String> PlayerClassInfo;
    static {
        PlayerClassInfo = new HashMap<>();
    }
    public String getPlayerClass(Player player){
        return PlayerClassInfo.get(player);
    }
    public void resetPlayerClass(Player player){
        PlayerClassInfo.put(player,"unknown");
    }
    public void setPlayerClassInfo(Player player,String MhClass){
        PlayerClassInfo.put(player,MhClass);
    }

    public String[] getMhClasses(){
        return new String[]{"Miner","Pal","Vam","Doc","Arc","Esc","Lng","Cac","Sac","Irg","Ass","unknown","Gua","Rob","MM"};
    }

    public void SkillDisplay(){
        for (Player player : Bukkit.getOnlinePlayers()){
            if (player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK) > 1){
                ItemStack itemStack = player.getItemInHand();
                itemStack.removeEnchantment(Enchantment.ARROW_KNOCKBACK);
                itemStack.addEnchantment(Enchantment.ARROW_KNOCKBACK,1);
                player.setItemInHand(new ItemStack(Material.AIR));
                player.setItemInHand(itemStack);
            }
            if (player.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK) > 1){
                ItemStack itemStack = player.getItemInHand();
                itemStack.removeEnchantment(Enchantment.KNOCKBACK);
                itemStack.addEnchantment(Enchantment.KNOCKBACK,1);
                player.setItemInHand(new ItemStack(Material.AIR));
                player.setItemInHand(itemStack);
            }
            if (!ItemStackUtil.isEmpty(player.getEquipment().getHelmet()) && player.getEquipment().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 2){
                ItemStack itemStack = player.getEquipment().getHelmet();
                itemStack.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
                itemStack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2);
                player.getEquipment().setHelmet(itemStack);
            }
            if (!ItemStackUtil.isEmpty(player.getEquipment().getChestplate()) && player.getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 2){
                ItemStack itemStack = player.getEquipment().getChestplate();
                itemStack.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
                itemStack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2);
                player.getEquipment().setChestplate(itemStack);
            }
            if (!ItemStackUtil.isEmpty(player.getEquipment().getLeggings()) && player.getEquipment().getLeggings().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 2){
                ItemStack itemStack = player.getEquipment().getLeggings();
                itemStack.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
                itemStack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2);
                player.getEquipment().setLeggings(itemStack);
            }
            if (!ItemStackUtil.isEmpty(player.getEquipment().getBoots()) && player.getEquipment().getBoots().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 2){
                ItemStack itemStack = player.getEquipment().getBoots();
                itemStack.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
                itemStack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2);
                player.getEquipment().setBoots(itemStack);
            }

            if (!player.getItemInHand().getType().equals(Material.COMPASS)) {
                switch (getPlayerClass(player)){
                    case "Vam":
                        new Vampire().VampireSkillDisplay(player);
                        break;
                    case "Doc":
                        new Doctor().RegenSkillDisplay(player);
                        break;
                    case "Esc":
                        new EvilScorcher().LavaBucketSkillDisplay(player);
                        break;
                    case "Lng":
                        new LightningGod().SkillDisplay(player);
                        break;
                    case "Cac":
                        new Cactus().CacDisplay(player);
                        break;
                    case "Pal":
                        new Paladin().SkillDisplay(player);
                        break;
                    case "Sac":
                        new Sacrifice().Display(player);
                        break;
                    case "Irg":
                        new IronGolem().Display(player);
                        break;
                    case "Ass":
                        new Assassin().Display(player);
                        break;
                    case "Gua":
                        new Guardian().Display(player);
                        break;
                    case "Rob":
                        new Robber().Display(player);
                        break;
                    case "MM":
                        new MachineMaster().Display(player);
                        break;
                    default: break;
                }
            }
        }
    }

    public String getClassStringName(String className) {
        for (MhClasses s : MhClasses.values()){
            if (className.equals(s.getName())){
                return s.getDisplayName();
            }
        }
        return "unknown";
    }

    @EventHandler
    public void a(EntityDamageByEntityEvent e){
        playerManager pM = new playerManager();
        MhTeam mhTeam = new MhTeam();
        if (!e.isCancelled() && e.getEntity() instanceof Player){
            Player victim = (Player) e.getEntity();
            /*Player victim = (Player) e.getEntity();
            for (int i = 0 ; i <= 255 ; i++) {
                if (pM.hasEffect(attacker, PotionEffectType.INCREASE_DAMAGE, i)) {
                    e.setDamage((e.getDamage() / (1 + (1.3 * (i + 1)) )) * (1 + (0.3 * (i + 1)))); //力量 增加30%x力量等级伤害
                }
            }
             */
            if (e.getDamager() instanceof Player){
                Player attacker = (Player) e.getDamager();
                // DAMAGE MANAGER处理普通伤害
                String attackerClass = getPlayerClass(attacker);
                String victimClass = getPlayerClass(victim);
                boolean isOnDamageTicks = !((double) victim.getNoDamageTicks() > (double)victim.getMaximumNoDamageTicks() / 2.0);
                if (attackerClass.equals(MhClasses.Doc.getName())){
                    if (pM.hasEffect(attacker,PotionEffectType.INCREASE_DAMAGE,0)) e.setDamage(e.getDamage() * 0.5652);
                }else if (attackerClass.equals(MhClasses.Esc.getName())){
                    new EvilScorcher().BurnSkill(attacker,e,victim);
                }else if (attackerClass.equals(MhClasses.Lng.getName())){
                    if (isOnDamageTicks) new LightningGod().Skill0(attacker, e, victim);
                }else if (attackerClass.equals(MhClasses.Cac.getName())){
                    if (isOnDamageTicks){
                        if (Cactus.ForwardCD.containsKey(attacker) && Cactus.ForwardCD.get(attacker) > System.currentTimeMillis()) {
                        } else {
                            Cactus.ForwardCD0.put(attacker, System.currentTimeMillis() + 500L);
                        }
                        Cactus.ForwardCD1.put(attacker, false);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (Cactus.ForwardCD1.containsKey(attacker) && !Cactus.ForwardCD1.get(attacker)) {
                                    Cactus.ForwardCD.put(attacker, System.currentTimeMillis() + 5L);
                                    Cactus.ForwardCD1.put(attacker, false);
                                }
                            }
                        }.runTaskLater(Main.getPlugin(Main.class), 10L);
                    }
                }else if (attackerClass.equals(MhClasses.Sac.getName())){
                    if (pM.hasEffect(attacker,PotionEffectType.INCREASE_DAMAGE,0)) e.setDamage(e.getDamage() * 0.5652);
                }else if (attackerClass.equals(MhClasses.Gua.getName())){
                    if (pM.hasEffect(attacker,PotionEffectType.INCREASE_DAMAGE,0)) e.setDamage(e.getDamage() * 0.5652);
                    if (isOnDamageTicks) new Guardian().GuardRay(attacker,5);
                } else if (attackerClass.equals(MhClasses.Miner.getName())){
                    if (victimClass.equals(MhClasses.Cac.getName())){
                        new Cactus().GetMoreDamage(attacker,victim,e);
                    }else if (pM.hasEffect(attacker, PotionEffectType.INCREASE_DAMAGE, 0))
                        e.setDamage(e.getDamage() * 0.5652);
                }else if (attackerClass.equals(MhClasses.Vam.getName())){
                    if (isOnDamageTicks) new Vampire().BloodingSkill(attacker,victim,e);
                }else if (attackerClass.equals(MhClasses.Arc.getName())){
                    if (victimClass.equals(MhClasses.Cac.getName())){
                        new Cactus().GetMoreDamage(attacker,victim,e);
                    }else if (pM.hasEffect(attacker, PotionEffectType.INCREASE_DAMAGE, 0))
                        e.setDamage(e.getDamage() * 0.5652);
                }else if (attackerClass.equals(MhClasses.Pal.getName())){
                    if (victimClass.equals(MhClasses.Cac.getName())){
                        new Cactus().GetMoreDamage(attacker,victim,e);
                    }else if (pM.hasEffect(attacker, PotionEffectType.INCREASE_DAMAGE, 0))
                        e.setDamage(e.getDamage() * 0.5652);
                }else if (attackerClass.equals(MhClasses.Irg.getName())){
                    if (isOnDamageTicks) new IronGolem().StrongHit(attacker,victim,e);
                }else if (attackerClass.equals(MhClasses.Ass.getName())){
                    new Assassin().backHit(attacker,victim,e);
                }else if (attackerClass.equals(MhClasses.Rob.getName())){
                    if (pM.hasEffect(attacker, PotionEffectType.INCREASE_DAMAGE, 0)) e.setDamage(e.getDamage() * 0.5652);
                    if (isOnDamageTicks) new Robber().Melee(attacker);
                }
                /*
                if (!((double) victim.getNoDamageTicks() > (double)victim.getMaximumNoDamageTicks() / 2.0)) {

                    if (pM.hasEffect(attacker,PotionEffectType.INCREASE_DAMAGE,0)) e.setDamage(e.getDamage() * 0.5652);
                    if (mhTeam.isHunter(attacker) && mhTeam.isRunner(victim)) {
                        if (getPlayerClass(attacker).equals("Esc")) {
                            new EvilScorcher().BurnSkill(attacker, e,victim);
                        } else if (getPlayerClass(attacker).equals("Lng")) {
                            new LightningGod().Skill0(attacker, e, victim);
                        } else if (getPlayerClass(attacker).equals("Cac")) {
                            if (new playerManager().hasEffect(attacker,PotionEffectType.INCREASE_DAMAGE,0)) e.setDamage(e.getDamage() * 0.5652);
                            if (Cactus.ForwardCD.containsKey(attacker) && Cactus.ForwardCD.get(attacker) > System.currentTimeMillis()) {

                            } else {
                                Cactus.ForwardCD0.put(attacker, System.currentTimeMillis() + 500L);
                            }
                            Cactus.ForwardCD1.put(attacker, false);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (Cactus.ForwardCD1.containsKey(attacker) && !Cactus.ForwardCD1.get(attacker)) {
                                        Cactus.ForwardCD.put(attacker, System.currentTimeMillis() + 5L);
                                        Cactus.ForwardCD1.put(attacker, false);
                                    }
                                }
                            }.runTaskLater(Main.getPlugin(Main.class), 10L);
                        }
                    } else if (mhTeam.isHunter(victim) && mhTeam.isRunner(attacker)) {
                        if (getPlayerClass(victim).equals("Doc")) {
                            new Doctor().RegenerationSkill(victim, e,attacker);
                        } else if (getPlayerClass(victim).equals("Cac")) {
                            new Cactus().ThornsSkill(attacker, victim, e);
                            new Cactus().GetMoreDamage(attacker, e);
                        }
                        if (getPlayerClass(attacker).equals("Vam")) {
                            new Vampire().BloodingSkill(attacker, victim,e);
                        }else if (getPlayerClass(attacker).equals("Irg")) {
                            new IronGolem().StrongHit(attacker,victim,e);
                        }
                    }

                }*/
                if (victimClass.equals(MhClasses.Doc.getName())){
                    new Doctor().RegenerationSkill(victim,e,attacker);
                }else if (victimClass.equals(MhClasses.Cac.getName())){
                    new Cactus().ThornsSkill(attacker,victim,e);
                }else if (victimClass.equals(MhClasses.Gua.getName())){
                    if (victim.getHealth() < 10){
                        new Guardian().Neuron(victim);
                    }
                }
            }else if (e.getDamager() instanceof Arrow){
                Player attacker = (Player) ((Arrow) e.getDamager()).getShooter();
                if (getPlayerClass(attacker).equals("Arc")) {
                    new Archer().SuperArrowSkill(attacker,victim,e);
                }else if (getPlayerClass(attacker).equals(MhClasses.Gua.getName())) {
                    new Guardian().GuardRay(attacker, 10);
                }else if (getPlayerClass(attacker).equals(MhClasses.Rob.getName())) {
                    new Robber().Arrow(attacker,victim);
                }
                if (getPlayerClass(victim).equals(MhClasses.Gua.getName())){
                    if (victim.getHealth() < 10){
                        new Guardian().Neuron(victim);
                    }
                }else if (getPlayerClass(victim).equals(MhClasses.Doc.getName())){
                    new Doctor().addDamage(victim,e.getFinalDamage());
                }
            }
        }
    }
    @EventHandler
    public void Skill2(EntityDamageByEntityEvent e){
        if (!e.isCancelled() && e.getDamager() instanceof Projectile && e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (getPlayerClass(player).equals(MhClasses.Pal.getName())) {
                playerManager pM = new playerManager();
                e.setDamage(e.getDamage() * 0.75);
                pM.addEffect(player, PotionEffectType.DAMAGE_RESISTANCE, 60, 0, false);
                player.getWorld().playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 2.0F);
            }
        }
    }

    public boolean isSkillOnCD(HashMap<Player,Long> hashMap,Player player){
        if (hashMap.containsKey(player) && hashMap.get(player) > System.currentTimeMillis()){
            return false;
        }else {
            return true;
        }
    }
}
