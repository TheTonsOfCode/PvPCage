package com.gmail.desk1123.builder;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class CageBuilder {
    
    //Default Cage World Name
    private static final String CAGE_WORLD = "PVPCageArenas";
    
    public World createCageWorld() {
        World newWorld = null;   
        
        WorldCreator wc = new WorldCreator(CAGE_WORLD);
        wc.environment(World.Environment.NORMAL);
        wc.generator(new CageWorldGenerator());
        newWorld = wc.createWorld();

        return newWorld;
    }
    
    public void teleportToCageWorld(Player player) {
        
    }
}
