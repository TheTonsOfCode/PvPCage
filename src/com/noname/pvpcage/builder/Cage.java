package com.noname.pvpcage.builder;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

public abstract class Cage {

    public abstract void calculateCuboid(Location loc);
    public abstract void onCreateBattle(Location loc);
    public abstract void onRemoveBattle();
    
    protected List<Location> blocks = new ArrayList<>();
    protected CageCuboid cageCuboid;
    
    private CageType cageType;

    public Cage(CageType cageType) {
        this.cageType = cageType;
        calculateCuboid(new Location(CageBuilder.getCageWorld(), 0, 0, 0));
    }

    public CageType getCageType() {
        return cageType;
    }
    
    public CageCuboid getCageCuboid() {
        return cageCuboid;
    }

    protected String desLocation(Location loc) {
        return loc.getWorld() + " | " + loc.getBlockX() + " | " + loc.getBlockY() + " | " + loc.getBlockZ() + "\n";
    }
}
