package com.noname.pvpcage.utilities;

import com.noname.pvpcage.PvPCage;
import org.bukkit.command.ConsoleCommandSender;

public class LoggerUtils {

    //private static final Logger logger = Logger.getLogger("Minecraft");
    private static final ConsoleCommandSender logger = PvPCage.getInstance().getServer().getConsoleSender();
    /*
     level 0 - null
     level 1 - error
     level 2 - error and info
     level 3 - error and info and debug
     */

    public static void debug(String msg) {
        if (Configuration.LOGGER_LEVEL == 3) {
            logger.sendMessage(Utils.fixColor("&8[&3Debug&8] &e>>  &6" + msg));
        }
    }

    public static void error(String msg) {
        if (Configuration.LOGGER_LEVEL == 1 || Configuration.LOGGER_LEVEL == 2 || Configuration.LOGGER_LEVEL == 3) {
            logger.sendMessage(Utils.fixColor("&8[&4Error&8] &e>>  &6" + msg));
        }
    }

    public static void info(String msg) {
        if (Configuration.LOGGER_LEVEL == 2 || Configuration.LOGGER_LEVEL == 3) {
            logger.sendMessage(Utils.fixColor("&8[&2Info&8] &e>>  &6" + msg));
        }
    }
}
