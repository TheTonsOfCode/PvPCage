package com.gmail.desk1123;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author dekros987
 */
public class Cage {

    private String id;
    private List<Location> blocks = new ArrayList<>();
    private int size;
    private Material build;
    private World world;
    private Location center;

    public Cage() {
        id = "";
        size = 10;
        build = Material.GLASS;
        center = null;

    }

    public void build(Player player, int size, int a) {
        center = player.getLocation();
        id = player.getName();
        world = player.getWorld();

        build = Material.getMaterial(a);
        if (this.center == null) {
            return;
        }
        if (this.size < 1) {
            return;
        }
        if (this.world == null) {
            this.world = Bukkit.getWorlds().get(0);
        }
        int lx = this.center.getBlockX() + size;
        int lz = this.center.getBlockZ() + size;

        int px = this.center.getBlockX() - size;
        int pz = this.center.getBlockZ() - size;

        int ly = this.center.getBlockY();

        for (int x = px; x < lx; x++) {
            for (int z = pz; z < lz; z++) {
                for (int y = 0; y <= 3; y++) {
                    if ((x == 0 || z == 0) || (x == lx - 1 || z == lz - 1)
                            || (x == lx - 1 || z == 0) || (x == 0 || z == lz - 1)) {
                        Location loc = new Location(world, x, ly + y, z);
                        loc.getBlock().setType(build);
                        blocks.add(loc);

                    }
                }
            }
        }

    }

    private String desLocation(Location loc) {
        return loc.getWorld() + " | " + loc.getBlockX() + " | " + loc.getBlockY() + " | " + loc.getBlockZ() + "\n";

    }
}
