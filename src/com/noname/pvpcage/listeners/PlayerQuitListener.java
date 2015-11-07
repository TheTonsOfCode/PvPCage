package com.noname.pvpcage.listeners;

import com.noname.pvpcage.PvPCage;
import com.noname.pvpcage.managers.UserManager;
import com.noname.pvpcage.objects.User;
import com.noname.pvpcage.utilities.data.MySQL;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuitListener(PlayerQuitEvent e) {
        if(PvPCage.getInstance().getMySQL().getConnection() == null) {
            MySQL.LOG_ERROR.print("&cNieudany zapis podczas wyjscia gracza!");
            return;
        }
        User user = UserManager.getUser(e.getPlayer().getUniqueId());
        user.saveToMySQL();
    }

}
