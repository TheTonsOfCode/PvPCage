package com.noname.pvpcage.Test;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmnd, String string, String[] strings) {
        if(sender instanceof Player){
            ItemManager.createInventory((Player)sender);
        }
        return false;
    }
    
    
    

}
