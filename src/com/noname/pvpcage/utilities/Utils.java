package com.noname.pvpcage.utilities;

import com.noname.pvpcage.PvPCage;
import com.noname.pvpcage.objects.Command;
import de.slikey.effectlib.effect.TextEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

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
    
    public static void registerCommand(Command command) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(command.getName(), command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void registerCommands(Plugin plugin, String commandsPackagePath) {
        int classesNumber = commandsPackagePath.split("\\.").length;

        ArrayList<String> classes = new ArrayList();

        String path = commandsPackagePath.replaceAll("\\.", "/");

        String jarName = plugin.getDataFolder().getAbsolutePath() + ".jar";

        try {
            JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
            JarEntry jarEntry;

            while (true) {
                jarEntry = jarFile.getNextJarEntry();
                if (jarEntry == null) {
                    break;
                }
                if ((jarEntry.getName().startsWith(path)) && (jarEntry.getName().endsWith(".class"))) {
                    classes.add(jarEntry.getName().replaceAll("/", "\\."));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String name : classes) {
            try {
                Class c = Utils.class.getClassLoader().loadClass(name.substring(0, name.length() - 6));

                if(c.getGenericSuperclass() == Command.class){
                    registerCommand((Command) c.newInstance());
                }
            } catch (Exception ex) {
                Logger.getLogger(ex.getMessage());
            }
        }
    }
}
