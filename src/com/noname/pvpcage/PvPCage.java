package com.noname.pvpcage;

import com.noname.pvpcage.builder.CageBuilder;
import com.noname.pvpcage.configuration.CONFIG;
import com.noname.pvpcage.configuration.CfgDatabase;
import com.noname.pvpcage.managers.TeamManager;
import com.noname.pvpcage.utilities.Log;
import com.noname.pvpcage.utilities.data.MySQL;
import com.noname.pvpcage.utilities.instance.PIBukkitListeners;
import com.noname.pvpcage.utilities.instance.PICommand;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import de.slikey.effectlib.EffectManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PvPCage extends JavaPlugin {

    private static PvPCage instance;
    private EffectManager effectManager;
    private MySQL sql;

    public void onEnable() {
        instance = this;
        effectManager = new EffectManager(instance);
        saveDefaultConfig();
        CfgDatabase.loadConfiguration();
        CONFIG.loadConfiguration();
        sql = new MySQL();
        sql.createTables();
        TeamManager.loadTeamsFromMySQL();
        new PICommand(this).instanceAllAt("com.noname.pvpcage.commands");
        new PIBukkitListeners(this).instanceAllAt("com.noname.pvpcage.listeners");

        CageBuilder.refreshSchematicsNames();
    }

    public void onDisable() {
        effectManager.dispose();
    }
    
    public static PvPCage getInstance() {
        return instance;
    }

    public MySQL getMySQL() {
        return sql;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    public WorldEditPlugin getWorldEdit() {
        WorldEditPlugin we = ((WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit"));
        if (we == null) {
            Log.INFO.print("WorldEdit not hooked.");
            return null;
        }
        Log.INFO.print("WorldEdit hooked.");
        return we;    
    }

}
