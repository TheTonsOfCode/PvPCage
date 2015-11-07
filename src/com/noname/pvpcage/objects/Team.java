package com.noname.pvpcage.objects;

import com.noname.pvpcage.PvPCage;
import com.noname.pvpcage.configuration.CONFIG;
import com.noname.pvpcage.managers.UserManager;
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

public class Team {

    private int max_size;
    private List<User> members;
    private String tag;
    private String name;
    private User leader;
    private User mod;
    private List<User> invited = new ArrayList<>();
    private Long lifeTime;
    private Long createTime;

    public Team() {
        max_size = CONFIG.PARTY_MAX_MEMBERS;
        tag = "";
        name = "";
        leader = null;
        mod = null;
    }

    public int getMax_size() {
        return max_size;
    }

    public void setMax_size(int max_size) {
        this.max_size = max_size;
    }

    public List<User> getInvited() {
        return invited;
    }

    public void setInvited(List<User> invited) {
        this.invited = invited;
    }

    public Long getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(Long lifeTime) {
        this.lifeTime = lifeTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public User getMod() {
        return mod;
    }

    public void setMod(User mod) {
        this.mod = mod;
    }

    public List<User> getMembers() {
        return members;
    }

    public boolean containsMember(User user) {
        return members.contains(user);
    }

    public void removeMember(User user) {
        if (containsMember(user)) {
            members.remove(user);
        }
    }

    public void addMember(User user) {
        if (!containsMember(user)) {
            members.add(user);
        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    /*
     private int max_size;
     private List<User> members;
     private String tag;
     private String name;
     private User leader;
     private User mod;
     private List<User> invited = new ArrayList<>();
     private Long lifeTime;
     private Long createTime;
     */
    private void loadMembers() {
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
            st = conn.prepareStatement("SELECT * FROM `MembersDataPvPCage` WHERE `tag`=?");//change names and where `somethink`
            st.setString(1, tag);
            rs = st.executeQuery();
            if (rs.next()) {
                User u = new User(UUID.fromString(rs.getString("uuid")));
                u.loadFromMySQL();
                members.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PvPCage.getInstance().getMySQL().closeResources(rs, st);

    }

    private void saveMembers() {
        Connection conn = PvPCage.getInstance().getMySQL().getConnection();
        if (conn == null) {
            try {
                conn = PvPCage.getInstance().getMySQL().openConnection();
            } catch (Exception e) {
                Log.ERROR.prefix("MySQL").print("&4Nie mozna zapisac ofiar z powodu braku polaczenia do mysql!");
                return;
            }
        }

        PreparedStatement st = null;
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO `MembersDataPvPCage` (`tag`, `uuid`)")
                .append("VALUES (?,?) ON DUPLICATE KEY UPDATE ")
                .append("`tag`=VALUES(`tag`), `uuid`=VALUES(`uuid`)");
        try {
            for (User victim : members) {
                st.addBatch(query.toString());
                st.setString(1, tag);
                st.setString(2, victim.getUuid().toString());
            }
            st.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PvPCage.getInstance().getMySQL().closeResources(null, st);
    }

    public void saveToMySQL() {
        Connection conn = PvPCage.getInstance().getMySQL().getConnection();
        if (conn == null) {
            try {
                conn = PvPCage.getInstance().getMySQL().openConnection();
            } catch (Exception e) {
                Log.ERROR.prefix("MySQL").print("&4Nie mozna zapisac teamu z powodu braku polaczenia do mysql!");
                return;
            }
        }
        PreparedStatement st = null;      
        try {
            st = conn.prepareStatement(Utils.loadQueryNew("insertTeamData"));
            st.setString(1, tag);
            st.setString(2, name);
            st.setString(3, leader.getUuid().toString());
            st.setString(4, mod.getUuid().toString());
            st.setLong(5, lifeTime);
            st.setLong(6, createTime);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        saveMembers();
        PvPCage.getInstance().getMySQL().closeResources(null, st);
    }

    public void loadFromMySQL() {
        Connection conn = PvPCage.getInstance().getMySQL().getConnection();
        if (conn == null) {
            try {
                conn = PvPCage.getInstance().getMySQL().openConnection();
            } catch (Exception e) {
                Log.ERROR.prefix("MySQL").print("&4Nie mozna wczytaj teamu z powodu braku polaczenia do mysql!");
                return;
            }
        }
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM `TeamDataPvPCage` WHERE `tag`=?");
            st.setString(1, tag);
            rs = st.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
                leader = UserManager.getUser(UUID.fromString("leader"));
                mod = UserManager.getUser(UUID.fromString("mod"));
                lifeTime = rs.getLong("lifetime");
                createTime = rs.getLong("createtime");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadMembers();
        PvPCage.getInstance().getMySQL().closeResources(rs, st);
    }
}


