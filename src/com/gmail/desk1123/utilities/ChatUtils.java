package com.gmail.desk1123.utilities;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

public class ChatUtils {

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
