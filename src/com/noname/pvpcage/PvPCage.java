package com.noname.pvpcage;

import com.noname.pvpcage.utilities.Configuration;
import com.noname.pvpcage.utilities.MessageManager;
import com.noname.pvpcage.utilities.instance.PIBukkitListeners;
import com.noname.pvpcage.utilities.instance.PICommand;
import de.slikey.effectlib.EffectManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.plugin.java.JavaPlugin;

public class PvPCage extends JavaPlugin {

    private static PvPCage instance;
    private static MessageManager msgManager;
    private static EffectManager effectManager;

    public void onEnable() {
        instance = this;
        msgManager = new MessageManager();
        effectManager = new EffectManager(instance);
        saveDefaultConfig();
        Configuration.loadConfiguration();

        new PICommand(this).instanceAllAt("com.noname.pvpcage.commands");
        new PIBukkitListeners(this).instanceAllAt("com.noname.pvpcage.listeners");
    }

    public void onDisable() {
        effectManager.dispose();
//        HandlerList.unregisterAll((Listener) this);
    }

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
