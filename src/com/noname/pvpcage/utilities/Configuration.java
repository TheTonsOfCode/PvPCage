package com.noname.pvpcage.utilities;

import com.noname.pvpcage.PvPCage;
import com.noname.pvpcage.managers.FileManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configuration {

    public static FileConfiguration config;
    public static FileConfiguration cageConfig = YamlConfiguration.loadConfiguration(FileManager.getCageConfig());
    public static FileConfiguration partyConfig = YamlConfiguration.loadConfiguration(FileManager.getPartyConfig());
    
    public static Material CAGE_WALL_MATERIAL;
    public static Material CAGE_FLOOR_MATERIAL;

    public static int USER_START_RANKING;

    public static int PARTY_TAG_LENTH;
    public static int PARTY_NAME_LENTH;
    public static int PARTY_MAX_MEMBERS;
    
    public static int LOGGER_LEVEL;

    public static String HOST;
    public static String PASSWORD;
    public static String DATABASE;
    public static String USER;
    public static int PORT;

    public static void loadConfiguration() {
        config = PvPCage.getInstance().getConfig();
        USER_START_RANKING = config.getInt("User.Start_Ranking");

        HOST = config.getString("MySQL.Host");
        PASSWORD = config.getString("MySQL.Password");
        DATABASE = config.getString("MySQL.Database");
        USER = config.getString("MySQL.User");
        PORT = config.getInt("MySQL.Port");

        PARTY_MAX_MEMBERS = partyConfig.getInt("Party_Max_Members");
        PARTY_NAME_LENTH = partyConfig.getInt("Party_Name_Lenth");
        PARTY_TAG_LENTH = partyConfig.getInt("Party_Tag_Lenth");
        
        CAGE_WALL_MATERIAL = Material.getMaterial(cageConfig.getString("WalledCage.Wall_Material"));
        CAGE_FLOOR_MATERIAL = Material.getMaterial(cageConfig.getString("WalledCage.Floor_Material"));
        PvPCage.getInstance().saveConfig();
    }

}
