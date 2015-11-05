package com.noname.pvpcage.builder;

import com.noname.pvpcage.PvPCage;
import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.FilenameException;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import java.util.ArrayList;

public class WESchematic {

    private static final String EXTENSION = "schematic";
    private static final String SCHEM_FOLDER = PvPCage.getInstance().getDataFolder().getAbsolutePath() + "/schems";
    private static final WorldEditPlugin WE_PLUGIN = PvPCage.getInstance().getWorldEdit();

    static {
        File f = new File(SCHEM_FOLDER);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    public static class SchematicCorners {

        public Location l1, l2;

        public SchematicCorners(Location l1, Location l2) {
            this.l1 = l1;
            this.l2 = l2;
        }
    }

    public static ArrayList<String> getSchematicsNames() {
        File folder = new File(SCHEM_FOLDER);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null || listOfFiles.length == 0) {
            return null;
        }

        ArrayList<String> names = new ArrayList<>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String[] s = file.getName().split("\\.");
                if (s[1].equals(EXTENSION)) {
                    names.add(s[0]);
                }
            }
        }

        return names;
    }

    private final WorldEdit worldEdit;
    private final LocalSession localSession;
    private final EditSession editSession;
    private final LocalPlayer localPlayer;

    public WESchematic(Player player) {
        worldEdit = WE_PLUGIN.getWorldEdit();
        localPlayer = WE_PLUGIN.wrapPlayer(player);
        localSession = worldEdit.getSession(localPlayer);
        editSession = localSession.createEditSession(localPlayer);
    }

    public WESchematic(World world) {
        worldEdit = WE_PLUGIN.getWorldEdit();
        localPlayer = null;
        localSession = new LocalSession(worldEdit.getConfiguration());
        editSession = new EditSession(new BukkitWorld(world), worldEdit.getConfiguration().maxChangeLimit);
    }

    public void saveSchematic(String schemName, Location l1, Location l2) {
        File saveFile = new File(SCHEM_FOLDER, schemName);
        Vector min = getMin(l1, l2);
        Vector max = getMax(l1, l2);
        try {
            saveFile = worldEdit.getSafeSaveFile(localPlayer,
                    saveFile.getParentFile(), saveFile.getName(),
                    EXTENSION, new String[]{EXTENSION});
        } catch (FilenameException ex) {
        }

        editSession.enableQueue();
        CuboidClipboard clipboard = new CuboidClipboard(max.subtract(min).add(new Vector(1, 1, 1)), min);
        clipboard.copy(editSession);
        try {
            SchematicFormat.MCEDIT.save(clipboard, saveFile);
        } catch (IOException ex) {
        } catch (DataException ex) {
        }
        editSession.flushQueue();
    }

    public void loadSchematic(String schemName, Location loc) {
        File saveFile = new File(SCHEM_FOLDER, schemName);
        try {
            saveFile = worldEdit.getSafeSaveFile(localPlayer,
                    saveFile.getParentFile(), saveFile.getName(),
                    EXTENSION, new String[]{EXTENSION});
        } catch (FilenameException ex) {
        }

        editSession.enableQueue();
        try {
            localSession.setClipboard(SchematicFormat.MCEDIT.load(saveFile));
        } catch (IOException ex) {
        } catch (DataException ex) {
        }

        CuboidClipboard c = null;
        Vector v = null;
        try {
            c = localSession.getClipboard();
            v = getPastePosition(loc);
        } catch (EmptyClipboardException ex) {
        }

        v = v.add(
                -(c.getWidth() >> 1),
                2, // 0 powinno byc
                -(c.getLength() >> 1)
        );

        try {
            localSession.getClipboard().place(editSession, v, false);
        } catch (EmptyClipboardException ex) {
        } catch (MaxChangedBlocksException ex) {
        }
        editSession.flushQueue();
        worldEdit.flushBlockBag(localPlayer, editSession);
    }

    public void loadSchematic(String schemName) {
        loadSchematic(schemName, null);
    }

    public SchematicCorners getSchematicCorners(String schemName) {
        File saveFile = new File(SCHEM_FOLDER, schemName);
        try {
            saveFile = worldEdit.getSafeSaveFile(localPlayer,
                    saveFile.getParentFile(), saveFile.getName(),
                    EXTENSION, new String[]{EXTENSION});
        } catch (FilenameException ex) {
        }

        editSession.enableQueue();
        try {
            localSession.setClipboard(SchematicFormat.MCEDIT.load(saveFile));
        } catch (IOException ex) {
        } catch (DataException ex) {
        }

        CuboidClipboard c = null;
        try {
            c = localSession.getClipboard();
        } catch (EmptyClipboardException ex) {
        }
        Location l = new Location(CageBuilder.getCageWorld(), 0, 0, 0);
        SchematicCorners sc = new SchematicCorners(l.clone(), l.clone().add(c.getWidth(), 0, c.getLength()));
        editSession.flushQueue();
        return sc;
    }

    private Vector getPastePosition(Location loc) throws EmptyClipboardException {
        if (loc == null) {
            return localSession.getClipboard().getOrigin();
        } else {
            return new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        }
    }

    private Vector getMin(Location l1, Location l2) {
        return new Vector(
                Math.min(l1.getBlockX(), l2.getBlockX()),
                Math.min(l1.getBlockY(), l2.getBlockY()),
                Math.min(l1.getBlockZ(), l2.getBlockZ())
        );
    }

    private Vector getMax(Location l1, Location l2) {
        return new Vector(
                Math.max(l1.getBlockX(), l2.getBlockX()),
                Math.max(l1.getBlockY(), l2.getBlockY()),
                Math.max(l1.getBlockZ(), l2.getBlockZ())
        );
    }
}

/*
 WESchematic s = new WESchematic(player);
 WESchematic s = new WESchematic(world);

 File saveFile = new File(plugin.getDataFolder(), "backup1");

 s.saveTerrain(saveFile, l1, l2);

 s.loadSchematic(saveFile, location);

 s.loadSchematic(saveFile);
 */
