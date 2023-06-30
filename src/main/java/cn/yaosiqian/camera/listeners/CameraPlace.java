package cn.yaosiqian.camera.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class CameraPlace implements Listener {
    @EventHandler
    public void cameraPlaced(BlockPlaceEvent e) {
        //Prevent players from placing Cameras

        final ItemMeta itemMeta = e.getItemInHand().getItemMeta();
        if (itemMeta != null && itemMeta.hasCustomModelData() && itemMeta.getCustomModelData() == 14) {
            e.setCancelled(true);
        }
    }
}