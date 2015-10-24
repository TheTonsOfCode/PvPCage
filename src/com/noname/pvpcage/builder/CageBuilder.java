package com.noname.pvpcage.builder;

import com.noname.pvpcage.utilities.generators.SchemeRecipment;
import com.noname.pvpcage.utilities.generators.SchemeStruct;
import java.util.ArrayList;
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
        player.teleport(getCageWorld().getSpawnLocation().add(0, posY, 0));
    }
    
    private static ArrayList<Cage> onlineCages = new ArrayList<>();

    public static void buildCage(Cage cage) {
        
    }
}
