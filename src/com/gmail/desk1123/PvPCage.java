package com.gmail.desk1123;


import de.slikey.effectlib.EffectManager;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
/**
 *
 * @author dekros987
 */
public class PvPCage extends JavaPlugin {

    private static PvPCage instance;

    private static EffectManager effectManager;

    public void onEnable() {
        instance = this;
        effectManager = new EffectManager(instance);

        getCommand("test").setExecutor(new Test());
    }

    public void onDisable() {
        effectManager.dispose();
        HandlerList.unregisterAll((Listener) this);
    }

    public static EffectManager getEffectManager(){
        return effectManager;
    }
    public static PvPCage getInstance() {
        return instance;
    }

}
