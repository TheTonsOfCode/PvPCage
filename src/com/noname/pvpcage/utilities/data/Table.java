package com.noname.pvpcage.utilities.data;

import com.noname.pvpcage.PvPCage;
import com.noname.pvpcage.utilities.Utils;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public enum Table {

    DEATHS("createDeaths"),
    TEAMS("createTeams"),
    USERS("createUsers"),
    TEAM_MEMBERS("createMembers");
    
    private String createQueryName;

    private Table(String createQueryName) {
        this.createQueryName = createQueryName;
    }
    
    public void createTable() {
        PreparedStatement st = null;
        try {
            st = PvPCage.getMySQL().getConnection().prepareStatement(loadCreateQuery());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String loadCreateQuery() {
        return Utils.loadQuery(createQueryName + ".sql");
    }
}
