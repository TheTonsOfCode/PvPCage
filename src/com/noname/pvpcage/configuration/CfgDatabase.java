package com.noname.pvpcage.configuration;

import com.noname.pvpcage.PvPCage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class CfgDatabase {
    
    public static String HOST;
    public static String PASSWORD;
    public static String DATABASE;
    public static String USER;
    public static int PORT;
    
    public static void loadConfiguration() {
        FileConfiguration config = PvPCage.getInstance().getConfig();

        HOST = config.getString("MySQL.Host");
        PASSWORD = config.getString("MySQL.Password");
        DATABASE = config.getString("MySQL.Database");
        USER = config.getString("MySQL.User");
        PORT = config.getInt("MySQL.Port");
    }
}
