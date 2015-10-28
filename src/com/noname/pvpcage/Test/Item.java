package com.noname.pvpcage.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {

    private String name;
    private Material mat;
    private int date;
    private Map<Enchantment, Integer> ench = new HashMap();
    private List<String> lore = new ArrayList<>();
    private int amount;
    private boolean glow;

    public Item() {
        name = "";
        date = 0;
        amount = 1;
        mat = Material.STONE;
        glow = false;
    }

    public static void test() {
        Item item1 = new Item();
        item1.setAmount(1);
        item1.setDate(0);
        Map<Enchantment, Integer> e = new HashMap();
        e.put(Enchantment.LUCK, 5);
        item1.setEnch(e);
        item1.setMat(Material.DIAMOND_AXE);
        item1.setName("SIEKERA MOCY");
        ItemManager.addItem(item1);
        Item item2 = new Item();
        item2.setAmount(1);
        item2.setDate(0);
        e.clear();
        e.put(Enchantment.LOOT_BONUS_BLOCKS, 5);
        item2.setEnch(e);
        item2.setMat(Material.DIAMOND_HOE);
        item2.setName("&4MOTYKA MOCY");
        ItemManager.addItem(item2);
    }

    public boolean isGlow() {
        return glow;
    }

    public void setGlow(boolean glow) {
        this.glow = glow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getMat() {
        return mat;
    }

    public void setMat(Material mat) {
        this.mat = mat;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Map<Enchantment, Integer> getEnch() {
        return ench;
    }

    public void setEnch(Map<Enchantment, Integer> ench) {
        this.ench = ench;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void addLore(String a) {
        this.lore.add(a.replace("//", ","));
    }

    public int getAmount() {
        return amount;
    }

    public ItemStack build() {
        ItemStack it = new ItemStack(mat, amount, (byte) date);
        ItemMeta im = it.getItemMeta();
        im.setDisplayName(fixColor(name));
        im.setLore(fixColor(lore));
        it.addUnsafeEnchantments(ench);
        it.setItemMeta(im);
        return it;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static String fixColor(String string) {
        if (string == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> fixColor(List<String> strings) {
        List<String> colors = new ArrayList<String>();
        for (String s : strings) {
            colors.add(fixColor(s));
        }
        return colors;
    }
}
