package com.noname.pvpcage.utilities.instance;

import java.lang.reflect.Type;
import java.util.logging.Logger;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/*
    Super Instance Maker
    @author = Merbio
*/

public class PIBukkitListeners extends PackageInstancer {

    private PluginManager pm;
    
    public PIBukkitListeners(Plugin plugin) {
        super(plugin, "BukkitListeners");
        pm = plugin.getServer().getPluginManager();
    }

    @Override
    protected void onPositiveCheck(Class c) {
        for (Type iface : c.getGenericInterfaces()) {
            if (iface == Listener.class) {
                try {
                    pm.registerEvents((Listener) c.newInstance(), plugin);
                    mb++;
                } catch (Exception ex) {
                    Logger.getLogger(ex.getMessage());
                }
            }
        }
    }
}
