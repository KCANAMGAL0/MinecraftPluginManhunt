package org.manhunt.entity;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.manhunt.player.team.MhTeam;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.manhunt.entity.CustomZombie.getPrivateField;

public class CustomZombie extends EntityZombie implements Listener {
    public static HashMap<Zombie,String> specialZombie;
    static {
        specialZombie = new HashMap<>();
    }
    public CustomZombie(org.bukkit.World world) {
        super(((CraftWorld) world).getHandle());
        this.setBaby(false);
        List<?> goalB = (List<?>) getPrivateField("b", PathfinderGoalSelector.class,goalSelector); goalB.clear();
        List<?> goalC = (List<?>) getPrivateField("c", PathfinderGoalSelector.class,goalSelector); goalC.clear();
        List<?> targetB = (List<?>) getPrivateField("b", PathfinderGoalSelector.class,targetSelector); targetB.clear();
        List<?> targetC = (List<?>) getPrivateField("c", PathfinderGoalSelector.class,targetSelector); targetC.clear();
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityIronGolem.class, 1.0D, false));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, false, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityIronGolem.class, false, false));
        this.setInvisible(false);

        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.345);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(1024);
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(40);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(6);
        this.setHealth(this.getMaxHealth());
        specialZombie.put((Zombie) this.getBukkitEntity(),"SPECIAL");
        ((Zombie) this.getBukkitEntity()).getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
        ((Zombie) this.getBukkitEntity()).getEquipment().setHelmetDropChance(0);

    }
    public static Object getPrivateField(String name,Class<?> clazz,Object object){
        Field field;
        Object o = null;
        try {
            field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            o = field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return o;
    }



}

