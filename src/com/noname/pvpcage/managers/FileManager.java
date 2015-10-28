package com.noname.pvpcage.managers;

import com.noname.pvpcage.PvPCage;
import java.io.File;

public class FileManager {

    private static final File dirCfg = new File(PvPCage.getInstance().getDataFolder() + File.separator + "configs");
    private static final File dirData = new File(PvPCage.getInstance().getDataFolder() + File.separator + "data");

    private static final File partyCfg = new File(dirCfg, "PartyConfig.yml");
    private static final File cageCfg = new File(dirCfg, "CageConfig.yml");

    private static final File userData = new File(dirData, "UsersData");
    private static final File partyData = new File(dirData, "PartyData");

    public static void checkFiles() {
        if (!dirCfg.exists()) {
            dirCfg.mkdirs();
        }
        if (!dirData.exists()) {
            dirData.mkdirs();
        }
        if (!userData.exists()) {
            userData.mkdirs();
        }
        if (!partyData.exists()) {
            partyData.mkdirs();
        }
        if (!partyCfg.exists()) {
            if (PvPCage.getInstance().getResource("PartyConfig.yml") != null) {
                PvPCage.copy(PvPCage.getInstance().getResource("PartyConfig.yml"), partyCfg);
            }
        }
        if (!cageCfg.exists()) {
            if (PvPCage.getInstance().getResource("CageConfig.yml") != null) {
                PvPCage.copy(PvPCage.getInstance().getResource("CageConfig.yml"), cageCfg);
            }
        }
    }

    public static File getPartyData() {
        return partyData;
    }

    public static File getUserData() {
        return userData;
    }

    public static File getPartyConfig() {
        return partyCfg;
    }

    public static File getCageConfig() {
        return cageCfg;
    }
}
