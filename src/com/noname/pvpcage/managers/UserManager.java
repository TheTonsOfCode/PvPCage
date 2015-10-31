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
    public static void saveUsersToFile() {
        int saved = 0;
        for (User u : users) {
            File f = new File(FileManager.getUserData(), u.getUuid() + ".yml");
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            FileConfiguration yml = YamlConfiguration.loadConfiguration(f);
            yml.set("Name", u.getName());
            yml.set("Uuid", u.getUuid());
            yml.set("WinDuel", u.getWinDuel());
            yml.set("LoseDuel", u.getLoseDuel());
            yml.set("EscapeDuel", u.getEscapeDuel());
            yml.set("Points", u.getPoints());
            yml.set("Team", u.getTeam());
            yml.set("LastSeen", u.getLastSeen());
            yml.set("Victims", u.getVictims());
            saved++;
        }
        Log.INFO.print("&2Zapisano: &6&l" + saved + "&r&2 Graczy");
    }

    public static void loadUsersFromFile() {
        int load = 0;
        for (File f : FileManager.getUserData().listFiles()) {
            FileConfiguration yml = YamlConfiguration.loadConfiguration(f);
            User u = new User(UUID.fromString(yml.getString("Uuid")));
            u.setName(yml.getString("Name"));
            u.setUuid(UUID.fromString(yml.getString("Uuid")));
            u.setWinDuel(yml.getInt("WinDuel"));
            u.setLoseDuel(yml.getInt("LoseDuel"));
            u.setEscapeDuel(yml.getInt("EscapeDuel"));
            u.setPoints(yml.getInt("Points"));
            u.setLastSeen(yml.getLong("LastSeen"));
            u.setTeam(yml.getString("team"));
            u.setVictims(yml.getStringList("Victims"));
            load++;
        }
        Log.INFO.print("&2Wczytano: &6&l" + load + "&r&2 Graczy");
    }

}
