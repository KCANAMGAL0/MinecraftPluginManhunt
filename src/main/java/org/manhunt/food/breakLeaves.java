package org.manhunt.food;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;


public class breakLeaves implements Listener {
    @EventHandler
    public void playerBreakLeaves(BlockBreakEvent e){
        Block block = e.getBlock();
        World world = block.getWorld();
        if (block.getType().equals(Material.LEAVES) || block.getType().equals(Material.LEAVES_2)){
            int a = (int) (Math.random() * 100 + 1);
            if (a <= 14) {
                world.dropItem(block.getLocation(), new ItemStack(Material.APPLE,1));
            }
        }
    }
}
