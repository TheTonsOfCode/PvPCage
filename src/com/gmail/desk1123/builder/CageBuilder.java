package com.gmail.desk1123.builder;

import org.bukkit.Difficulty;
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
        newWorld.setAnimalSpawnLimit(0);
        newWorld.setPVP(true);
        newWorld.setDifficulty(Difficulty.PEACEFUL);
        newWorld.setStorm(false);
        newWorld.setThundering(false);
        newWorld.setWaterAnimalSpawnLimit(0);
        newWorld.setTicksPerAnimalSpawns(0);
        newWorld.setTime(0);
        return newWorld;
    }
    
    public void teleportToCageWorld(Player player) {
        
    }
}
