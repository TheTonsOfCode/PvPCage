package com.noname.pvpcage;

import com.noname.pvpcage.utilities.MessageManager;
import de.slikey.effectlib.EffectManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author dekros987
 */
public class PvPCage extends JavaPlugin {

    private static PvPCage instance;
    private static MessageManager msgManager;
    private static EffectManager effectManager;

    public void onEnable() {
        instance = this;
        msgManager = new MessageManager();
        effectManager = new EffectManager(instance);

        getCommand("cage").setExecutor(new Test());
    }

    public void onDisable() {
        effectManager.dispose();
//        HandlerList.unregisterAll((Listener) this);
    }

    /*
    jest sprawa
    jakbys mogl napisac co ma byc w danych klasach bo nie wiem co ma np byc w JustDuelAnywhereCage xD
    */
    public static EffectManager getEffectManager() {
        return effectManager;
    }

    public static PvPCage getInstance() {
        return instance;
    }

    public static MessageManager getMessageManager() {
        return msgManager;
    }

    public static void copy(InputStream resource, File to) {
        try {
            OutputStream out = new FileOutputStream(to);

            int size = 0;
            byte[] buffer = new byte[1024];

            while ((size = resource.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }

            resource.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
