package com.gmail.desk1123.builder;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class CageWorldGenerator extends ChunkGenerator
{
  
  public short[][] generateExtBlockSections(World world, Random random, int cx, int cz, ChunkGenerator.BiomeGrid biomes)
    {
        short[][] result = new short[16][];
    
    for (int x = 0; x < 16; x++) {
        for (int z = 0; z < 16; z++) {
            for (int y = 0; y < 11; y++) {
                if (y < 10) {
                setBlock(result, x, y, z, (byte)7);
                } else {
                  if(x == 0 || z == 0)
                    setBlock(result, x, y, z, (byte)42);
                  else if(x == z || (z == 16 - x && x == 16 - z))
                    setBlock(result, x, y, z, (byte)172);
                  else
                    setBlock(result, x, y, z, (byte)7);
                }
            }
        }
    }
    
    return result;
  }
  
  public Location getFixedSpawnLocation(World world, Random random)
  {
    return world.getHighestBlockAt(0, 0).getLocation().add(0, 1, 0);
  }
  
  private void setBlock(short[][] result, int x, int y, int z, short blkid) 
    {
        if (result[y >> 4] == null) {
            result[y >> 4] = new short[4096];
        }
        result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
    }
}

