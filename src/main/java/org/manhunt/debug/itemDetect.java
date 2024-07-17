package org.manhunt.debug;

import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.manhunt.Main;
import org.manhunt.MhClass.MhClassManager;
import org.manhunt.Utils.MathUtil;
import org.manhunt.player.nametag.NameTagManager;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;

import javax.naming.Name;

public class itemDetect implements Listener {
    /*
    @EventHandler
    public void a(PlayerMoveEvent e){
        Player player = e.getPlayer();
        if (player.getItemInHand().getType() == Material.STONE) {
            Location location = player.getLocation().add(0, -1, 0);
            location.getBlock().setType(Material.COBBLESTONE);
        }
    }

     */
    @EventHandler
    public void a(PlayerInteractEvent e){
        if (e.getAction() == Action.LEFT_CLICK_AIR){
            e.getPlayer().sendMessage(String.valueOf(e.getPlayer().getItemInHand().getType()));
        }
    }
}
