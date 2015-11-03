package com.noname.pvpcage.utilities;

import com.noname.pvpcage.PvPCage;
import de.slikey.effectlib.effect.TextEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class Utils {

    public static void createParticleText(String chars, Location loc) {
        TextEffect text = new TextEffect(PvPCage.getEffectManager());
        text.text = chars;
        text.particle.equals(ParticleEffect.FLAME);
        text.period = 10;
        text.visibleRange = 50F;
        text.autoOrient = true;
        text.particle = de.slikey.effectlib.util.ParticleEffect.FLAME;
        text.setLocation(loc.add(5, 2, 0));
        text.start();       
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
    
    public static String loadQuery(String queryName) {
        queryName += ".sql";
        String query = "";
        try {
            String line;
            InputStream is = new Utils().getClass().getClassLoader().getResourceAsStream(queryName);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            while ((line = input.readLine()) != null) {
                query += line;
            }
            input.close();
            return query;
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }
}
