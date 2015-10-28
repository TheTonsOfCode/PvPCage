package com.noname.pvpcage.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TestListener implements Listener {
    
    @EventHandler
    public void join(PlayerJoinEvent e) {
        Bukkit.broadcastMessage(">> " + e.getPlayer().getName());
    }

}
