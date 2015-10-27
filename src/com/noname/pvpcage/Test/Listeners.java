package com.noname.pvpcage.Test;

import com.noname.pvpcage.managers.UserManager;
import com.noname.pvpcage.objects.User;
import com.noname.pvpcage.utilities.Log;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class Listeners implements Listener {

    public static List<String> vievers = new ArrayList<>();

    @EventHandler
    public void onClickInventory(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (p.getOpenInventory().getTopInventory().getName().equals("Wybor Przedmiotow")) {
            if (vievers.contains(p.getName())) {
                System.out.println("1");
                if (e.getAction().equals(InventoryAction.NOTHING)) {
                    e.setCancelled(true);
                    return;
                    //continue;
                }
                System.out.println("2");
                System.out.println("3");
//                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
//                    e.setCancelled(true);
//                    return;
//                    //continue;
//                }
                System.out.println("4");
                User u = UserManager.getUser(p.getUniqueId());
                for (Item item : u.getSelectedItems()) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        System.out.println("5");
                        if (e.getCurrentItem().getItemMeta() == null
                                || !e.getCurrentItem().getItemMeta().getLore().contains("§3Enchanty:")
                                || e.getCurrentItem().getType() != item.getMat()) {
                            return;
                        }
                        System.out.println("6");
                        System.out.println("akcja: " + e.getAction());
                        if (item.isGlow()) {
                            item.setAmount(item.getAmount() + 1);
                            System.out.println(item.getMat() + " " + item.isGlow());
                        } else {
                            Log.DEBUG.print("Nie swiecil sie ale go zaswiece");
                            item.setGlow(true);
                            System.out.println(item.getMat() + " " + item.isGlow());
                        }
                    }
                    p.closeInventory();
                    ItemManager.createInventory(p);
                    e.setCancelled(true);
                    //return;
                }
            } else {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    void InvClose(InventoryCloseEvent e) {
        if ((e.getPlayer() instanceof Player)) {
            Player p = (Player) e.getPlayer();
            if (vievers.contains(p.getName())) {
                vievers.remove(p.getName());
            }
        }
    }
}
