package com.noname.pvpcage.objects;

import com.noname.pvpcage.PvPCage;
import com.noname.pvpcage.Test.Item;
import com.noname.pvpcage.Test.ItemManager;
import com.noname.pvpcage.managers.TeamManager;
import com.noname.pvpcage.utilities.Log;
import com.noname.pvpcage.utilities.Msg;
import com.noname.pvpcage.utilities.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;

public class User {

    private String name;
    private UUID uuid;
    private int winDuel;
    private int loseDuel;
    private int escapeDuel;
    private int points;
    private long lastSave;
    private Team team;
    private List<User> victims = new ArrayList<>();
    private Player player;
    private List<Item> selectedItem = new ArrayList<>();

    public User(UUID uuid) {
        this.uuid = uuid;
        winDuel = 0;
        name = "";
        loseDuel = 0;
        escapeDuel = 0;
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

    public void setVictims(List<User> victims) {
        this.victims = victims;
    }

    public List<User> getVictims() {
        return victims;
    }

    public void addVictim(User u) {
        if (!this.victims.contains(u)) {
            this.victims.add(u);
        }
    }

    public void removeVictim(User u) {
        if (this.victims.contains(u)) {
            this.victims.remove(u);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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

    private void loadVictims() {
        Connection conn = PvPCage.getInstance().getMySQL().getConnection();
        if (conn == null) {
            try {
                conn = PvPCage.getInstance().getMySQL().openConnection();
            } catch (Exception e) {
                Log.ERROR.prefix("MySQL").print("&4Nie mozna wczytaj ofiar z powodu braku polaczenia do mysql!");
                return;
            }
        }

        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM `VictimsDataPvPCage` WHERE `killer_uuid`=?");//change names and where `somethink`
            st.setString(1, uuid.toString());
            rs = st.executeQuery();
            if (rs.next()) {
                User u = new User(UUID.fromString(rs.getString("victim_uuid")));
                u.loadFromMySQL();
                victims.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PvPCage.getInstance().getMySQL().closeResources(rs, st);

    }

    private void saveVictims() {
        Connection conn = PvPCage.getInstance().getMySQL().getConnection();
        if (conn == null) {
            try {
                conn = PvPCage.getInstance().getMySQL().openConnection();
            } catch (Exception e) {
                Log.ERROR.prefix("MySQL").print("&4Nie mozna zapisac ofiar z powodu braku polaczenia do mysql!");
                return;
            }
        }
        /*
         Statement st = null;
         Set<Integer> toRem = getRecordsToRemove(player);
         try {
         st = connection.createStatement();
         */
        PreparedStatement st = null;
        try {
            for (User victim : victims) {
                st.addBatch(Utils.loadQueryNew("insertVictimsData"));
                st.setString(1, uuid.toString());
                st.setString(2, victim.getUuid().toString());
                st.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PvPCage.getInstance().getMySQL().closeResources(null, st);
    }

    public void loadFromMySQL() {
        Connection conn = PvPCage.getInstance().getMySQL().getConnection();
        if (conn == null) {
            try {
                conn = PvPCage.getInstance().getMySQL().openConnection();
            } catch (Exception e) {
                Log.ERROR.prefix("MySQL").print("&4Nie mozna wczytaj gracza z powodu braku polaczenia do mysql!");
                return;
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
                team = TeamManager.getTeam(rs.getString("team"));
                loseDuel = rs.getInt("loseduel");
                winDuel = rs.getInt("winduel");
                escapeDuel = rs.getInt("escapeduel");
                points = rs.getInt("points");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadVictims();
        PvPCage.getInstance().getMySQL().closeResources(rs, st);
    }

    public void saveToMySQL() {
        Connection conn = PvPCage.getInstance().getMySQL().getConnection();
        if (conn == null) {
            try {
                conn = PvPCage.getInstance().getMySQL().openConnection();
            } catch (Exception e) {
                Log.ERROR.prefix("MySQL").print("&4Nie mozna zapisac gracza z powodu braku polaczenia do mysql!");
                return;
            }
        }
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(Utils.loadQueryNew("insertUserData"));
            st.setString(1, uuid.toString());
            st.setString(2, name);
            st.setString(3, team.getTag());
            st.setInt(4, loseDuel);
            st.setInt(5, winDuel);
            st.setInt(6, escapeDuel);
            st.setInt(7, points);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        saveVictims();
        PvPCage.getInstance().getMySQL().closeResources(null, st);
    }
}
