package com.noname.pvpcage.utilities.data;

import com.noname.pvpcage.utilities.Configuration;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
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

    public void createTable() {
        StringBuilder table = new StringBuilder();
        table.append("CREATE TABLE IF NOT EXISTS `UserDataPvPCage` (")
                .append("`id` INT(6) NOT NULL AUTO_INCREMENT, ")
                .append("`uuid` VARCHAR(36) NOT NULL UNIQUE, ")
                .append("`name` VARCHAR(20) NOT NULL UNIQUE, ")
                .append("`loseduel` INT(12), ")
                .append("`winduel` INT(12), ")
                .append("`escapeduel` INT(12), ")
                .append("`points` INT(12), ")
                .append("`victims` VARCHAR(100), ")
                .append("PRIMARY KEY (`id`), ")
                .append("KEY (`name`, `uuid`))");
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(table.toString());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        table.setLength(0);
        table.append("");
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
