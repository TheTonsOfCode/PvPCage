package com.noname.pvpcage.hooks;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.plugin.PluginManager;

public class WorldEditHook extends Hook {
    
    private static WorldEditPlugin hook;
    
    public static WorldEditPlugin getWorldEdit() {
        return hook;
    }

    public WorldEditHook() {
        super("WorldEdit");
    }

    @Override
    public boolean onHookConnect(PluginManager pluginManager) {
        hook = ((WorldEditPlugin) pluginManager.getPlugin(getHookName()));
        if (hook == null) {
            connectMessage(false);
            return false;
        }
        connectMessage(true);
        return true;    
    }

}
