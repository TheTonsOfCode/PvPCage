package com.noname.pvpcage.utilities.instance;

import com.noname.pvpcage.objects.Command;
import static com.noname.pvpcage.utilities.Utils.registerCommand;
import java.lang.reflect.Field;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

/*
    Super Instance Maker
    @author = Merbio
*/

public class PICommand extends PackageInstancer {

    public PICommand(Plugin plugin) {
        super(plugin, "MerbioCommand");
    }

    @Override
    protected void onPositiveCheck(Class c) {
        if (c.getGenericSuperclass() == Command.class) {
            try {
                registerCommand((Command) c.newInstance());
                mb++;
            } catch (Exception ex) {
                Logger.getLogger(ex.getMessage());
            }
        }
    }

    private void registerCommand(Command command) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(command.getName(), command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
