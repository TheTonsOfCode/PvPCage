package com.noname.pvpcage.hooks;

import org.bukkit.plugin.PluginManager;

public class WorldEditHook<WorldEditPlugin> extends Hook {
    
    private WorldEditPlugin hook;
    
    public WorldEditPlugin getWorldGuard() {
        return hook;
    }

    public WorldEditHook() {
        super("WorldEdit");
    }

    @Override
    protected boolean onHookConnect(PluginManager pluginManager) {
        hook = ((WorldEditPlugin) pluginManager.getPlugin(getHookName()));
        if (hook == null) {
            connectMessage(false);
            return false;
        }
        connectMessage(true);
        return true;    
    }

}
