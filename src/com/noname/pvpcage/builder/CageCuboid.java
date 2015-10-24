package com.noname.pvpcage.builder;

import org.bukkit.Location;

public class CageCuboid {
    private int width, height;
    private Location lower, upper;
    
    public CageCuboid() {
        width = height = 0;
    }
    
    public CageCuboid(int size) {
        this(new Location(null, 0, 0, 0), size);
    }
    
    public CageCuboid(Location loc, int size) {        
        setRegions(loc, size);
    }
    
    public CageCuboid(Location loc, Location loc2) {
        setRegions(loc, loc2);       
    }
    
    private void init() {
        width = upper.getBlockX() - lower.getBlockX();
        height = upper.getBlockZ() - lower.getBlockZ();
    }
    
    public void setRegions(Location loc, int size) {  
        lower = loc.clone().add(-size, 0, -size);
        upper = loc.clone().add(size, 0, size);
        init();
    }
    
    public void setRegions(Location loc, Location loc2) {
        lower = new Location(loc.getWorld(), Math.min(loc.getBlockX(), loc2.getBlockX()), 0, Math.min(loc.getBlockZ(), loc2.getBlockZ()));
        upper = new Location(loc.getWorld(), Math.max(loc.getBlockX(), loc2.getBlockX()), 0, Math.max(loc.getBlockZ(), loc2.getBlockZ()));        
        init();
    }
    
    public boolean isIn(CageCuboid cc) {
        int aLX = lower.getBlockX();
        int aLZ = lower.getBlockZ();
        int aUX = upper.getBlockX();
        int aUZ = upper.getBlockZ();
        int bLX = cc.lower.getBlockX();
        int bLZ = cc.lower.getBlockZ();
        int bUX = cc.upper.getBlockX();
        int bUZ = cc.upper.getBlockZ();
        return ((bLX >= aLX && bLX <= aUX) || (bUX >= aLX && bUX <= aUX)) && ((bLZ >= aLZ && bLZ <= aUZ) || (bUZ >= aLZ && bUZ <= aUZ));
    }
}
