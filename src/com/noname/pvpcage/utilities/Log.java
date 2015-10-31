package com.noname.pvpcage.utilities;

import com.noname.pvpcage.PvPCage;
import org.bukkit.command.ConsoleCommandSender;

public enum Log {

    ERROR(1, 4),
    INFO(2, 2),
    DEBUG(3, 3);
    
    protected int level, color;
    
    private Log(int level, int color) {
        this.level = level;
        this.color = color;
    }
    
    public void print(String msg) {
        print(this, msg);
        //dsad
    }

    private static final ConsoleCommandSender console = PvPCage.getInstance().getServer().getConsoleSender();

    private static void print(Log log, String msg) {
        if(log.level <= Configuration.LOGGER_LEVEL) {
            console.sendMessage(Utils.fixColor("&8[&" + log.color + log.name() + "&8] &e>>  &6" + msg));
        }
    }
}

/*
 level 0 - null
 level 1 - error
 level 2 - error and info
 level 3 - error and info and debug
 */
    