package com.noname.pvpcage.utilities.data;

import com.noname.pvpcage.PvPCage;
import com.noname.pvpcage.utilities.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public enum Table {

    DEATHS("Victims"),
    TEAMS("Teams"),
    USERS("Users"),
    TEAM_MEMBERS("Members");

    private String createQueryName;

    private Table(String createQueryName) {
        this.createQueryName = createQueryName;
    }

    public void createTable() {
        PreparedStatement st;
        try {
            String query = loadCreateQuery();
            System.out.println(query);
            Connection conn = PvPCage.getInstance().getMySQL().getConnection();
            if (conn == null) {
                try {
                    conn = PvPCage.getInstance().getMySQL().openConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            st = conn.prepareStatement(query);
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String loadCreateQuery() {
        return Utils.loadQueryNew("createTable" + createQueryName);
    }
}
