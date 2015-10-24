package com.noname.pvpcage.builder;

import com.noname.pvpcage.objets.User;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

public abstract class Cage {

    public abstract CageCuboid calculateCuboid();
    public abstract void onCreateBattle(Location loc);
    public abstract void onRemoveBattle();
    
    protected List<Location> blocks = new ArrayList<>();

    private CageType cageType;
    private CageCuboid cageCuboid;

    public Cage(CageType cageType) {
        this.cageType = cageType;
        calculateCuboid();
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
