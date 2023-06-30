package cn.yaosiqian.camera.listeners;

import java.util.Map;
import java.util.Objects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import cn.yaosiqian.camera.Camera;
import cn.yaosiqian.camera.Picture;

public class CameraClick implements Listener {

    private Camera instance = Camera.getInstance();

    @EventHandler
    public void cameraClicked(PlayerInteractEvent e) {
        final Action action = e.getAction();
        if (action != Action.RIGHT_CLICK_AIR) {
            if (action != Action.RIGHT_CLICK_BLOCK || Objects.requireNonNull(e.getClickedBlock()).getType().isInteractable()) {
                return;
            }
        }

        final ItemStack itemStack = e.getItem();
        if(itemStack == null)
            return;

        if (itemStack.getItemMeta() == null || !itemStack.getItemMeta().hasCustomModelData() || itemStack.getItemMeta().getCustomModelData() != 14) {
            return;
        }

        e.setCancelled(true);
        final Player p = e.getPlayer();

        if (instance.getConfig().getBoolean("settings.camera.permissions")
                && !p.hasPermission("cameras.useitem")) {
            return;
        }

        boolean messages = instance.getConfig().getBoolean("settings.messages.enabled");
        if (p.getInventory().firstEmpty() == -1) { //check to make sure there is room in the inventory for the map
            if(messages) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("settings.messages.invfull")));
            }
            return;
        }
        if (p.getInventory().contains(Material.PAPER)) { //check to make sure the player has paper
            boolean tookPicture = Picture.takePicture(p);

            if(tookPicture) {
                p.playSound(p.getLocation(), Sound.BLOCK_PISTON_EXTEND, 0.5F, 2.0F);
                //remove 1 paper from the player's inventory
                Map<Integer, ? extends ItemStack> paperHash = p.getInventory().all(Material.PAPER);
                for (ItemStack item : paperHash.values()) {
                    item.setAmount(item.getAmount() - 1);
                    break;
                }
            }
        } else {
            if(messages) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("settings.messages.nopaper")));
            }
        }
    }
}