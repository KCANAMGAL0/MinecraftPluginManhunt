package org.manhunt.MhClass;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.manhunt.Main;
import org.manhunt.Utils.MathUtil;
import org.manhunt.player.actionbar.ActionbarManager;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;
import org.manhunt.player.team.attackOnPlayer;

import java.util.HashMap;

public class IronGolem extends MhClassManager {
    public static HashMap<Player,Long> StrongHitCD,IronPunchCD;
    public static HashMap<Player,Long> IronPunchVelocity;
    static {
        StrongHitCD = new HashMap<>();
        IronPunchCD = new HashMap<>();
        IronPunchVelocity = new HashMap<>();
    }
    public void IronPunch(Player player){
        if (IronPunchCD.containsKey(player) && IronPunchCD.get(player) > System.currentTimeMillis()) {
            return;
        }else {
            World world = player.getWorld();
            MhTeam mhTeam = new MhTeam();
            Location location1;
            IronPunchCD.put(player,System.currentTimeMillis() + 30 * 1000L);
            for (Player p : world.getPlayers()) {
                if (mhTeam.isHunter(p) && p.getLocation().distance(player.getLocation()) <= 4) {
                    new attackOnPlayer().setPlayerLastDamager(p,player);
                    p.damage(5);
                    if (super.getPlayerClass(p).equals(MhClasses.Doc.getName())) new Doctor().addDamage(p,5);
                    new playerManager().addEffect(p, PotionEffectType.BLINDNESS, 40, 0, false);
                    IronPunchVelocity.put(p,System.currentTimeMillis() + 2L);
                    p.setLastDamageCause(null);
                    p.setVelocity(new Vector(0,1,0));
                }
            }
            for (Location location : MathUtil.getCircle(4, player.getLocation(), 2d)) { //特效
                location1 = new Location(world, location.getX(), location.getY() + 2, location.getZ());
                ArmorStand armorStand = (ArmorStand) world.spawnEntity(location1, EntityType.ARMOR_STAND);
                armorStand.setMarker(true);
                armorStand.setVisible(false);
                armorStand.setHelmet(new ItemStack(Material.IRON_BLOCK, 1));
                armorStand.setSmall(true);
                world.playSound(player.getLocation(), Sound.ANVIL_LAND, 2.0F, 2.0F);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        armorStand.remove();
                    }
                }.runTaskLater(Main.getPlugin(Main.class), 9L);
            }

            player.sendMessage(ChatColor.GREEN + "你的钢铁之击技能已激活");

        }
    }
    public void StrongHit(Player player , Player victim, EntityDamageByEntityEvent e){
        if (player.isSneaking()){
            if (StrongHitCD.containsKey(player) && StrongHitCD.get(player) > System.currentTimeMillis()){
                return;
            }else {
                StrongHitCD.put(player,System.currentTimeMillis() + 10 * 1000L);
                victim.setLastDamageCause(null);
                victim.setVelocity(new Vector(0,1,0));
                player.sendMessage(ChatColor.GREEN + "你的击飞技能已激活");
                player.getWorld().playSound(player.getLocation(),Sound.IRONGOLEM_HIT,2.0F,1.0F);
            }
        }
    }

    public void Display(Player player){
        ActionbarManager actionbarManager = new ActionbarManager();
        String color = ChatColor.WHITE + ChatColor.BOLD.toString();
        if (IronPunchCD.containsKey(player) && IronPunchCD.get(player) > System.currentTimeMillis()){
            String x = (IronPunchCD.get(player) - System.currentTimeMillis()) / 1000 + "秒 ";
            if (StrongHitCD.containsKey(player) && StrongHitCD.get(player) > System.currentTimeMillis()){
                String x1 = (StrongHitCD.get(player) - System.currentTimeMillis()) / 1000 + "秒 ";
                actionbarManager.sendActionbar(player,color + "钢铁之击 " + ChatColor.GRAY + x,color + "击飞 " + ChatColor.GRAY + x1);
            }else {
                actionbarManager.sendActionbar(player,color + "钢铁之击 " + ChatColor.GRAY + x,color + "击飞 " + ChatColor.GREEN + ChatColor.BOLD + "✔ ");
            }
        }else {
            if (StrongHitCD.containsKey(player) && StrongHitCD.get(player) > System.currentTimeMillis()){
                String x1 = (StrongHitCD.get(player) - System.currentTimeMillis()) / 1000 + "秒 ";
                actionbarManager.sendActionbar(player,color + "钢铁之击 " + ChatColor.GREEN + ChatColor.BOLD + "✔ ",color + "击飞 " + ChatColor.GRAY + x1);
            }else {
                actionbarManager.sendActionbar(player,color + "钢铁之击 " + ChatColor.GREEN + ChatColor.BOLD + "✔ ",color + "击飞 " + ChatColor.GREEN + ChatColor.BOLD + "✔ ");
            }
        }
    }
}
