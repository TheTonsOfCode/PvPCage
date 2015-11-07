package com.noname.pvpcage.utilities.data;

import com.noname.pvpcage.configuration.CfgDatabase;
import com.noname.pvpcage.utilities.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {
    
    public static final Log LOG_ERROR = Log.ERROR.prefix("MySQL");
    public static final Log LOG_INFO = Log.INFO.prefix("MySQL");
    public Connection connection;

    public MySQL() {
        try {
            openConnection();
            
            LOG_INFO.print("&aPolaczono z baza danych MajSeQuEl.");
        } catch (Exception e) {
            LOG_ERROR.print("&4NIE POLACZONO Z MYSQL");
        }
    }

    public void createTables() {
        if (getConnection() == null) {
            LOG_ERROR.print("&4NIE MOZNA STWORZYC TABEL PONIEWAZ NIE POLACZONO Z MYSQL");
            return;
        }
        for (Table t : Table.values()) {
            t.createTable();
        }
    }

    public Connection openConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://"
                + CfgDatabase.HOST
                + ":" + CfgDatabase.PORT
                + "/" + CfgDatabase.DATABASE, CfgDatabase.USER, CfgDatabase.PASSWORD);
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
//                LOG_ERROR.print("&4Blad z otwarciem polaczenia");
                return null;
            }//hehe
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
            if(connection == null) {
                return false;
            }
            
            return connection.isValid(1);
        } catch (SQLException ex) {
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
