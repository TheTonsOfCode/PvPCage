package com.gmail.desk1123.managers;

import com.gmail.desk1123.objets.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

}
