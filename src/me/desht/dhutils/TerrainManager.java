//package me.desht.dhutils;
//
//import com.noname.pvpcage.hooks.WorldEditHook;
//import java.io.File;
//import java.io.IOException;
//
//import org.bukkit.Location;
//import org.bukkit.World;
//import org.bukkit.entity.Player;
//
//import com.sk89q.worldedit.CuboidClipboard;
//import com.sk89q.worldedit.EditSession;
//import com.sk89q.worldedit.EmptyClipboardException;
//import com.sk89q.worldedit.FilenameException;
//import com.sk89q.worldedit.LocalPlayer;
//import com.sk89q.worldedit.LocalSession;
//import com.sk89q.worldedit.MaxChangedBlocksException;
//import com.sk89q.worldedit.Vector;
//import com.sk89q.worldedit.WorldEdit;
//import com.sk89q.worldedit.bukkit.BukkitWorld;
//import com.sk89q.worldedit.data.DataException;
//import com.sk89q.worldedit.schematic.SchematicFormat;
//
///**
// * @author desht
// *
// * A wrapper class for the WorldEdit terrain loading & saving API to make things
// * a little simple for other plugins to use.
// */
//public class TerrainManager {
//
//    private static final String EXTENSION = "schematic";
//
//    private final WorldEdit we;
//    private final LocalSession localSession;
//    private final EditSession editSession;
//    private final LocalPlayer localPlayer;
//
//    /**
//     * Constructor
//     *
//     * @param wep	the WorldEdit plugin instance
//     * @param player	the player to work with
//     */
//    public TerrainManager(Player player) {
//        we = WorldEditHook.getWorldEdit().getWorldEdit();
//        localPlayer = WorldEditHook.getWorldEdit().wrapPlayer(player);
//        localSession = we.getSession(localPlayer);
//        editSession = localSession.createEditSession(localPlayer);
//    }
//
//    /**
//     * Constructor
//     *
//     * @param wep	the WorldEdit plugin instance
//     * @param world	the world to work in
//     */
//    public TerrainManager(World world) {
//        we = WorldEditHook.getWorldEdit().getWorldEdit();
//        localPlayer = null;
//        localSession = new LocalSession(we.getConfiguration());
//        editSession = new EditSession(new BukkitWorld(world), we.getConfiguration().maxChangeLimit);
//    }
//
//    /**
//     * Write the terrain bounded by the given locations to the given file as a
//     * MCedit format schematic.
//     *
//     * @param saveFile	a File representing the schematic file to create
//     * @param l1	one corner of the region to save
//     * @param l2	the corner of the region to save, opposite to l1
//     * @throws DataException
//     * @throws IOException
//     */
//    public void saveTerrain(File saveFile, Location l1, Location l2) throws FilenameException, DataException, IOException {
//        Vector min = getMin(l1, l2);
//        Vector max = getMax(l1, l2);
//
//        saveFile = we.getSafeSaveFile(localPlayer,
//                saveFile.getParentFile(), saveFile.getName(),
//                EXTENSION, new String[]{EXTENSION});
//
//        editSession.enableQueue();
//        CuboidClipboard clipboard = new CuboidClipboard(max.subtract(min).add(new Vector(1, 1, 1)), min);
//        clipboard.copy(editSession);
//        SchematicFormat.MCEDIT.save(clipboard, saveFile);
//        editSession.flushQueue();
//    }
//
//    /**
//     * Load the data from the given schematic file and paste it at the given
//     * location. If the location is null, then paste it at the saved data's
//     * origin.
//     *
//     * @param saveFile	a File representing the schematic file to load
//     * @param loc	the location to paste the clipboard at (may be null)
//     * @throws FilenameException
//     * @throws DataException
//     * @throws IOException
//     * @throws MaxChangedBlocksException
//     * @throws EmptyClipboardException
//     */
//    public void loadSchematic(File saveFile, Location loc) throws FilenameException, DataException, IOException, MaxChangedBlocksException, EmptyClipboardException {
//        saveFile = we.getSafeSaveFile(localPlayer,
//                saveFile.getParentFile(), saveFile.getName(),
//                EXTENSION, new String[]{EXTENSION});
//
//        editSession.enableQueue();
//        localSession.setClipboard(SchematicFormat.MCEDIT.load(saveFile));
//        localSession.getClipboard().place(editSession, getPastePosition(loc), false);
//        editSession.flushQueue();
//        we.flushBlockBag(localPlayer, editSession);
//    }
//
//    /**
//     * Load the data from the given schematic file and paste it at the saved
//     * clipboard's origin.
//     *
//     * @param saveFile
//     * @throws FilenameException
//     * @throws DataException
//     * @throws IOException
//     * @throws MaxChangedBlocksException
//     * @throws EmptyClipboardException
//     */
//    public void loadSchematic(File saveFile) throws FilenameException, DataException, IOException, MaxChangedBlocksException, EmptyClipboardException {
//        loadSchematic(saveFile, null);
//    }
//
//    private Vector getPastePosition(Location loc) throws EmptyClipboardException {
//        if (loc == null) {
//            return localSession.getClipboard().getOrigin();
//        } else {
//            return new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
//        }
//    }
//
//    private Vector getMin(Location l1, Location l2) {
//        return new Vector(
//                Math.min(l1.getBlockX(), l2.getBlockX()),
//                Math.min(l1.getBlockY(), l2.getBlockY()),
//                Math.min(l1.getBlockZ(), l2.getBlockZ())
//        );
//    }
//
//    private Vector getMax(Location l1, Location l2) {
//        return new Vector(
//                Math.max(l1.getBlockX(), l2.getBlockX()),
//                Math.max(l1.getBlockY(), l2.getBlockY()),
//                Math.max(l1.getBlockZ(), l2.getBlockZ())
//        );
//    }
//}
//
///*
//    Location l1 = // Location representing one corner of the region
//    Location l2 = // Location representing the corner opposite to <l1>
//    // ensure WorldEdit is available
//    WorldEditPlugin wep = (WorldEditPlugin)Bukkit.getPluginManager.getPlugin("WorldEdit");
//    if (wep == null) {
//      // then don't try to use TerrainManager!
//    }
//
//    // create a terrain manager object
//    TerrainManager tm = new TerrainManager(wep, player);
//    // OR - without needing an associated Player
//    TerrainManager tm = new TerrainManager(wep, world);
//
//    // don't include an extension - TerrainManager will auto-add ".schematic"
//    File saveFile = new File(plugin.getDataFolder(), "backup1");
//
//    // save the terrain to a schematic file
//    try {
//      tm.saveTerrain(saveFile, l1, l2);
//    } catch (FilenameException e) {
//      // thrown by WorldEdit - it doesn't like the file name/location etc.
//    } catch (DataException e) {
//      // thrown by WorldEdit - problem with the data
//    } catch (IOException e) {
//      // problem with creating/writing to the file
//    }
//
//    // reload a schematic
//    try {
//      // reload at the given location
//      Location location = new Location(x, y, z);
//      tm.loadSchematic(saveFile, location);
//      // OR
//      // reload at the same place it was saved
//      tm.loadSchematic(saveFile);
//    } catch (FilenameException e) {
//      // thrown by WorldEdit - it doesn't like the file name/location etc.
//    } catch (DataException e) {
//      // thrown by WorldEdit - problem with the data
//    } catch (IOException e) {
//      // problem with opening/reading the file
//    } catch (MaxChangedBlocksException e) {
//      // thrown by WorldEdit - the schematic is larger than the configured block limit for the player
//    } catch (EmptyClipboardException e) {
//    // thrown by WorldEdit - should be self-explanatory
//    }
//*/