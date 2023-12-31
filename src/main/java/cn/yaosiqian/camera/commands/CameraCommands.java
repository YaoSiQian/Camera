package cn.yaosiqian.camera.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.yaosiqian.camera.Camera;
import cn.yaosiqian.camera.Picture;

public class CameraCommands implements CommandExecutor {

    private Camera instance = Camera.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("photo")) {
            if(instance.getConfig().getBoolean("settings.camera.permissions")) {
                if(!p.hasPermission("cameras.command")) return false;
            }

            Picture.photo(p);
            return true;
        }

        return false;
    }

}