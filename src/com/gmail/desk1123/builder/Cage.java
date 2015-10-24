package com.gmail.desk1123.builder;

import com.gmail.desk1123.objets.User;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

public abstract class Cage {

    public abstract void onCreateBattle(Location loc);

    public abstract void onEndBattle(User winner);
    protected List<Location> blocks = new ArrayList<>();

    private CageType cageType;

    public Cage(CageType cageType) {
        this.cageType = cageType;
    }

    public CageType getCageType() {
        return cageType;
    }

    protected String desLocation(Location loc) {
        return loc.getWorld() + " | " + loc.getBlockX() + " | " + loc.getBlockY() + " | " + loc.getBlockZ() + "\n";
    }
}
