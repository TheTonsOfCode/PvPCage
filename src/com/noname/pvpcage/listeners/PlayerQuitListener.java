package com.noname.pvpcage.listeners;

import com.noname.pvpcage.managers.UserManager;
import com.noname.pvpcage.objects.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuitListener(PlayerQuitEvent e) {
        System.out.println("dziala!");
        User user = UserManager.getUser(e.getPlayer().getUniqueId());
        user.saveToMySQL();
    }

}
