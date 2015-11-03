package com.noname.pvpcage.listeners;

import com.noname.pvpcage.utilities.Msg;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CageItemListener implements Listener {

    public static Location LEFT, RIGHT;
    
    public static boolean areSetted() {
        return LEFT != null && RIGHT != null;
    }

    @EventHandler
    public void item(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("pvp.admin")) {
            return;
        }

        Action a = e.getAction();

        if (a == Action.RIGHT_CLICK_AIR || a == Action.LEFT_CLICK_AIR) {
            return;
        }

        if (!(e.getItem().getType() == Material.BLAZE_ROD)) {
            return;
        }

        Block b = e.getClickedBlock();

        if (e == null) {
            Msg.send(p, "&cNie kliknieto zadnego bloku!");
            return;
        }

        Location l = b.getLocation();
        boolean set = false;
        boolean left = true;
        String cl = "";

        if (a == Action.LEFT_CLICK_BLOCK) {
            LEFT = l;
            set = true;
            cl = "LEWA";
        } else if (a == Action.RIGHT_CLICK_BLOCK) {
            RIGHT = l;
            set = true;
            left = false;
            cl = "PRAWA";
        }

        if (!set) {
            Msg.send(p, "&cERROR!");
        }
        else {
            int f = 0;
            if(LEFT != null && RIGHT != null) {
                int x = Math.abs(RIGHT.getBlockX() - LEFT.getBlockX()) + 1;
                int y = Math.abs(RIGHT.getBlockY() - LEFT.getBlockY()) + 1;
                int z = Math.abs(RIGHT.getBlockZ() - LEFT.getBlockZ()) + 1;
                f = x * y * z;
            }
            
            Location loc = left ? LEFT : RIGHT;
            String s = "&7(&8X:&e" + loc.getBlockX() + "&7,&8Y:&e" + loc.getBlockY() + "&7,&8Z:&e" + loc.getBlockZ() + "&7)";
            
            Msg.send(p, "&aUstawiono &b" + cl + " &alokacje klatki! " + s + (f == 0 ? "" : " &7[&e" + f + "&7]"));
            e.setCancelled(true);
        }
    }
}
