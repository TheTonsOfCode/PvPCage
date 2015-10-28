package com.noname.pvpcage.objects;

import com.noname.pvpcage.PvPCage;
import com.noname.pvpcage.Test.Item;
import com.noname.pvpcage.Test.ItemManager;
import com.noname.pvpcage.managers.FileManager;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
    private String team;
    private long lastSeen;// to check if somethink....
    private List<String> victims = new ArrayList<>();
    private Player player;
    private List<Item> selectedItem = new ArrayList<>();

    public User(UUID uuid) {
        winDuel = 0;
        name = "";
        loseDuel = 0;
        escapeDuel = 0;
        lastSave = System.currentTimeMillis();
        points = 0;
        team = null;
        player = null;
        victims.clear();
        selectedItem = ItemManager.getItems();
    }

    public List<Item> getSelectedItems() {
        return selectedItem;
    }

    public void clearSelctedItems() {
        selectedItem = ItemManager.getItems();
    }

    public List<String> getVictims() {
        return victims;
    }

    public void addVictim(String uuid) {
        if (!this.victims.contains(uuid)) {
            this.victims.add(uuid);
        }
    }

    public void removeVictim(String uuid) {
        if (this.victims.contains(uuid)) {
            this.victims.remove(uuid);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
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

    private String victimsToString() {
        String s = null;
        for (String name : victims) {
            s = name + ",";
        }
        return s;
    }

    private void stringToVictims(String s) {
        String[] name = s.split(",");
        for (String name1 : name) {
            victims.add(name1);
        }
    }

    public void loadFromMySQL() {
        Connection conn = PvPCage.getMySQL().getConnection();
        if (conn == null) {
            try {
                conn = PvPCage.getMySQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM `UserDataPvPCage` WHERE `uuid`=?");
            st.setString(1, uuid.toString());
            rs = st.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
                team = rs.getString("team");
                loseDuel = rs.getInt("loseduel");
                winDuel = rs.getInt("winduel");
                escapeDuel = rs.getInt("escapeduel");
                points = rs.getInt("points");
                stringToVictims(rs.getString("victims"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PvPCage.getMySQL().closeResources(rs, st);
    }

    public void saveToMySQL() {
        Connection conn = PvPCage.getMySQL().getConnection();
        if (conn == null) {
            try {
                conn = PvPCage.getMySQL().openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        PreparedStatement st = null;
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO `UserDataPvPCage` (`uuid`, `name`, `team`, `loseduel`, `winduel`,`escapeduel`,`points`, `victims`)")
                .append("VALUES (?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE ")
                .append(" `uuid`=VALUES(`uuid`), `name`=VALUES(`name`), `team`=VALUES(`team`), ")
                .append("`loseduel`=VALUES(`loseduel`), `winduel`=VALUES(`winduel`),")
                .append("`escapeduel`=VALUES(`escapeduel`), `points`=VALUES(`points`),")
                .append("`victims`=VALUES(`victims`)");
        try {
            st = conn.prepareStatement(query.toString());
            st.setString(1, uuid.toString());
            st.setString(2, name);
            st.setString(3, team);
            st.setInt(4, loseDuel);
            st.setInt(5, winDuel);
            st.setInt(6, escapeDuel);
            st.setInt(7, points);
            st.setString(8, victimsToString());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PvPCage.getMySQL().closeResources(null, st);
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
     private List<UUID> victims = new ArrayList<>();
     private Player player;
     private List<Item> selectedItem = new ArrayList<>();
     */
    public void saveToFile() {
        File f = new File(FileManager.getUserData(), "");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        FileConfiguration yml = YamlConfiguration.loadConfiguration(f);
        yml.set("Name", name);
        yml.set("Uuid", uuid);
        yml.set("WinDuel", winDuel);
        yml.set("LoseDuel", loseDuel);
        yml.set("EscapeDuel", escapeDuel);
        yml.set("Points", points);
        yml.set("LastSeen", lastSeen);
        yml.set("Team", team);
        yml.set("Victims", victims);
        try {
            yml.save(f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadFromFile() {
        File f = new File(FileManager.getUserData(), "");
        if (!f.exists()) {
            return;
        }
        FileConfiguration yml = YamlConfiguration.loadConfiguration(f);
        name = yml.getString("Name");
        uuid = UUID.fromString(yml.getString("Uuid"));
        winDuel = yml.getInt("WinDuel");
        loseDuel = yml.getInt("LoseDuel");
        escapeDuel = yml.getInt("EscapeDuel");
        points = yml.getInt("Points");
        lastSeen = yml.getLong("LastSeen");
        team = yml.getString("team");
        victims = yml.getStringList("Victims");
    }

}
