package com.noname.pvpcage.hooks;

import com.noname.pvpcage.utilities.Log;
import org.bukkit.plugin.PluginManager;

public abstract class Hook {
    private String hookName;
    private boolean isHooked;
    private boolean isInConfigEnabled;
    
    public Hook(String hookName) {
        this.hookName = hookName;
    }

    public String getHookName() {
        return hookName;
    }
    
    public boolean isHooked() {
        return isHooked;
    }
    
    public boolean isInConfigEnabled() {
        return isInConfigEnabled;
    }
    
    public void checkHookIsActive(PluginManager pluginManager) {
        //Settings get isInConfigEnabled
        //if (true)
        isHooked = onHookConnect(pluginManager);
    }
    
    protected abstract boolean onHookConnect(PluginManager pluginManager);
    
    protected void connectMessage(boolean connected) {
        Log.DEBUG.print("Plugin: " + getHookName() + " " + (connected ? "" : "not ") + "hooked.");
    }
}
