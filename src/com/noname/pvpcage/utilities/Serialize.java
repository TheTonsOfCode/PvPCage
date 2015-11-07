package com.noname.pvpcage.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Serialize {
    
    private static String key = ":";
    
    public static String serializeLocation(Location location){
        if(location == null){
            return null;
        }
        return location.getWorld().getName() + key + location.getBlockX() + key + location.getBlockY() + key + location.getBlockZ();
    }
    
    public static String serializeFullLocation(Location location){
        if(location == null){
            return null;
        }
        return location.getWorld().getName() + key + location.getX() + key + location.getY() + key + location.getZ() + key + location.getYaw() + key + location.getPitch();
    }
    
    public static boolean isSerialWorldDetected(String serial){
        return Bukkit.getServer().getWorld(serial.split(key)[0]) != null;
    }
    
    public static Location unserializeLocation(String serial){
        String tokens[] = serial.split(key);
        if(tokens.length != 4){
            return null;
        }

        return new Location(
                Bukkit.getServer().getWorld(tokens[0]), 
                Integer.parseInt(tokens[1]), 
                Integer.parseInt(tokens[2]), 
                Integer.parseInt(tokens[3])
        );
    }
    
     public static Location unserializeFullLocation(String serial){
        String tokens[] = serial.split(key);
        if(tokens.length != 6){
            return null;
        }

        return new Location(
                Bukkit.getServer().getWorld(tokens[0]), 
                Integer.parseInt(tokens[1]), 
                Integer.parseInt(tokens[2]), 
                Integer.parseInt(tokens[3]),
                Float.parseFloat(tokens[4]),
                Float.parseFloat(tokens[5])
        );
    }
}
