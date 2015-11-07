package com.noname.pvpcage.builder;

import com.noname.pvpcage.PvPCage;
import com.noname.pvpcage.utilities.Log;
import com.noname.pvpcage.utilities.Serialize;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class CageConfigFile {
    
    private static final HashMap<String, CageConfigFile> cache = new HashMap<>();
    private static final String SCHEM_FOLDER = PvPCage.getInstance().getDataFolder().getAbsolutePath() + "/schems";

    private List<String> locationsTeamA;
    private List<String> locationsTeamB;
    private String fileName;

    public CageConfigFile(String fileName) {
        this.fileName = fileName;
        
        locationsTeamA = new ArrayList<>();
        locationsTeamB = new ArrayList<>();
        
        if(!new File(SCHEM_FOLDER, fileName + ".yml").exists()){
            save();
        }
        load();
        cache.put(fileName, this);
    }

    public Location getTeamASpawn() {
        return aSingle(locationsTeamA);
    }

    public ArrayList<Location> getTeamASpawns() {
        return aMulti(locationsTeamA);
    }

    public Location getTeamBSpawn() {
        return aSingle(locationsTeamB);
    }
    
    public ArrayList<Location> getTeamBSpawns() {
        return aMulti(locationsTeamB);
    }
    
    private Location aSingle(List<String> a) {
        if (a.isEmpty()) {
            return null;
        }

        return Serialize.unserializeFullLocation(a.get(0));
    }

    private ArrayList<Location> aMulti(List<String> a) {
        if (a.isEmpty()) {
            return null;
        }
        
        ArrayList<Location> b = new ArrayList<>();
        
        for(int i = 0; i < getMinimumMultiple() ;i++) {
            b.add(Serialize.unserializeFullLocation(a.get(i)));
        }

        return b;
    }

    public int getMinimumMultiple() {
        int a = locationsTeamA.size();
        int b = locationsTeamB.size();

        if (a < b) {
            return a;
        }
        return b;
    }

    public boolean isMultipleArena() {
        return locationsTeamA.size() > 1 && locationsTeamB.size() > 1;
    }

    public void save() {
        File a = new File(SCHEM_FOLDER);
        if (!a.exists()) {
            a.mkdirs();
        }
        File f = new File(SCHEM_FOLDER, fileName + ".yml");
        if (!f.exists()) {
            try { 
                f.createNewFile();
            } catch (IOException ex) {
                Log.DEBUG.print("&cBlad podczasz tworzenia konfiguracji schematu!");
            }
        }
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
        
        yaml.set("ASpawns", locationsTeamA);
        yaml.set("BSpawns", locationsTeamB);
        
        try {
            yaml.save(f);
        } catch (IOException e) {
        }
    }

    private void load() {
        File f = new File(SCHEM_FOLDER, fileName + ".yml");
        if(f == null) {
            Log.DEBUG.print("&cBlad podczasz wczytywania konfiguracji schematu!");
            return;
        }
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
        
        locationsTeamA = yaml.getStringList("ASpawns");
        locationsTeamB = yaml.getStringList("BSpawns");
    }
    
    public static CageConfigFile getCageConfigFile(String fileName) {
        CageConfigFile c = cache.get(fileName);
        if(c == null) {
            c = new CageConfigFile(fileName);
        }
        return c;
    }
}
