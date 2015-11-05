package com.noname.pvpcage.builder;

import org.bukkit.Location;

public class CageCuboid {

    public int width, height;
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

    public CageCuboid(Location loc, Location loc2, Location center) {
        setRegions(loc, loc2, center);
    }

    public Location getLower() {
        return lower;
    }

    public Location getUpper() {
        return upper;
    }

    private void init() {
        width = upper.getBlockX() - lower.getBlockX();
        height = upper.getBlockZ() - lower.getBlockZ();
    }

    public void setRegions(Location center, int size) {
        lower = center.clone().add(-size, 0, -size);
        upper = center.clone().add(size, 0, size);
        init();
    }

    public void setRegions(Location loc, Location loc2, Location center) {
        lower = new Location(loc.getWorld(), Math.min(loc.getBlockX(), loc2.getBlockX()), 0, Math.min(loc.getBlockZ(), loc2.getBlockZ()));
        upper = new Location(loc.getWorld(), Math.max(loc.getBlockX(), loc2.getBlockX()), 0, Math.max(loc.getBlockZ(), loc2.getBlockZ()));
        init();
        int x = center.getBlockX();
        int z = center.getBlockZ();
        //Mozna odjac do min i dodac do ax ze 2 bloczki to d zawsze odstep
        lower = center.clone().add(-(width >> 1), 0, -(height >> 1));
        upper = center.clone().add((width >> 1), 0, (height >> 1));
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
        
        return aLX <= bUX
            && bLX <= aUX
            && aLZ <= bUZ
            && bLZ <= aUZ;
    }
//        return (corner(aLX, aLZ, aUX, aUZ, bLX, bLZ)
//                || corner(aLX, aLZ, aUX, aUZ, bLX, bUZ)
//                || corner(aLX, aLZ, aUX, aUZ, bUX, bLZ)
//                || corner(aLX, aLZ, aUX, aUZ, bUX, bUZ)
//                || corner(bLX, bLZ, bUX, bUZ, aLX, aLZ)             ZLE...
//                || corner(bLX, bLZ, bUX, bUZ, aLX, aUZ)
//                || corner(bLX, bLZ, bUX, bUZ, aUX, aLZ)
//                || corner(bLX, bLZ, bUX, bUZ, aUX, aUZ));
//    }
//
//    private boolean corner(int aX, int aZ, int bX, int bZ, int cX, int cZ) {
//        return aX >= cX && cX <= bX && aZ >= cZ && cZ <= bZ;
//    }
}
//        return (
//                ((aLX >= bLX && aLX <= bUX) || (aUX >= bLX && aUX <= bUX)) && ((aLZ >= bLZ && aLZ <= bUZ) || (aUZ >= bLZ && aUZ <= bUZ))
//                || ((bLX >= aLX && bLX <= aUX) || (bUX >= aLX && bUX <= aUX)) && ((bLZ >= aLZ && bLZ <= aUZ) || (bUZ >= aLZ && bUZ <= aUZ)) 
////|| ((bLX >= aLX && bUX <= aUX) && (bLZ >= aLZ && bUZ <= aUZ))
//        );
//                                                                                      Niby dobre ale z bugsami
