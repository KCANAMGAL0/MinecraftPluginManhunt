package org.manhunt.Brewing;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.manhunt.Main;
import org.manhunt.Utils.ItemStackUtil;
import org.manhunt.Utils.MathUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BrewingStandGUI {
    public static HashMap<Inventory,Long> BrewingTime;
    public static HashMap<Inventory, String> ResultPotionEffect;
    static {
        BrewingTime = new HashMap<>();
        ResultPotionEffect = new HashMap<>();
    }
    public int getBrewingTime(Inventory inventory) {
        if (BrewingTime.containsKey(inventory) && BrewingTime.get(inventory) > System.currentTimeMillis()){
            return (int) ((double) (BrewingTime.get(inventory) - System.currentTimeMillis()) / 1000);
        }else {
            return 0;
        }
    }
    public String getResultPotionEffect(Inventory inventory){
        return ResultPotionEffect.getOrDefault(inventory, "无");
    }

    public void setBrewingTime(Block block,Long SecondTime){
        if (block.getType() == Material.BREWING_STAND){
            BrewingTime.put(new BrewingManager().getBrewingGUI(block),System.currentTimeMillis() + SecondTime * 1000L);
        }
    }
    public void setResultPotionEffect(Block block,String potionEffect){
        if (block.getType() == Material.BREWING_STAND){
            ResultPotionEffect.put(new BrewingManager().getBrewingGUI(block),potionEffect);
        }
    }
    public List<Material> getMaterials(){
        List<Material> materials = new ArrayList<>();
        for (BrewingRecipes brewingRecipes : BrewingRecipes.values()){
            materials.add(brewingRecipes.getBrewingMaterial());
        }
        return materials;
    }
    public List<Material> getTier1Materials(){
        List<Material> materials = new ArrayList<>();
        for (BrewingRecipes brewingRecipes : BrewingRecipes.values()){
            if (brewingRecipes.getBrewingMaterial() != Material.GLOWSTONE_DUST && brewingRecipes.getBrewingMaterial() != Material.REDSTONE && brewingRecipes.getBrewingMaterial() != Material.NETHER_STALK)
                materials.add(brewingRecipes.getBrewingMaterial());
        }
        return materials;
    }
    public List<Material> getTier2Materials(){
        List<Material> materials = new ArrayList<>();
        materials.add(Material.GLOWSTONE_DUST);
        materials.add(Material.REDSTONE);
        return materials;
    }
    public List<Short> getUpgradablePotionData(){
        List<Short> PotionDatas = new ArrayList<>();
        PotionDatas.add(BrewingRecipes.SPEED1.getResultPotion().getDurability());
        PotionDatas.add(BrewingRecipes.STRENGTH1.getResultPotion().getDurability());
        PotionDatas.add(BrewingRecipes.REGENERATION1.getResultPotion().getDurability());
        PotionDatas.add(BrewingRecipes.RESISTANCE1.getResultPotion().getDurability());
        PotionDatas.add(BrewingRecipes.FIRERESISTANCE1.getResultPotion().getDurability());
        return PotionDatas;
    }
}

enum BrewingRecipes{
    AWKWARD("Awkward", new ItemStack(Material.POTION,1,(short) 16),Material.NETHER_STALK,10L),
    SPEED1("Speed1",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8194),new PotionEffect(PotionEffectType.SPEED,2 * 60 * 20,0)),Material.SUGAR,15L),
    SPEED2("Speed2",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8226),new PotionEffect(PotionEffectType.SPEED, 75 * 20,1)),Material.GLOWSTONE_DUST,20L),
    SPEED3("Speed3",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8258),new PotionEffect(PotionEffectType.SPEED,4 * 60 * 20,0)),Material.REDSTONE,20L),
    STRENGTH1("Strength1",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8201),new PotionEffect(PotionEffectType.INCREASE_DAMAGE,3 * 60 * 20,0)),Material.BLAZE_POWDER,15L),
    STRENGTH2("Strength2",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8265),new PotionEffect(PotionEffectType.INCREASE_DAMAGE,6 * 60 * 20,0)),Material.REDSTONE,20L),
    RESISTANCE1("Resistance1",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8238),new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40 * 20,0)),Material.IRON_BLOCK,15L),
    RESISTANCE2("Resistance2",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8270),new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 75 * 20,0)),Material.REDSTONE,20L),
    REGENERATION1("Regeneration1",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8193),new PotionEffect(PotionEffectType.REGENERATION,16 * 20,1)),Material.GHAST_TEAR,15L),
    REGENERATION2("Regeneration2",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8225),new PotionEffect(PotionEffectType.REGENERATION,10 * 20,2)),Material.GLOWSTONE_DUST,20L),
    REGENERATION3("Regeneration3",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8257),new PotionEffect(PotionEffectType.REGENERATION,30 * 20,1)),Material.REDSTONE,20L),
    //
    JumpBoost1("JumpBoost1",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8203),new PotionEffect(PotionEffectType.JUMP,40 * 20,1)),Material.RABBIT_FOOT,15L),
    JumpBoost2("JumpBoost2",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8235),new PotionEffect(PotionEffectType.JUMP,24 * 20,2)),Material.GLOWSTONE_DUST,20L),
    JumpBoost3("JumpBoost3",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8267),new PotionEffect(PotionEffectType.JUMP,75 * 20,1)),Material.REDSTONE,20L),
    //
    FIRERESISTANCE1("FireResistance1",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8227),new PotionEffect(PotionEffectType.FIRE_RESISTANCE,3 * 60 * 20,0)),Material.MAGMA_CREAM,15L),
    FIRERESISTANCE2("FireResistance2",ItemStackUtil.getNBTItemStack(new ItemStack(Material.POTION,1, (short) 8259),new PotionEffect(PotionEffectType.FIRE_RESISTANCE,6 * 60 * 20,0)),Material.REDSTONE,20L);
    private String ResultPotionEffectName;
    private ItemStack ResultPotion;
    private Material material;
    private long BrewingTime;
    public String getResultPotionEffectName(){
        return ResultPotionEffectName;
    }
    public ItemStack getResultPotion(){
        return ResultPotion;
    }
    public Material getBrewingMaterial(){
        return material;
    }
    public long getBrewingTime(){
        return BrewingTime;
    }

    BrewingRecipes(String ResultPotionEffectName, ItemStack potionEffect,Material material,Long BrewingTime){
        this.ResultPotionEffectName = ResultPotionEffectName;
        this.ResultPotion= potionEffect;
        this.material = material;
        this.BrewingTime = BrewingTime;
    }
}
