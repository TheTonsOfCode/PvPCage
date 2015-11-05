package com.noname.pvpcage.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Msg {

    public static final Server server = Bukkit.getServer();
    public static final ConsoleCommandSender console = server.getConsoleSender();
    
    public static void send(Player p, Object o) {
        send(p, o.toString());
    }
    
    public static void send(Player p, String msg) {
        p.sendMessage(Utils.fixColor(msg));
    }
    
    public static void broadcast(Object o) {
        broadcast(o.toString());
    }
    
    public static void broadcast(String message) {
        server.broadcastMessage(Utils.fixColor(message));
    }
    
    @Deprecated
    public static void console(Object o) {
        console(o.toString());
    }
    
    @Deprecated
    public static void console(String message) {
        console.sendMessage(Utils.fixColor(message));
    }
}
