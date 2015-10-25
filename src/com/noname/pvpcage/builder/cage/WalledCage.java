package com.noname.pvpcage.builder.cage;

import com.noname.pvpcage.builder.Cage;
import com.noname.pvpcage.builder.CageCuboid;
import com.noname.pvpcage.builder.CageType;
import com.noname.pvpcage.utilities.Configuration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class WalledCage extends Cage {
    
    private static final int SIZE = 10;
    private static final Material 
            WALL_MATERIAL = Configuration.CAGE_WALL_MATERIAL,
            FLOOR_MATERIAL = Configuration.CAGE_FLOOR_MATERIAL;
    
    public WalledCage() {
        super(CageType.WALLS);
    }
    
    @Override
    public void calculateCuboid(Location loc) {
        cageCuboid = new CageCuboid(loc, SIZE);
    }

    @Override
    public void onCreateBattle(Location loc) {
        World world = loc.getWorld();

        int lx = loc.getBlockX() + SIZE;
        int lz = loc.getBlockZ() + SIZE;

        int px = loc.getBlockX() - SIZE;
        int pz = loc.getBlockZ() - SIZE;

        int ly = loc.getBlockY();

        for (int x = px; x <= lx; x++) {
            for (int z = pz; z <= lz; z++) {
                for (int y = 0; y <= 6; y++) {
                    if ((x == px || z == pz) || (x == lx || z == lz)
                            || (x == lx || z == pz) || (x == px || z == lz)) {
                        Location l = new Location(world, x, ly + y, z);
                        l.getBlock().setType(WALL_MATERIAL);
                        blocks.add(loc);
                    }
                    else if(y == 0) {
                        Location l = new Location(world, x, ly + y, z);
                        l.getBlock().setType(FLOOR_MATERIAL);
                        blocks.add(loc);
                    }                 
                }
            }
        }

    }

    @Override
    public void onRemoveBattle() {
    
    }
}
