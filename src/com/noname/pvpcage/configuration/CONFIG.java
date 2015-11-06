package com.noname.pvpcage.configuration;

import com.noname.pvpcage.PvPCage;
import org.bukkit.configuration.file.FileConfiguration;

public class CONFIG {
    
    public static int LOGGER_LEVEL = 3;
    
    /*
    
    User configuration section
    
    */
    
    public static int USER_START_RANKING;

    public static int PARTY_TAG_LENTH;
    public static int PARTY_NAME_LENTH;
    public static int PARTY_MAX_MEMBERS;
    
    public static void loadConfiguration() {
        FileConfiguration config = PvPCage.getInstance().getConfig();

        USER_START_RANKING = config.getInt("User.Start_Ranking");

        PARTY_MAX_MEMBERS = config.getInt("Party_Max_Members");
        PARTY_NAME_LENTH = config.getInt("Party_Name_Lenth");
        PARTY_TAG_LENTH = config.getInt("Party_Tag_Lenth");
    }
}
