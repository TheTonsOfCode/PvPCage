package com.gmail.desk1123.builder.cage;

import com.gmail.desk1123.builder.Cage;
import com.gmail.desk1123.builder.CageType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class WalledCage extends Cage {
    
    private static final int SIZE = 10;
    private static final Material 
            WALL_MATERIAL = Material.EMERALD_BLOCK,
            FLOOR_ATERIAL = Material.SPONGE;
    
    public WalledCage() {
        super(CageType.WALLS);
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
                    if(y == 0) {
                        Location l = new Location(world, x, ly + y, z);
                        loc.getBlock().setType(FLOOR_ATERIAL);
                        blocks.add(loc);
                    }
                    else if ((x == px || z == pz) || (x == lx || z == lz)
                            || (x == lx || z == pz) || (x == px || z == lz)) {
                        Location l = new Location(world, x, ly + y, z);
                        loc.getBlock().setType(WALL_MATERIAL);
                        blocks.add(loc);
                    }
                }
            }
        }

    }
}
