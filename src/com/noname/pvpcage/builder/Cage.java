package com.noname.pvpcage.builder;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

public class Cage {
    
    protected List<Location> blocks = new ArrayList<>();
    protected CageCuboid cageCuboid;
    private Location l1, l2;
    private String schemName;
    
    public Cage() {
        cageCuboid = new CageCuboid(new Location(CageBuilder.getCageWorld(), 0, 0, 0), 20);
    }
    
    public Cage(String schemName) {
        this();
        this.schemName = schemName;
        WESchematic.SchematicCorners sc = new WESchematic(CageBuilder.getCageWorld()).getSchematicCorners(schemName);
        this.l1 = sc.l1;
        this.l2 = sc.l2;
    }
    
    public CageCuboid getCageCuboid() {
        return cageCuboid;
    }
    
    public CageConfigFile getCageConfigFile() {
        return CageConfigFile.getCageConfigFile(schemName);
    }
    
    public void calculateCuboid(Location loc) {
        cageCuboid = new CageCuboid(l1, l2, loc);
    }
    
    public void onCreateBattle(Location loc){
        new WESchematic(CageBuilder.getCageWorld()).loadSchematic(schemName, loc);
    }
    
    public void onRemoveBattle(){
        
    }
}
