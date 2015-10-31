package com.noname.pvpcage.listeners;

import com.noname.pvpcage.managers.TeamManager;
import com.noname.pvpcage.managers.UserManager;
import com.noname.pvpcage.objects.Team;
import com.noname.pvpcage.objects.User;
import com.noname.pvpcage.utilities.Log;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        System.out.println("dziala!");
        User user = new User(e.getPlayer().getUniqueId());
        user.loadFromMySQL();
        user.setPlayer(e.getPlayer());
        user.setName(e.getPlayer().getName());
        Team team = user.getTeam();
        if (team != null) {
            if (!team.containsMember(user)) {
                team.addMember(user);
            }
        }
        UserManager.addUser(user);
        Log.INFO.print("Stworzony nowy obiekt User dla gracza: " + e.getPlayer().getName());
    }

}
