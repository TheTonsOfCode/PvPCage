package com.noname.pvpcage.Test;

import com.noname.pvpcage.managers.UserManager;
import com.noname.pvpcage.objects.User;
import com.noname.pvpcage.reflect.ReflConstants;
import com.noname.pvpcage.reflect.ReflectUTIL;
import com.noname.pvpcage.utilities.Utils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {

    public static List<Item> items = new ArrayList<>();

    public static void addItem(Item it) {
        if (!items.contains(it)) {
            items.add(it);
        }
    }

    public static void removeItem(Item it) {
        if (items.contains(it)) {
            items.remove(it);
        }
    }

    public static List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public static void createInventory(Player p) {
        Inventory inv = Bukkit.createInventory(null, 45, "Wybor Przedmiotow");
        User u = UserManager.getUser(p.getUniqueId());
        for (Item item : u.getSelectedItems()) {
            System.out.println(item.getMat() + " " + item.isGlow());
            ItemStack it = new ItemStack(item.getMat(), item.getAmount(), (byte) item.getDate());
            ItemMeta im = it.getItemMeta();
            im.setDisplayName(Utils.fixColor(item.getName()));
            List<String> lore = new ArrayList<>();
            lore.add("&3Enchanty:");
            for (Enchantment ench : item.getEnch().keySet()) {
                lore.add("&2" + ench.getName() + " &e- &7[&6" + item.getEnch().get(ench) + "&7]");
            }
            im.setLore(Utils.fixColor(lore));
            it.setItemMeta(im);
            if (item.isGlow()) {
                System.out.println(item.getMat() + " " + item.isGlow());
                inv.addItem(getGlowItemStack(it));
            } else {
                inv.addItem(it);
                System.out.println(item.getMat() + " " + item.isGlow());
            }
            lore.clear();
        }
        p.openInventory(inv);
        Listeners.vievers.add(p.getName());
    }

    public static ItemStack getGlowItemStack(ItemStack i) {
        if (i == null) {
            return i;
        }
        ItemStack item = i.clone();
        Object nmsStack = ReflectUTIL.invoke(ReflConstants.asNMSCopy, null, new Object[]{item});

        Object tag = null;
        Boolean b = (Boolean) ReflectUTIL.invoke(ReflConstants.hasTag, nmsStack, new Object[0]);
        if ((b == null) || (!b.booleanValue())) {
            tag = ReflectUTIL.newInstance(ReflConstants.NBTTagCompound, new Object[0]);
        }
        if (tag == null) {
            tag = ReflectUTIL.invoke(ReflConstants.getTag, nmsStack, new Object[0]);
        }
        Object ench = ReflectUTIL.newInstance(ReflConstants.NBTTagList, new Object[0]);
        ReflectUTIL.invoke(ReflConstants.NBTTagList_set, tag, new Object[]{"ench", ench});

        ReflectUTIL.invoke(ReflConstants.setTag, nmsStack, new Object[]{tag});

        return (ItemStack) ReflectUTIL.invoke(ReflConstants.asCraftMirror, null, new Object[]{nmsStack});
    }

}
