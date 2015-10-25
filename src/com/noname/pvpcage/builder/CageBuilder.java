package com.noname.pvpcage.builder;

import com.noname.pvpcage.utilities.generators.SchemeRecipment;
import com.noname.pvpcage.utilities.generators.SchemeStruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.util.org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class CageBuilder {

    //Default Cage World Name
    private static final String CAGE_WORLD = "PVPCageArenas";

    public static World createCageWorld() {
        World newWorld = null;

        WorldCreator wc = new WorldCreator(CAGE_WORLD);
        wc.environment(World.Environment.NORMAL);
        wc.generator(new CageWorldGenerator());

        newWorld = wc.createWorld();
        newWorld.setAnimalSpawnLimit(0);
        newWorld.setPVP(true);
        newWorld.setDifficulty(Difficulty.PEACEFUL);
        newWorld.setStorm(false);
        newWorld.setThundering(false);
        newWorld.setWaterAnimalSpawnLimit(0);
        newWorld.setTicksPerAnimalSpawns(0);
        newWorld.setTime(0);
        
        createSpawnPlatform();

        return newWorld;
    }
    
    public static boolean deleteCageWorld() {
        Location mainWorld = Bukkit.getWorlds().get(0).getSpawnLocation();
        
        World cgWorld = getCageWorld();
        for(Player p: Bukkit.getOnlinePlayers()){
            if(p.getWorld() == cgWorld){
                p.teleport(mainWorld);
            }
        }

        try { 
            Bukkit.getServer().unloadWorld(CAGE_WORLD, true);
            File f = new File(new File(".").getAbsolutePath() + File.separator + CAGE_WORLD);
            FileUtils.deleteDirectory(f);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private static void createSpawnPlatform() {
        World w = getCageWorld();
        SchemeStruct ss = new SchemeStruct("A", new String[][]{
            new String[]{
                "AAA",
                "A~A",
                "AAA"
            },
            new String[]{
                "AAA",
                "AWA",
                "AAA"
            },
            new String[]{
                "AWA",
                "WWW",
                "AWA"
            },
            new String[]{
                "WWW",
                "WWW",
                "WWW"
            },
            new String[]{
                "WAW",
                "AAA",
                "WAW"
            },
            new String[]{
                "WAW",
                "AAA",
                "WAW"
            },
            new String[]{
                "BAB",
                "AEA",
                "BAB"
            }
        }, new SchemeRecipment[]{
            new SchemeRecipment("W", Material.WOOD),
            new SchemeRecipment("B", Material.BOOKSHELF),
            new SchemeRecipment("E", Material.ENCHANTMENT_TABLE)
        });

        ss.build(new Location(w, 0, 12, 0));

    }

    public static World getCageWorld() {
        World w = Bukkit.getWorld(CAGE_WORLD);
        if (w == null) {
            w = createCageWorld();
        }

        return w;
    }

    public static void teleportToCageWorld(Player player) {
        Location spawn = getCageWorld().getSpawnLocation();
        int posY = 0;
        while (spawn.add(0, posY, 0).getBlock().getType() == Material.AIR) {
            posY++;//getting upper location
        }
        player.teleport(getCageWorld().getSpawnLocation().add(0.5, posY, 0.5));
    }
    
    private static ArrayList<Cage> onlineCages = new ArrayList<>();
    private static ArrayList<Point> possibleCagesLocs;
    static {
        onlineCages.add(new Cage(CageType.WALLS) {          
            @Override
            public void calculateCuboid(Location loc) {
                cageCuboid = new CageCuboid(20);
            }         
            @Override
            public void onCreateBattle(Location loc) {
            }           
            @Override
            public void onRemoveBattle() {
            }
        });
        
        genPossibleCagesLocs(30);
    }
    
    private static void genPossibleCagesLocs(int size) {
        possibleCagesLocs = new ArrayList();
        Location sp = getCageWorld().getSpawnLocation();
        
        double o = 0D;
        double locationToSpawn = 0D, slice = 0D;
        int i = 0;
        
        int MAX = 8;
        
        for (int j = 0; j < size ;j++) {
            if(i == 0) {
                o += 50.0;
                locationToSpawn = Math.sqrt(Math.pow(o, 2.0) + Math.pow(o, 2.0));
                slice = (2 * 3.14) / ((double) MAX);
            }
            possibleCagesLocs.add(
                    new Point(
                            sp.getX() + locationToSpawn * Math.sin(i * slice),
                            sp.getZ() + locationToSpawn * Math.cos(i * slice)
                    )
            );

            i++;
            if(i == MAX) {
                i = 0;
                MAX *= 2;
            }
        }
    }
    
    public static final int BUILD_Y = 32;
    private static class Point { 
        public double x, z;
        
        Point (double x, double z) {
            this.x = x;
            this.z = z;
        }
    }
    
    public static void buildCage(Cage cage) {
        if(onlineCages.size() == possibleCagesLocs.size()) {
            genPossibleCagesLocs(possibleCagesLocs.size() + 10);
        }

        Location target = null;
        
        for(Point p: possibleCagesLocs) {
            Location l = new Location(getCageWorld(), p.x, BUILD_Y, p.z);
            cage.calculateCuboid(l);
            boolean collide = false;
            CageCuboid cc = cage.getCageCuboid();
            for(Cage c: onlineCages) {
                if(c.getCageCuboid().isIn(cc)) {
                    collide = true;
                    break;
                }
            }
            
            if(!collide) {
                target = l;
                break;
            }
        }
        
        if(target != null) {
            cage.onCreateBattle(target);
            onlineCages.add(cage);
        }
        else {
            genPossibleCagesLocs(possibleCagesLocs.size() + 20);
            buildCage(cage);
        }
    }
}
