package com.noname.pvpcage.utilities.data;

import com.noname.pvpcage.utilities.Configuration;
import com.noname.pvpcage.utilities.Msg;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    private Connection connection;

    public MySQL() {
        try {
            openConnection();
            Msg.console("polaczono z baza danych");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTables(){
        for(Table t: Table.values()) {
            t.createTable();
        }
    }
    public Connection openConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://"
                + Configuration.HOST
                + ":" + Configuration.PORT
                + "/" + Configuration.DATABASE, Configuration.USER, Configuration.PASSWORD);
        connection = conn;
        return conn;
    }

    public Connection getConnection() {
        if (hasConnection()) {
            return connection;
        } else {
            try {
                return openConnection();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void remove(String name) {
        if (!hasConnection()) {
            try {
                openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                PreparedStatement st = connection.prepareStatement("DELETE FROM `AwesomeDrop` WHERE `name`=?");
                st.setString(1, name);
                st.executeUpdate();
                closeResources(null, st);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean hasConnection() {
        try {
            return connection != null || connection.isValid(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = null;
        }
    }

    public void closeResources(ResultSet rs, PreparedStatement st) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
