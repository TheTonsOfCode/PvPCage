package com.noname.pvpcage.managers;

import com.noname.pvpcage.objects.User;
import com.noname.pvpcage.utilities.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author dekros987
 */
public class UserManager {

    public static List<User> users = new ArrayList<>();

    public static void addUser(User u) {
        users.add(u);
    }

    public static void removeUser(User u) {
        if (users.contains(u)) {
            users.remove(u);
        }
    }

    public static List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public static boolean isUser(User u) {
        return users.contains(u);
    }

    public static User getUser(UUID uuid) {
        for (User u : UserManager.getUsers()) {
            if (u.getUuid().equals(uuid)) {
                return u;
            }
        }
        return new User(uuid);
    }

    /*
     private String name;
     private UUID uuid;
     private int winDuel;
     private int loseDuel;
     private int escapeDuel;
     private int points;
     private long lastSave;
     private String team;
     private long lastSeen;// to check if somethink....
     private List<String> victims = new ArrayList<>();
     private Player player;
     private List<Item> selectedItem = new ArrayList<>();
     */
    public static void saveUsersToMySQL() {
        int saved = 0;
        for (User user : users) {
            user.saveToMySQL();
            saved++;
        }
        Log.INFO.print("&2Zapisano: &6&l" + saved + "&r&2 Graczy");
    }

}
