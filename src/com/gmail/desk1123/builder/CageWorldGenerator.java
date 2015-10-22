package com.gmail.desk1123.builder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

public class CageWorldGenerator extends ChunkGenerator {
    private NoiseGenerator generator;

    private NoiseGenerator getGenerator(World world) {
        if (generator == null) {
            generator = new SimplexNoiseGenerator(world);
        }

        return generator;
    }

    public byte[] generate(World world, Random random, int cx, int cz) {
        byte[] result = new byte[32768];

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 11; y++)
                    if (y < 10) {
                      result[(x * 16 + z) * 128 + y] = (byte)Material.COAL_BLOCK.getId();
                    } else {
                      if(x == 0 || z == 0)
                        result[(x * 16 + z) * 128 + y] = (byte)Material.QUARTZ_BLOCK.getId();
                      else
                        result[(x * 16 + z) * 128 + y] = (byte)Material.COAL_BLOCK.getId();
                }
            }
        }

        return result;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList();
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        int y = world.getHighestBlockYAt(0, 0);
        return new Location(world, 0, y, 0);
    }
}

