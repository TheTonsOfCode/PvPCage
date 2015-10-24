package com.noname.pvpcage.objects;

import java.util.UUID;
import org.bukkit.entity.Player;

/**
 *
 * @author dekros987
 */
public class User {

    private String name;
    private UUID uuid;
    private int winDuel;
    private int loseDuel;
    private int escapeDuel;
    private int points;
    private long lastSave;
    //make invite

    public User(UUID uuid) {
        winDuel = 0;
        name = "";
        loseDuel = 0;
        escapeDuel = 0;
        lastSave = System.currentTimeMillis();
        points = 0;
    }

    public static User fromPlayer(Player player) {
        if (player != null) {
            User user = new User(player.getUniqueId());
            user.setName(player.getName());
            return user;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getWinDuel() {
        return winDuel;
    }

    public void setWinDuel(int winDuel) {
        this.winDuel = winDuel;
    }

    public int getLoseDuel() {
        return loseDuel;
    }

    public void setLoseDuel(int loseDuel) {
        this.loseDuel = loseDuel;
    }

    public int getEscapeDuel() {
        return escapeDuel;
    }

    public void setEscapeDuel(int escapeDuel) {
        this.escapeDuel = escapeDuel;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int rank) {
        this.points = rank;
    }

    public long getLastSave() {
        return lastSave;
    }

    public void setLastSave(long lastSave) {
        this.lastSave = lastSave;
    }

    public void loadFromMySQL() {

    }

    public void saveToMySQL() {

    }

}
