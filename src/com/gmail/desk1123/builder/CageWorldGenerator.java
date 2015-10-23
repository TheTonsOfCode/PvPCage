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
                    setBlock(result, x, y, z, (byte)80);
                  else
                    setBlock(result, x, y, z, (byte)7);
                }
            }
        }
    }
    
//    Iterator<BedrockCoords> it = this.plugin.blocks.iterator();
//    while (it.hasNext())
//    {
//      BedrockCoords block = (BedrockCoords)it.next();
//      if ((block.x >= chunkX * 16) && (block.x < (chunkX + 1) * 16) && (block.z >= chunkZ * 16) && (block.z < (chunkZ + 1) * 16))
//      {
//        int x = block.x % 16;
//        if (x < 0) {
//          x += 16;
//        }
//        int z = block.z % 16;
//        if (z < 0) {
//          z += 16;
//        }
//        setBlock(result, x, block.y, z, (byte)7);
//        it.remove();
//      }
//    }
    return result;
  }
  
  public Location getFixedSpawnLocation(World world, Random random)
  {
    return new Location(world, 0, 11, 0);
  }
  
  private void setBlock(short[][] result, int x, int y, int z, short blkid) 
    {
        if (result[y >> 4] == null) {
            result[y >> 4] = new short[4096];
        }
        result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
    }
}

