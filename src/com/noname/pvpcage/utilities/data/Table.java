package com.noname.pvpcage.utilities.data;

import com.noname.pvpcage.PvPCage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        String queryName = createQueryName + ".sql", query = "";
        try {
            String line;
            InputStream is = getClass().getClassLoader().getResourceAsStream(queryName);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            while ((line = input.readLine()) != null) {
                query += line;
            }
            input.close();
            return query;
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }
}
