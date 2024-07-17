package org.manhunt.food;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class foodList {
    public List<Material> getCookedFood(){
        List<Material> cookedFood = new ArrayList<>();
        cookedFood.add(Material.COOKED_BEEF);
        cookedFood.add(Material.COOKED_CHICKEN);
        cookedFood.add(Material.COOKED_MUTTON);
        cookedFood.add(Material.COOKED_RABBIT);
        cookedFood.add(Material.GRILLED_PORK);
        return cookedFood;
    }
    public List<Material> getCooklessFood(){
        List<Material> cooklessFood = new ArrayList<>();
        cooklessFood.add(Material.RAW_BEEF);
        cooklessFood.add(Material.PORK);
        cooklessFood.add(Material.RAW_CHICKEN);
        cooklessFood.add(Material.MUTTON);
        cooklessFood.add(Material.RABBIT);
        return cooklessFood;
    }
    public List<Material> getFoodList(){
        List<Material> foodlist = new ArrayList<>();
        foodlist.add(Material.GOLDEN_APPLE);
        foodlist.add(Material.BREAD);
        foodlist.add(Material.APPLE);
        foodlist.add(Material.COOKED_BEEF);
        foodlist.add(Material.COOKED_CHICKEN);
        foodlist.add(Material.COOKED_MUTTON);
        foodlist.add(Material.COOKED_RABBIT);
        foodlist.add(Material.GRILLED_PORK);
        foodlist.add(Material.RAW_BEEF);
        foodlist.add(Material.PORK);
        foodlist.add(Material.RAW_CHICKEN);
        foodlist.add(Material.MUTTON);
        foodlist.add(Material.RABBIT);
        return foodlist;
    }
}
