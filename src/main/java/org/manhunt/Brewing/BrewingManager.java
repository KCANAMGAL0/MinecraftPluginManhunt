package org.manhunt.Brewing;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.manhunt.Main;
import org.manhunt.Utils.ItemStackUtil;
import org.manhunt.Utils.MathUtil;

import javax.print.attribute.standard.MediaSize;
import java.sql.Struct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BrewingManager implements Listener {
    public int BrewingMaterial = 13,BrewingAnimation1 = 22;
    public int[] BrewingAnimation2 = new int[]{21,23,31};
    public int[] BrewingAnimation3 = new int[]{20,24};
    public int[] BrewingAnimation4 = new int[]{29,33};
    public int[] BrewingResultSlot = new int[]{38,40,42};
    public int[] BrewingAnimations = new int[]{22,21,23,31,20,24,29,33};
    Plugin plugin = Main.getPlugin(Main.class);
    final String Title = ChatColor.GOLD + ChatColor.BOLD.toString() + "酿造台";
    public static HashMap<Block,Inventory> MhBrewingStand;
    public static HashMap<Block, BukkitRunnable> MhBrewingStandManager;

    static {
        MhBrewingStand = new HashMap<>();
        MhBrewingStandManager = new HashMap<>();
    }
    @EventHandler
    public void a(PlayerInteractEvent e){
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType().equals(Material.BREWING_STAND)){
            e.setCancelled(true);
            Block block = e.getClickedBlock();
            openBrewingStandGUI(e.getPlayer(),block);
            BrewingStandGUI brewingStandGUI = new BrewingStandGUI();
            if (!MhBrewingStandManager.containsKey(block)) {
                new BukkitRunnable() { //炼药台主线程
                    @Override
                    public void run() {
                        if (e.getPlayer().getOpenInventory().getTitle().equals(Title)) {
                            Inventory inventory = e.getPlayer().getOpenInventory().getTopInventory();
                            if (inventory.equals(MhBrewingStand.get(block))) {
                                MhBrewingStandManager.put(block, this);
                                String b = "";
                                if (brewingStandGUI.getBrewingTime(inventory) == 0) {
                                    b = "没有正在进行的酿造";
                                } else {
                                    b = brewingStandGUI.getBrewingTime(inventory) + "秒";
                                }

                                int c = brewingStandGUI.getBrewingTime(inventory);

                                if (!ItemStackUtil.isEmpty(inventory, 13)) {
                                    String t = "无";
                                    long time = 0L;
                                    if (isMaterialOn(inventory) && isPotionOn(inventory)) {
                                        for (BrewingRecipes bR : BrewingRecipes.values()){
                                            if (inventory.getItem(BrewingMaterial).getType() == bR.getBrewingMaterial()){
                                                t = bR.getResultPotionEffectName();
                                                time = bR.getBrewingTime();
                                            }
                                        }
                                        if (c == 30 || c == 25 ||c == 20 || c == 15 || c == 10 || c == 5) {
                                            playBrewingAnimation1(inventory, b);
                                        } else if (c == 29 || c == 24|| c == 19 || c == 14 || c == 9 || c == 4) {
                                            playBrewingAnimation2(inventory, b);
                                        } else if (c == 28 || c == 23 || c == 18 || c == 13 || c == 8 || c == 3) {
                                            playBrewingAnimation3(inventory, b);
                                        } else if (c == 27 || c == 22 || c == 17 || c == 12 || c == 7 || c == 2) {
                                            playBrewingAnimation4(inventory, b);
                                        } else if (c == 26 || c == 21 || c == 16 || c == 11 || c == 6 || c == 1){
                                            playBrewingAnimation5(inventory, b);
                                        }
                                        //debug
                                        //e.getPlayer().sendMessage(t);
                                        if (brewingStandGUI.getBrewingTime(inventory) == 0) {
                                            playBrewingAnimation6(inventory, b);
                                            if (brewingStandGUI.getResultPotionEffect(inventory).equals(t) && !brewingStandGUI.getResultPotionEffect(inventory).equals("无")) {
                                                setResultPotion(inventory);
                                                if (inventory.getItem(BrewingMaterial).getAmount() == 1) {
                                                    inventory.setItem(BrewingMaterial, new ItemStack(Material.AIR));
                                                } else {
                                                    inventory.setItem(BrewingMaterial, new ItemStack(inventory.getItem(13).getType(), inventory.getItem(13).getAmount() - 1));
                                                }
                                                brewingStandGUI.setResultPotionEffect(block, "无");
                                            } else {
                                                brewingStandGUI.setBrewingTime(block, time);
                                                brewingStandGUI.setResultPotionEffect(block, t);
                                            }
                                        }
                                    } else {
                                        brewingStandGUI.setResultPotionEffect(block, "无");
                                        brewingStandGUI.setBrewingTime(block, 0L);
                                        playBrewingAnimation6(inventory, b);
                                    }
                                }else {
                                    playBrewingAnimation6(inventory,b);
                                    brewingStandGUI.setResultPotionEffect(block, "无");
                                    brewingStandGUI.setBrewingTime(block, 0L);
                                    playBrewingAnimation6(inventory, b);
                                }
                            }
                        }
                    }
                    private void playBrewingAnimation1(Inventory inventory,String b){
                        for (int x : BrewingAnimations) inventory.setItem(x,ItemStackUtil.getNBTItemStack(new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.YELLOW.getData()),ChatColor.RED + b));
                        inventory.setItem(BrewingAnimation1,ItemStackUtil.getNBTItemStack(new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.ORANGE.getData()),ChatColor.RED + b));
                    }
                    private void playBrewingAnimation2(Inventory inventory,String b){
                        for (int x : BrewingAnimations) inventory.setItem(x,ItemStackUtil.getNBTItemStack(new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.YELLOW.getData()),ChatColor.RED + b));
                        for (int x0 : BrewingAnimation2) inventory.setItem(x0,ItemStackUtil.getNBTItemStack(new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.ORANGE.getData()),ChatColor.RED + b));
                    }
                    private void playBrewingAnimation3(Inventory inventory,String b){
                        for (int x : BrewingAnimations) inventory.setItem(x,ItemStackUtil.getNBTItemStack(new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.YELLOW.getData()),ChatColor.RED + b));
                        for (int x0 : BrewingAnimation3) inventory.setItem(x0,ItemStackUtil.getNBTItemStack(new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.ORANGE.getData()),ChatColor.RED + b));
                    }
                    private void playBrewingAnimation4(Inventory inventory,String b){
                        for (int x : BrewingAnimations) inventory.setItem(x,ItemStackUtil.getNBTItemStack(new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.YELLOW.getData()),ChatColor.RED + b));
                        for (int x0 : BrewingAnimation4) inventory.setItem(x0,ItemStackUtil.getNBTItemStack(new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.ORANGE.getData()),ChatColor.RED + b));
                    }
                    private void playBrewingAnimation5(Inventory inventory,String b){
                        for (int x : BrewingAnimations) inventory.setItem(x,ItemStackUtil.getNBTItemStack(new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.YELLOW.getData()),ChatColor.RED + b));
                    }
                    private void playBrewingAnimation6(Inventory inventory,String b){
                        for (int x : BrewingAnimations) inventory.setItem(x,ItemStackUtil.getNBTItemStack(new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.BLUE.getData()),ChatColor.RED + b));
                    }
                    private boolean isMaterialOn(Inventory inventory){
                        for (Material material : brewingStandGUI.getMaterials()){
                            if (material == inventory.getItem(BrewingMaterial).getType()){
                                return true;
                            }
                        }
                        return false;
                    }
                    private void setResultPotion(Inventory inventory){
                        Material material = inventory.getItem(BrewingMaterial).getType();
                        List<Material> Tier1List = new BrewingStandGUI().getTier1Materials();
                        List<Material> Tier2List = new BrewingStandGUI().getTier2Materials();
                        List<Short> UpgradablePotionData = new BrewingStandGUI().getUpgradablePotionData();
                        for (int b : BrewingResultSlot) {
                            if (!ItemStackUtil.isEmpty(inventory, b)) {
                                short PotionData = inventory.getItem(b).getDurability();
                                if (material == BrewingRecipes.AWKWARD.getBrewingMaterial()) {   //粗制药水
                                    if (PotionData == 0) inventory.setItem(b, BrewingRecipes.AWKWARD.getResultPotion());
                                } else if (isTier1Material(material)) {
                                    if (PotionData == 16) {
                                        for (BrewingRecipes bR : BrewingRecipes.values()) {
                                            if (material == bR.getBrewingMaterial()) {
                                                inventory.setItem(b, bR.getResultPotion());
                                            }
                                        }
                                    }
                                } else if (isTier2Material(material)) {  //材料为红石或萤石粉
                                    if (material == Material.REDSTONE) {
                                        if (PotionData == BrewingRecipes.SPEED1.getResultPotion().getDurability()) {
                                            inventory.setItem(b, BrewingRecipes.SPEED3.getResultPotion());
                                        } else if (PotionData == BrewingRecipes.STRENGTH1.getResultPotion().getDurability()) {
                                            inventory.setItem(b, BrewingRecipes.STRENGTH2.getResultPotion());
                                        } else if (PotionData == BrewingRecipes.REGENERATION1.getResultPotion().getDurability()) {
                                            inventory.setItem(b, BrewingRecipes.REGENERATION3.getResultPotion());
                                        } else if (PotionData == BrewingRecipes.RESISTANCE1.getResultPotion().getDurability()) {
                                            inventory.setItem(b, BrewingRecipes.RESISTANCE2.getResultPotion());
                                        } else if (PotionData == BrewingRecipes.FIRERESISTANCE1.getResultPotion().getDurability()) {
                                            inventory.setItem(b, BrewingRecipes.FIRERESISTANCE2.getResultPotion());
                                        } else if (PotionData == BrewingRecipes.JumpBoost1.getResultPotion().getDurability()) {
                                            inventory.setItem(b, BrewingRecipes.JumpBoost3.getResultPotion());
                                        }
                                    } else {
                                        if (PotionData == BrewingRecipes.SPEED1.getResultPotion().getDurability()) {
                                            inventory.setItem(b, BrewingRecipes.SPEED2.getResultPotion());
                                        } else if (PotionData == BrewingRecipes.REGENERATION1.getResultPotion().getDurability()) {
                                            inventory.setItem(b, BrewingRecipes.REGENERATION2.getResultPotion());
                                        } else if (PotionData == BrewingRecipes.JumpBoost1.getResultPotion().getDurability()) {
                                            inventory.setItem(b, BrewingRecipes.JumpBoost2.getResultPotion());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    private boolean isPotionOn(Inventory inventory){
                        int a = 0;
                        Material material = inventory.getItem(BrewingMaterial).getType();
                        for (int b : BrewingResultSlot) {
                            if (!ItemStackUtil.isEmpty(inventory, b)) {
                                short PotionData = inventory.getItem(b).getDurability();
                                if (material == BrewingRecipes.AWKWARD.getBrewingMaterial()) {
                                    if (PotionData == 0) a++; //waterBottle -> NetherStalk
                                } else if (isTier1Material(material)) {
                                    if (PotionData == BrewingRecipes.AWKWARD.getResultPotion().getDurability())
                                        a++;    // Check if the potion's data is 16 (awkward potion)
                                } else if (isTier2Material(material)) {
                                    //upgradable potion
                                    if (material == Material.REDSTONE) {
                                        if (PotionData == BrewingRecipes.SPEED1.getResultPotion().getDurability() || PotionData == BrewingRecipes.STRENGTH1.getResultPotion().getDurability() || PotionData == BrewingRecipes.JumpBoost1.getResultPotion().getDurability() || PotionData == BrewingRecipes.REGENERATION1.getResultPotion().getDurability() || PotionData == BrewingRecipes.FIRERESISTANCE1.getResultPotion().getDurability() || PotionData == BrewingRecipes.RESISTANCE1.getResultPotion().getDurability())
                                            a++;
                                    } else {
                                        if (PotionData == BrewingRecipes.SPEED1.getResultPotion().getDurability() || PotionData == BrewingRecipes.REGENERATION1.getResultPotion().getDurability() || PotionData == BrewingRecipes.JumpBoost1.getResultPotion().getDurability())
                                            a++;
                                    }
                                }
                            }
                        }
                        return a > 0;
                    }
                }.runTaskTimer(plugin, 1L, 1L);
            }
        }
    }

    @EventHandler
    public void b(InventoryClickEvent e){
        if (e.getClickedInventory().getTitle().equals(Title)){
            Inventory inventory = e.getClickedInventory();
            //手动
            ItemStack currentItem = e.getCurrentItem();
            ItemStack cursorItem = e.getCursor();
            if (e.getRawSlot() == BrewingMaterial){
                if (currentItem.getType() == Material.AIR || isMaterial(currentItem.getType())){  //材料槽
                    if (!isMaterial(cursorItem.getType()) && cursorItem.getType() != Material.AIR)
                        e.setCancelled(true);
                }
            }else if (e.getRawSlot() == BrewingResultSlot[0] || e.getRawSlot() == BrewingResultSlot[1] || e.getRawSlot() == BrewingResultSlot[2]){  //药水槽
                if (currentItem.getType() == Material.AIR || currentItem.getType() == Material.POTION){
                    if (cursorItem.getType() != Material.POTION && cursorItem.getType() != Material.AIR)
                        e.setCancelled(true);
                }
            }else e.setCancelled(true);
        }else if (e.getClickedInventory() == e.getWhoClicked().getInventory() && e.getClick() == ClickType.SHIFT_LEFT && e.getWhoClicked().getOpenInventory().getTopInventory().getTitle().equals(Title)){
            Inventory inventory = e.getWhoClicked().getOpenInventory().getTopInventory();
            e.setCancelled(true);
            if (isMaterial(e.getCurrentItem().getType())){
                if (ItemStackUtil.isEmpty(inventory,BrewingMaterial)){
                    inventory.setItem(BrewingMaterial,e.getCurrentItem());
                    e.setCurrentItem(new ItemStack(Material.AIR));
                }
            }else if (e.getCurrentItem().getType() == Material.POTION){
                for (int x : BrewingResultSlot){
                    if (ItemStackUtil.isEmpty(inventory,x)){
                        inventory.setItem(x,e.getCurrentItem());
                        e.setCurrentItem(new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }


    private void openBrewingStandGUI(Player player,Block block){
        Inventory i = getBrewingGUI(player,block);
        MhBrewingStand.put(block,i);
        player.openInventory(i);
        player.updateInventory();
    }

    public Inventory getBrewingGUI(Player player){
            Inventory inventory = Bukkit.createInventory(player, 54, Title);
            for (int i = 0;i < inventory.getSize() ; i++){
                inventory.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData()));
            }
            for (int a : BrewingAnimation2){
                inventory.setItem(a,new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData()));
            }
            for (int b : BrewingAnimation3){
                inventory.setItem(b,new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData()));
            }
            for (int c : BrewingAnimation4){
                inventory.setItem(c,new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData()));
            }
            for (int d : BrewingResultSlot){
                inventory.setItem(d,new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData()));
            }
            inventory.setItem(BrewingAnimation1,new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.BLUE.getData()));
            inventory.setItem(BrewingMaterial,new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()));

            return inventory;
    }

    public Inventory getBrewingGUI(Block block){
        return MhBrewingStand.get(block);
    }

    public Inventory getBrewingGUI(Player player , Block block){
        if (MhBrewingStand.containsKey(block)){
            return MhBrewingStand.get(block);
        }else {
            Inventory inventory = Bukkit.createInventory(player, 54, Title);
            for (int i = 0;i < inventory.getSize() ; i++){
                inventory.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData()));
            }
            for (int a : BrewingAnimation2){
                inventory.setItem(a,new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData()));
            }
            for (int b : BrewingAnimation3){
                inventory.setItem(b,new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData()));
            }
            for (int c : BrewingAnimation4){
                inventory.setItem(c,new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLUE.getData()));
            }
            for (int d : BrewingResultSlot){
                inventory.setItem(d,new ItemStack(Material.AIR));
            }
            inventory.setItem(BrewingAnimation1,new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.BLUE.getData()));
            inventory.setItem(BrewingMaterial,new ItemStack(Material.AIR));
            return inventory;
        }
    }
    private boolean isMaterial(Material material){
        for (Material material1 : new BrewingStandGUI().getMaterials()) if (material1 == material) return true;
        return false;
    }

    private boolean isTier1Material(Material material){
        for (Material material1 : new BrewingStandGUI().getTier1Materials())
            if (material1 == material) return true;
        return false;
    }

    private boolean isTier2Material(Material material){
        for (Material material1 : new BrewingStandGUI().getTier2Materials())
            if (material1 == material) return true;
        return false;
    }
    @EventHandler
    public void f(BlockBreakEvent e){
        if (e.getBlock().getType() == Material.BREWING_STAND){
            Block block = e.getBlock();
            Location location = block.getLocation();
            if (MhBrewingStand.containsKey(block)){
                Inventory inventory = getBrewingGUI(block);
                for (int x : BrewingResultSlot){
                    if (inventory.getItem(x).getType() == Material.POTION && !ItemStackUtil.isEmpty(inventory,x)){
                        location.getWorld().dropItem(location,inventory.getItem(x));
                    }
                }
                if (!ItemStackUtil.isEmpty(inventory,BrewingMaterial)){
                    location.getWorld().dropItem(location,inventory.getItem(BrewingMaterial));
                }
                MhBrewingStand.remove(block);
            }
        }
    }
}
