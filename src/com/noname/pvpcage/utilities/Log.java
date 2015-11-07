package com.noname.pvpcage.utilities;

import com.noname.pvpcage.PvPCage;
import com.noname.pvpcage.configuration.CONFIG;
import org.bukkit.command.ConsoleCommandSender;

public enum Log {

    ERROR(1, 4),
    INFO(2, 2),
    DEBUG(3, 3);
    
    protected int level, color;
    protected String prefix;
    
    private Log(int level, int color) {
        this.level = level;
        this.color = color;
    }
    
    public void print(String msg) {
        print(this, prefix, msg);
    }
    
    public Log prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    private static final ConsoleCommandSender console = PvPCage.getInstance().getServer().getConsoleSender();

    private static void print(Log log, String prefix, String msg) {
        if(log.level <= CONFIG.LOGGER_LEVEL) {
            console.sendMessage(Utils.fixColor("&8[&" + log.color + log.name() + "&8]" + (prefix != null ? "<&7" + prefix + "&8>" : "") + " &e>>  &6" + msg));
        }
    }
}

/*
 level 0 - null
 level 1 - error
 level 2 - error and info
 level 3 - error and info and debug
 */
    